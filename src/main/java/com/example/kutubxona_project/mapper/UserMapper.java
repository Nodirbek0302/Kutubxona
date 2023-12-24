package com.example.kutubxona_project.mapper;

import com.example.kutubxona_project.dto.ResUserDTO;
import com.example.kutubxona_project.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ResUserDTO mapResUserDTO(Users users);
}
