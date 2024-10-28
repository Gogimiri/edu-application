package com.project.edu_service.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String username;

    private String password;

    private List<String> roles;
}
