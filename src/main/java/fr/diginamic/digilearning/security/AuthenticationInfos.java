package fr.diginamic.digilearning.security;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInfos {
    private List<String> roles = new ArrayList<>();
    private String email;
    private String token;
    private Long id;
    private Boolean banned;

    public boolean isFormateur(){
        return roles.contains(TypeRole.ROLE_FORMATEUR.getLibelle());
    }

    public boolean isStagiaire(){
        return roles.contains(TypeRole.ROLE_STAGIAIRE.getLibelle());
    }

    public boolean isAdministrateur(){
        return roles.contains(TypeRole.ROLE_ADMINISTRATEUR.getLibelle());
    }
}
