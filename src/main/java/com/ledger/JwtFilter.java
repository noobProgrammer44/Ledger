package com.ledger;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component: registers this class as a Spring-managed bean so Spring
// creates it, injects its dependencies, and plugs it into the filter chain.
@Component
// OncePerRequestFilter: a Spring base class guaranteeing the filter runs
// exactly once per HTTP request. Our logic goes in doFilterInternal below.
public class JwtFilter extends OncePerRequestFilter {

    // Service that knows how to read/validate JWT tokens.
    private final JwtService jwtService;

    // Constructor injection: Spring passes in the JwtService bean automatically.
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // shouldNotFilter: return true to SKIP this filter for a request.
    // Signup and login happen before you have a token, so we let them through.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path=request.getRequestURI();
        return path.equals("/signup") || path.equals("/login");
    }

    // doFilterInternal: the actual filter logic, run once per request.
    // Here it checks the JWT and either rejects (401) or lets the request continue.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Read the "Authorization: Bearer <token>" header.
        String header = request.getHeader("Authorization");
        if(header==null||!header.startsWith("Bearer ")){
            response.setStatus(401); // no/invalid header -> Unauthorized
            return ;
        }
        // Strip the "Bearer " prefix (7 chars) to get the raw token.
        String token = header.substring(7);

        try {
            // Validate the token and pull out the user id.
            int userId=jwtService.getUserId(token);
            // Stash the user id so downstream controllers can read who is calling.
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            response.setStatus(401); // bad/expired token -> Unauthorized
            return ;
        }

        // Token is valid: hand the request on to the next filter / controller.
        filterChain.doFilter(request, response);

    }
}
