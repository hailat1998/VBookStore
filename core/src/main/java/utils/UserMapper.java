package com.hd.vbookstore.core.utils;

import com.hd.vbookstore.commons.UserDto;
import com.hd.vbookstore.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

   UserDto userToUserDto(User user);

   User UserDtoToUser(UserDto userDto);

}
