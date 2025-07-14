package com.usedcars.usedcars.mappers;


import com.usedcars.usedcars.dtos.UserDto;
import com.usedcars.usedcars.enums.Role;
import com.usedcars.usedcars.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserDtoMapper {
    @Mappings({
            @Mapping(target = "role", source = "role", qualifiedByName = "roleToString")})
    UserDto userToUserDto(User user);
    List<UserDto> userToUserDto(List<User> userList);

    @Named("roleToString")
    default String stringToPath(Role role) {
        return role.name();
    }
}
