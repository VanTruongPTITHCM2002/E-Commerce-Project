package com.ecommerce.cart_service.utils;

import com.ecommerce.cart_service.common.MessageError;
import com.ecommerce.cart_service.exception.AppException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.text.ParseException;

public class GetUserUtils {
    public static String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public static String getUsernameFromToken (HttpServletRequest request){
        String token = extractToken(request);
        if(!StringUtils.hasText(token))
            throw new AppException(HttpStatus.UNAUTHORIZED.value(), MessageError.UNAUTHORIZED.getMessage());
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            return jwtClaimsSet.getSubject();
        }catch (ParseException parseException){
            throw new AppException(HttpStatus.UNAUTHORIZED.value(), MessageError.UNAUTHORIZED.getMessage());
        }


    }
}
