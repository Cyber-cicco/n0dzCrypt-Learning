package fr.diginamic.digilearning.security.service;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.hx.HX;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;
    public AuthenticationInfos getAuthInfos(String token){
        Claims claims = jwtService.extractAllClaims(token);
        return AuthenticationInfos.builder()
                .email(jwtService.extractEmail(claims))
                .roles(jwtService.extractRoles(claims))
                .id(jwtService.extractId(claims))
                .banned(jwtService.extractBanned(claims))
                .token(token)
                .build();
    }

    public AuthenticationInfos getAuthInfos(){
        return AuthenticationInfos.builder()
                .email(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .roles(SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .stream()
                        .map(Object::toString)
                        .toList())
                .id((Long) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .build();
    }

    public void mustBeOfRole(List<String> currentRoles, TypeRole expectedRole, HttpServletResponse response) {
        if(!currentRoles.contains(expectedRole.getLibelle())) {
            try {
                response.setHeader(HX.RETARGET, "html");
                response.sendRedirect("/login");
            } catch (IOException e){
                throw new RuntimeException();
            }
        }
    }

    public void rolesMustMatchOne(List<String> currentRoles, List<TypeRole> acceptedRoles, HttpServletResponse response) {
        if(Collections.disjoint(acceptedRoles
                .stream()
                .map(TypeRole::getLibelle)
                .toList(), currentRoles)){
            try {
                response.setHeader(HX.RETARGET, "html");
                response.sendRedirect("/login");
            } catch (IOException e){
                throw new BrokenRuleException();
            }
        }
    }

    public Boolean checkBanned(Long id) {
        return utilisateurRepository.findById(id).orElseThrow(EntityNotFoundException::new).getBanned();
    }
}
