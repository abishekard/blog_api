package com.example.blog_app_api.security;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToke = request.getHeader("Authorization");
        System.out.println(requestToke);
        String userName = null;
        String token = null;
        if (requestToke != null && requestToke.startsWith("Bearer")) {
            token = requestToke.substring(7);
            try {
                userName = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("unable to get Jwt token");
            } catch (ExpiredJwtException e) {
                System.out.println("expired jwt token");
            } catch (MalformedJwtException e) {
                System.out.println("malformed jwt token");
            }

        } else {
            System.out.println("Jwt token does not starts with bearer");
        }
        //once we get the token , now validate it
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenHelper.validateAccessToken(token)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Invalid jwt Token");
            }
        } else {
            System.out.println("username is null or context or context is not null");
        }

        filterChain.doFilter(request,response);
    }
}
