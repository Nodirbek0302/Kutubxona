package com.example.kutubxona_project.service.auth;

import com.example.kutubxona_project.config.jwtFilter.JWTProvider;
import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.dto.LoginDTO;
import com.example.kutubxona_project.dto.RegisterDTO;
import com.example.kutubxona_project.dto.TokenDTO;
import com.example.kutubxona_project.enums.Roles;
import com.example.kutubxona_project.exceptions.RestException;
import com.example.kutubxona_project.model.Users;
import com.example.kutubxona_project.repository.UserRepository;
import com.example.kutubxona_project.utils.AppConstants;
import com.example.kutubxona_project.utils.EmailService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           @Lazy AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResult<TokenDTO> login(LoginDTO loginDTO) {
        Users user1 = checkCredential(loginDTO.email(), loginDTO.password());
        return ApiResult.successResponse(generateTokenDTO(user1));
    }

    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        if (!accessToken.startsWith(AppConstants.BEARER_TYPE))
            throw RestException.restThrow("Bearer emas");

        accessToken = accessToken.substring(AppConstants.BEARER_TYPE.length()).trim();
        refreshToken = refreshToken.substring(AppConstants.BEARER_TYPE.length()).trim();
        if (!jwtProvider.isExpired(accessToken))
            throw RestException.restThrow("Token muddati tugamagan");

        if (!jwtProvider.validRefreshToken(refreshToken))
            throw RestException.restThrow("Refresh token valid emas");

        String userId = jwtProvider.extractUserIdFromRefreshToken(refreshToken);
        Users user = findUserById(Integer.valueOf(userId))
                .orElseThrow(() -> RestException.restThrow("User not found: " + userId, HttpStatus.NOT_FOUND));

        TokenDTO tokenDTO = generateTokenDTO(user);

        return ApiResult.successResponse(tokenDTO);
    }

    @Override
    public ApiResult<String> register(RegisterDTO registerDTO) {
        Users user = userRepository.findByEmail(registerDTO.email()).orElseThrow(() -> RestException.restThrow("Iltimos email tasdiqlaganingizga ishonch hosil qiling", HttpStatus.BAD_REQUEST));

        if (!user.getRegisterCode().equals(registerDTO.code()))
            throw RestException.restThrow("activation kod xato ", HttpStatus.BAD_REQUEST);

        if (!registerDTO.password().equals(registerDTO.perPassword()))
            throw RestException.restThrow("parrollar birxilmas", HttpStatus.BAD_REQUEST);

        user.setName(registerDTO.name());
        user.setAccountNonLocked(false);
        user.setPass(passwordEncoder.encode(registerDTO.password()));
        user.setRole(Roles.USER);

        userRepository.save(user);

        return ApiResult.successResponse("successfully");
    }

    @Override
    public ApiResult<Boolean> sendEmail(String email) {
        Optional<Users> byEmail = userRepository.findByEmail(email);
        String generationCode = EmailService.getGenerationCode();

        if (byEmail.isPresent()) {
            byEmail.get().setRegisterCode(generationCode);
            boolean b = EmailService.sendMessageToEmail(email, generationCode);

            if (!b) {
                throw RestException.restThrow("iltimos birozdan sung qayta urunib kuring", HttpStatus.BAD_REQUEST);
            }
            userRepository.save(byEmail.get());
            return ApiResult.successResponse(true);
        }

        boolean res = EmailService.sendMessageToEmail(email, generationCode);
        if (!res) {
            throw RestException.restThrow("iltimos birozdan sung qayta urunib kuring", HttpStatus.BAD_REQUEST);
        }

        Users user = Users.builder()
                .email(email)
                .registerCode(generationCode)
                .pass("1")
                .build();

        userRepository.save(user);
        return ApiResult.successResponse(true);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public Optional<Users> findUserById(Integer userId) {
        if (userId == null)
            return Optional.empty();
        return userRepository.findById(userId);
    }


    private TokenDTO generateTokenDTO(Users user) {
        String id = user.getId().toString();
        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshAccessToken(id);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Users checkCredential(String username, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return (Users) authentication.getPrincipal();
    }

    @Override
    public ApiResult<Boolean> setRole(Integer userId, char firstLater) {
        Users us = userRepository.findById(userId).orElseThrow(() -> RestException.restThrow("Bunday user mavjud emas", HttpStatus.BAD_REQUEST));
        char lowerCase = Character.toLowerCase(firstLater);
        switch (lowerCase){
            case 'u'->us.setRole(Roles.USER);
            case 'm'->us.setRole(Roles.MODERATOR);
            case 'a'->us.setRole(Roles.ADMIN);
            default -> throw RestException.restThrow("iltimos rollni bosh harfini kiritng",HttpStatus.BAD_REQUEST);
        }
        userRepository.save(us);
        return ApiResult.successResponse(true);
    }
}
