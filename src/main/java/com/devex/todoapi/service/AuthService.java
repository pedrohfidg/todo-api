package com.devex.todoapi.service;

import com.devex.todoapi.exception.ForbiddenException;
import com.devex.todoapi.model.User;
import com.devex.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void validateSelfOrAdmin(Long userId) {
        User me = this.authenticated();

        if (!me.getId().equals(userId) && !me.hasRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Acesso negado");
        }
    }

    protected User authenticated() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
