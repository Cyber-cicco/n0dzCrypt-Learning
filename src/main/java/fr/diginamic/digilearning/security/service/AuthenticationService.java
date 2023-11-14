package fr.diginamic.digilearning.security.service;

import fr.diginamic.digilearning.entities.enums.RoleEnum;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.dto.LoginDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    public AuthenticationInfos getAuthInfos(String token){
        return AuthenticationInfos.builder()
                .email(jwtService.extractEmail(token))
                .roles(jwtService.extractRoles(token))
                .token(token)
                .build();
    }

    public void mustBeOfRole(List<String> currentRoles, RoleEnum expectedRole, HttpServletResponse response) {
        if(!currentRoles.contains(expectedRole.getLibelle())) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e){
                throw new BrokenRuleException();
            }
        }
    }
}
