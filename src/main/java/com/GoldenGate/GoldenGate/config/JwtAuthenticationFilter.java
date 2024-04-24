package com.GoldenGate.GoldenGate.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService JwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
                                   @NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain)
            throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
        //System.out.println("auth heafer "+authHeader);
            final String jwt;
            final String userEmail;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = JwtService.extractUsername(jwt); // Extarcting User Email From Jwt Token
       // System.out.println("user Email"+userEmail);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //System.out.println("user Details After User Email"+userDetails);
           if (JwtService.isTokenValid(jwt,userDetails)){
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,
                       null,
                       userDetails.getAuthorities()
               );
              // System.out.println("user authToken"+authToken);
               authToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
        }
        filterChain.doFilter(request , response);

    }
}
