package com.ecommerce.auth_service.filter;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



public class JwtBlackListFilter extends OncePerRequestFilter {

    private final Cache<String, Boolean> tokenBlackListCache;

    public JwtBlackListFilter(Cache <String, Boolean> tokenBlackListCache){
        this.tokenBlackListCache = tokenBlackListCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenBlackListCache.getIfPresent(token) != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token has been revoked (logged out)");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
