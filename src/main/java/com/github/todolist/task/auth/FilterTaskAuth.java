package com.github.todolist.task.auth;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.todolist.user.models.User;
import com.github.todolist.user.repositories.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/api/tasks")) {

            var authorization = request.getHeader("Authorization");
            String base64EncodedAuth = authorization.substring(6);
            byte[] decodedAuth = Base64.getDecoder().decode((base64EncodedAuth));
            String strAuth = new String(decodedAuth);
            String[] credentials = strAuth.split(":");
            String username = credentials[0];
            String password = credentials[1];

            User user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
                return;
            }

            var verifyer = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (verifyer.verified) {
                request.setAttribute("userId", user.getId());
                filterChain.doFilter(request, response);
                return;
            }

            response.sendError(401);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
