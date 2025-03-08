package com.hd.vbookstore.commons;


import com.hd.vbookstore.domain.enums.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    private String username;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;
    private Set<Role> roles;
}
