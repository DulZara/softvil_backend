package com.event.service.config;

import com.event.service.service.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String bearer = request.getHeader("Authorization");
            log.debug("JwtAuthFilter — Authorization header: {}", bearer);

            if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
                String jwt = bearer.substring(7);
                log.debug("JwtAuthFilter — Resolved JWT: {}", jwt);

                if (tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUserIdFromToken(jwt);
                    log.debug("JwtAuthFilter — Token validated. Subject: {}", username);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.debug("JwtAuthFilter — Authentication set for user: {}", username);
                }
            }
        } catch (Exception ex) {
            log.error("JwtAuthFilter — Could not set user authentication", ex);
        }

        // always continue the filter chain
        filterChain.doFilter(request, response);
    }
}
