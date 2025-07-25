package com.ecommerce.cart_service.aspect;

import com.ecommerce.cart_service.common.MessageError;
import com.ecommerce.cart_service.exception.AppException;
import com.ecommerce.cart_service.utils.GetUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationAspect {
    HttpServletRequest httpServletRequest;

    @Before("within(@com.ecommerce.cart_service.utils.CheckUserAccess *) && execution(* *(..))")
    public void checkUserAccessByClass(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof String userId) {
                String username = GetUserUtils.getUsernameFromToken(httpServletRequest);
                if (!username.equals(userId)) {
                    throw new AppException(HttpStatus.FORBIDDEN.value(), MessageError.UNAUTHORIZED_CART_ACCESS.getMessage());
                }
                break;
            }
        }
    }
}
