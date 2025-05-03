package com.bertcoscia.BookReader.security;

import com.bertcoscia.BookReader.entities.User;
import com.bertcoscia.BookReader.exceptions.UnauthorizedException;
import com.bertcoscia.BookReader.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Please insert token in Authorization header correctly");

        String token = authHeader.substring(7);
        jwtTools.verifyToken(token);

        String id = jwtTools.extractIdFromToken(token);
        User currentUser = this.userService.findById(Long.valueOf(id));
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        String method = request.getMethod();

        return new AntPathMatcher().match("/auth/**", servletPath) ||
                (new AntPathMatcher().match("/restaurant-categories/list", servletPath) && method.equalsIgnoreCase("GET"));
    }
}