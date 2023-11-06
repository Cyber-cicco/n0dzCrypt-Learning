package fr.diginamic.digilearning.components.elements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NavLink {
    private String url;
    private String iconSource;
    private String libelle;
}
