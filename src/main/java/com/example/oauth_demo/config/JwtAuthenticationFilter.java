package com.example.oauth_demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // İstekteki tokenı çözümle
        String token = jwtUtils.resolveToken(request);
        logger.debug("Resolved token: {}", token);

        // Boş değilse tokenı doğrula
        if (token != null && jwtUtils.validateToken(token)) {
            // Get username from the token
            String username = jwtUtils.getUsernameFromToken(token);
            logger.debug("Token validated successfully for username: {}", username);

            // Kullanıcı adını kullanarak kullanıcı ayrıntılarını yükle
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                // Create authentication object and set it in the SecurityContext
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authentication set for user: {}", username);
            } else {
                logger.warn("User details not found for username: {}", username);
            }
        } else {
            logger.warn("Token is either null or invalid: {}", token);
        }

        // filterChainin devam ettiğinden emin olun
        filterChain.doFilter(request, response);
    }

}
