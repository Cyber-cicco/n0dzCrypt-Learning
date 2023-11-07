package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ROLE")
@Cacheable(value = true)
public class Role implements GrantedAuthority {

	/** serialVersionUID : long */
	private static final long serialVersionUID = 5951377624122103098L;

	/** identifiant */
	@Id
	private Long id;

	/** type de rôle */
	@Enumerated(EnumType.STRING)
	private TypeRole type;

	/** Libellé */
	private String libelle;

	@Override
	public String getAuthority() {
		return type.name();
	}
}
