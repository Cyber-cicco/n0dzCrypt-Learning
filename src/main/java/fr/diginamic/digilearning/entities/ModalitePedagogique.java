package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "MODALITES_PEDAGOGIQUES")
public class ModalitePedagogique {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "code", length = 5)
	private String code;

	/** nom : String */
	@Column(name = "nom", length = 100)
	private String nom;

	/** nom : String */
	@Column(name = "commentaires", length = 100)
	private String commentaires;

	/** formateur : Boolean */
	@Column(name = "besoin_formateur")
	private boolean besoinFormateur;

	/** salle : Boolean */
	@Column(name = "besoin_salle")
	private boolean besoinSalle;

	/** horsCentre : boolean */
	@Column(name = "hors_centre")
	private boolean horsCentre;

	/** presentiel : boolean */
	@Column(name = "presentiel")
	private boolean presentiel;

	/** emargement : boolean */
	@Column(name = "emargement")
	private boolean emargement;

	/** tjmPartiel : pour les journées tutorées notamment */
	@Column(name = "tjm_partiel")
	private boolean tjmPartiel;

}
