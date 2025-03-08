package com.hd.vbookstore.commons;


import com.hd.vbookstore.domain.enums.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDto implements Serializable {
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
