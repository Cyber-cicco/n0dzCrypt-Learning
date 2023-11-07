package fr.diginamic.digilearning.entities.taches;

import fr.diginamic.digilearning.entities.Utilisateur;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TACHE_COMMENTAIRE")
public class TacheCommentaire {

	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** contenu */
	@Column(name = "CONTENU")
	private String contenu;

	/** utilisateur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

	/** dateCreation */
	@Column(name = "DATE_CREATION")
	private LocalDateTime dateCreation;

	/** dateModif */
	@Column(name = "DATE_MODIF")
	private LocalDateTime dateModif;

	/** tachePlanifiee */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TACHE_PLANIFIEE")
	private TachePlanifiee tachePlanifiee;

	/** couleur */
	@Column(name = "COULEUR")
	private String couleur;
}
