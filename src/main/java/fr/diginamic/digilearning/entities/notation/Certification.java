package fr.diginamic.digilearning.entities.notation;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "CERTIFICATION")
public class Certification {

	/**
	 * id : Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * identificationCandidat : String
	 */
	@Column(name = "IDENTIFIANT_CANDIDAT")
	private String identifiantCandidat;

	/**
	 * editeur
	 */
	@Column(name = "EDITEUR")
	private String editeur;

	/**
	 * nom
	 */
	@Column(name = "NOM")
	private String nom;

	/**
	 * languePassage : String
	 */
	@Column(name = "LANGUE_PASSAGE")
	private String languePassage;

	/**
	 * scoreBlanc1 : String
	 */
	@Column(name = "SCORE_BLANC1")
	private String scoreBlanc1;

	/**
	 * scoreBlanc2 : String
	 */
	@Column(name = "SCORE_BLANC2")
	private String scoreBlanc2;

	/**
	 * scoreBlanc3 : String
	 */
	@Column(name = "SCORE_BLANC3")
	private String scoreBlanc3;

	/**
	 * scoreBlanc4 : String
	 */
	@Column(name = "SCORE_BLANC4")
	private String scoreBlanc4;

	/**
	 * scoreBlanc5 : String
	 */
	@Column(name = "SCORE_BLANC5")
	private String scoreBlanc5;

	/**
	 * score d'obtention : String
	 */
	@Column(name = "SCORE_OBTENTION")
	private String scoreObtention;

	/**
	 * scoreReel : String
	 */
	@Column(name = "SCORE_REEL")
	private String scoreReel;

	/**
	 * datePassage : Date de passage
	 */
	@Column(name = "DATE_PASSAGE")
	private String datePassage;

	/**
	 * heureArrivee : String
	 */
	@Column(name = "HEURE_ARRIVEE")
	private String heureArrivee;

	/**
	 * heurePassage : Heure de passage
	 */
	@Column(name = "HEURE_PASSAGE")
	private String heurePassage;

	/**
	 * Adresse de passage de la certification
	 */
	@Column(name = "ADRESSE_PASSAGE")
	private String adressePassage;

	/**
	 * Nom de l'organisme pour le passage de la certification
	 */
	@Column(name = "NOM_ORGANISME")
	private String nomOrganisme;

	/**
	 * obtenu
	 */
	@Column(name = "OBTENU")
	private String obtenu;

	/**
	 * bulletin : Bulletin
	 */
	@ManyToOne
	@JoinColumn(name = "ID_BULLETIN")
	private Bulletin bulletin;

}