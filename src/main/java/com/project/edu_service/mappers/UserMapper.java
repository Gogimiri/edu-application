package com.project.edu_service.mappers;

import com.project.edu_service.Dtos.UserDto;
import com.project.edu_service.entityes.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public Users toEntity(UserDto userDto) {
        Users users = new Users();
        users.setUsername(userDto.getUsername());
        users.setPassword(userDto.getPassword());
        return users;
    }

    public UserDto toDto(Users users) {
        UserDto userDto = new UserDto();
        userDto.setUsername(users.getUsername());
        userDto.setPassword(users.getPassword());
        return userDto;
    }

}
