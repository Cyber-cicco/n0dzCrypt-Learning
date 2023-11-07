package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "CONNEXION")
public class Connexion {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** dateHeureConnexion */
	@Column(name = "DATE_HEURE")
	private LocalDateTime dateHeureConnexion;

	@ManyToOne
	@JoinColumn(name = "ID_UTL")
	private Utilisateur utilisateur;

}
