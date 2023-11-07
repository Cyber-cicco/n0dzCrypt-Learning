package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "COURS_PERSONNALISE")
public class CoursPersonnalise extends CoursReel {

	/** suivi */
	@Column(name = "SUIVI")
	private boolean suivi;

	/** identifiant de la session */
	@Column(name = "ID_SESSION")
	private Long idSession;

	/** stagiaire */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_STAGIAIRE")
	private Utilisateur stagiaire;

	/** userMaj */
	@Column(name = "USER_MAJ")
	protected String userMaj;

	/** dateMaj */
	@Column(name = "DATE_MAJ")
	protected LocalDateTime dateMaj;
}
