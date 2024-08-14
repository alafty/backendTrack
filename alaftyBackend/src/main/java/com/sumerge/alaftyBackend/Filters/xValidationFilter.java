package com.sumerge.alaftyBackend.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class xValidationFilter extends HttpFilter {

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String validationHeader = request.getHeader("x-validation-report");
        System.out.println("Validation Header: " + validationHeader + ", URI: " + request.getRequestURI());

        if ("true".equalsIgnoreCase(validationHeader)) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Missing or incorrect x-validation-report header");
        }
    }
}
