package fr.diginamic.digilearning.security.service;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    public AuthenticationInfos getAuthInfos(String token){
        Claims claims = jwtService.extractAllClaims(token);
        return AuthenticationInfos.builder()
                .email(jwtService.extractEmail(claims))
                .roles(jwtService.extractRoles(claims))
                .id(jwtService.extractId(claims))
                .token(token)
                .build();
    }

    public void mustBeOfRole(List<String> currentRoles, TypeRole expectedRole, HttpServletResponse response) {
        if(!currentRoles.contains(expectedRole.getLibelle())) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e){
                throw new BrokenRuleException();
            }
        }
    }
}
