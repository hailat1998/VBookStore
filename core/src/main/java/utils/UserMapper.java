package com.hd.vbookstore.core.utils;

import com.hd.vbookstore.commons.UserDto;
import com.hd.vbookstore.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import org.mapstruct.*;

import java.io.Serializable;
import java.util.Set;




@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends Serializable {

   @Mapping(target = "roles", source = "roles")
   UserDto userToUserDto(User user);

   @Mapping(target = "roles", source = "roles")
   User userDtoToUser(UserDto userDto);

}
