package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.common.ResponseMessageFailure;
import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.response.AuthResponse;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.exception.AppException;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.service.IAuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements IAuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Override
    public AuthResponse loginPage(AuthRequest authRequest) throws JOSEException {
        User user = this.userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST.value(), ResponseMessageFailure.USER_NOT_EXISTED.getMessage()));
        boolean isMatch = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if(!isMatch){
            throw new AppException(HttpStatus.BAD_REQUEST.value(), ResponseMessageFailure.PASSWORD_INCORRECT.getMessage());
        }
        String token = generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    private String generateToken(User user) throws JOSEException {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("vantruong.com")
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
}
