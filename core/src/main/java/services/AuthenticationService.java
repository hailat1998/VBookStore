package com.hd.vbookstore.core.services;

import com.hd.vbookstore.commons.LoginUserDto;
import com.hd.vbookstore.commons.RegisterUserDto;
import com.hd.vbookstore.commons.UserDto;
import com.hd.vbookstore.data.UserRepository;
import com.hd.vbookstore.domain.enums.Role;
import com.hd.vbookstore.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDto signup(RegisterUserDto input) {

        User user = new User(input.getUsername(),
                passwordEncoder.encode(input.getPassword()),
                input.getFullname(), input.getStreet(),
                input.getCity(),
                input.getState(), input.getZip(),
                input.getPhoneNumber(),
                input.getEmail());

        user.addRole(Role.ROLE_USER);

        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User createAdminUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_ADMIN);
        roles.add(Role.ROLE_USER);
        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void assignRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.addRole(role);
        userRepository.save(user);
    }


    public UserDetails authenticate(LoginUserDto input) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (UserDetails) authentication.getPrincipal();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
 }

