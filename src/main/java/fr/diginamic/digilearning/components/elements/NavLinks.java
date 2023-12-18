package fr.diginamic.digilearning.components.elements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Agrégat de liens de la navbar
 */
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class NavLinks {
    NavLink navLinks[];
}
