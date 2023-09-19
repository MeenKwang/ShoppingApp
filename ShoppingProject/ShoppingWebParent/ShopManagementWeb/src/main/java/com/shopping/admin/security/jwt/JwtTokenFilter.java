package com.shopping.admin.security.jwt;

import com.shopping.admin.repository.UserRepository;
import com.shopping.admin.security.CustomUserDetails;
import com.shopping.common.entity.Role;
import com.shopping.common.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!hasAuthorizationHeader(request)) {
            filterChain.doFilter(request,response);
            return;
        }

        String accessToken = getAccessToken(request);

        if(!jwtTokenUtil.validateAccessToken(accessToken)) {
            filterChain.doFilter(request,response);
            return;
        }
        setAuthenticationContext(accessToken, request);
        filterChain.doFilter(request,response);
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        System.out.println("Access Header: " + header);
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        String token = header.split(" ")[1].trim();
        System.out.println("Access Token: " + token);
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        User user = new User();

        Claims claims = jwtTokenUtil.parseClaims(token);
        String claimRoles = (String) claims.get("roles");
        claimRoles = claimRoles.replace("[", "").replace("]", "");
        System.out.println("ClaimRoles: " + claimRoles);
        String[] roleNames = claimRoles.split(",");
        for(String roleName : roleNames) {
            user.addRole(new Role(roleName));
        }
        String subject = (String) claims.get(Claims.SUBJECT);
        String[] jwtSubject = subject.split(",");
        Integer userId = Integer.parseInt(jwtSubject[0]);
        String email = jwtSubject[1];

        user.setEmail(email);
        user.setId(userId);

        return new CustomUserDetails(user);
    }
}
