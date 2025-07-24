package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.common.ResponseMessageFailure;
import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.request.ChangePasswordRequest;
import com.ecommerce.auth_service.dto.request.RegisterRequest;
import com.ecommerce.auth_service.dto.response.AuthResponse;
import com.ecommerce.auth_service.dto.response.RegisterResponse;
import com.ecommerce.auth_service.dto.response.TokenResponse;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.exception.AppException;
import com.ecommerce.auth_service.repository.RoleRepository;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.service.IAuthService;
import com.github.benmanes.caffeine.cache.Cache;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements IAuthService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    Cache<String, Boolean> tokenBlackListCache;

    @NonFinal
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Override
    public AuthResponse loginPage(AuthRequest authRequest) throws JOSEException {
        User user = this.userRepository.findByUsername(
                authRequest.getUsername()).
                orElseThrow(
                        () -> new AppException(HttpStatus.BAD_REQUEST.value(),
                                ResponseMessageFailure.USER_NOT_FOUND.getMessage())
                );
        if(!user.getStatus()) throw new AppException(HttpStatus.UNAUTHORIZED.value(),
                ResponseMessageFailure.ACCOUNT_DISABLED.getMessage());
        boolean isMatch = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if(!isMatch){
            throw new AppException(HttpStatus.BAD_REQUEST.value(), ResponseMessageFailure.LOGIN_FAILED.getMessage());
        }
        String token = generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    @Override
    public TokenResponse logoutPage(String token) {
        tokenBlackListCache.put(token, true);
        return TokenResponse.builder().token(token).build();
    }

    @Override
    public RegisterResponse registerPage(RegisterRequest registerRequest) {
        boolean checkUser = this.userRepository.existsByUsername(registerRequest.getUsername())
               || this.userRepository.existsByEmail(registerRequest.getEmail()) ;

        if(checkUser)
            throw new AppException(HttpStatus.BAD_REQUEST.value(),
                    ResponseMessageFailure.USER_EXISTS.getMessage());

        User user = this.userRepository.save(
                User.builder()
                        .username(registerRequest.getUsername())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .role(this.roleRepository.findAll().stream()
                                .filter(role -> role.getRoleName().equals(registerRequest.getRole()))
                                .toList().getFirst())
                        .build()
        );
        return RegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void changePasswordPage(HttpServletRequest httpServletRequest,ChangePasswordRequest request) {
        try {
            String token = extractToken(httpServletRequest);
            if(!StringUtils.hasText(token))
                throw new AppException(HttpStatus.UNAUTHORIZED.value(), ResponseMessageFailure.UNAUTHORIZED.getMessage());
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            String username = jwtClaimsSet.getSubject();
            User user = this.userRepository.findByUsername(username).orElseThrow(()
                    -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.USER_NOT_FOUND.getMessage()));
            if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
                throw new AppException(HttpStatus.BAD_REQUEST.value(), ResponseMessageFailure.INCORRECT_OLD_PASSWORD.getMessage());
            if(!request.getNewPassword().equals(request.getConfirmPassword()))
                throw new AppException(HttpStatus.BAD_REQUEST.value(), ResponseMessageFailure.CONFIRM_NEW_PASSWORD_FAIL.getMessage());
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            this.userRepository.save(user);
        }catch (ParseException p){
            throw new AppException(HttpStatus.UNAUTHORIZED.value(), ResponseMessageFailure.UNAUTHORIZED.getMessage());
        }

    }

    private String generateToken(User user) throws JOSEException {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("turn.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
        return jwsObject.serialize();
    }

    private String buildScope (User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!user.getRole().getUsers().isEmpty()){
            stringJoiner.add("ROLE_" + user.getRole().getRoleName());

            if(!user.getRole().getPermissions().isEmpty()){
                user.getRole().getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionName()));
            }
        }

        return stringJoiner.toString();
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
