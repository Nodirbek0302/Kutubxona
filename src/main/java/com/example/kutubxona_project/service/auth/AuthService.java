package com.example.kutubxona_project.service.auth;


import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.dto.LoginDTO;
import com.example.kutubxona_project.dto.RegisterDTO;
import com.example.kutubxona_project.dto.TokenDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    ApiResult<TokenDTO> login(LoginDTO loginDTO);

    ApiResult<TokenDTO> refreshToken(String accessToken,String refreshToken);

    ApiResult<String> register(RegisterDTO registerDTO);

    ApiResult<Boolean> sendEmail(String email);

    ApiResult<Boolean> setRole(Integer userId, char firstLater);
}
