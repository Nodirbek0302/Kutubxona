package com.example.kutubxona_project.controller;

import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.dto.LoginDTO;
import com.example.kutubxona_project.dto.RegisterDTO;
import com.example.kutubxona_project.dto.TokenDTO;
import com.example.kutubxona_project.service.auth.AuthService;
import com.example.kutubxona_project.utils.AppConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.AUTHE_CONTROLLER_BASE_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AppConstants.AUTHE_CONTROLLER_LOGIN_PATH)
    public HttpEntity<ApiResult<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO){
      return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping(AppConstants.AUTHE_CONTROLLER_REGISTER_PATH)
    public HttpEntity<ApiResult<String>> register(@Valid @RequestBody RegisterDTO registerDTO){
      return ResponseEntity.ok(authService.register(registerDTO));
    }

   @PostMapping(AppConstants.AUTHE_CONTROLLER_SEND_EMAIL)
    public HttpEntity<ApiResult<Boolean>> sendEmail(@RequestParam String email){
        return ResponseEntity.ok(authService.sendEmail(email));
   }

   @PatchMapping(AppConstants.AUTHE_CONTROLLER_REFRESH_TOKEN_PATH)
    public HttpEntity<ApiResult<TokenDTO>> refreshToken(String accessToken,String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(accessToken,refreshToken));
   }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping("/setRole")
    public HttpEntity<ApiResult<Boolean>> setRole(@NotNull @RequestParam Integer userId, @NotNull @RequestParam char firstLater){
        return ResponseEntity.ok(authService.setRole(userId,firstLater));
    }
}
