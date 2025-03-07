package com.hd.vbookstore.commons;


import com.hd.vbookstore.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserDto {
    private final String username;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;
    private final String email;
    private final Set<Role> roles;
}
