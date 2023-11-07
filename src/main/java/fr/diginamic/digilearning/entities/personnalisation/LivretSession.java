package fr.diginamic.digilearning.entities.personnalisation;

import fr.diginamic.digilearning.entities.Societe;
import fr.diginamic.digilearning.entities.Utilisateur;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "LIVRET_SESSION")
public class LivretSession {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** nom : String */
	@ManyToOne
	@JoinColumn(name = "ID_STAGIAIRE")
	private Utilisateur stagiaire;

	/** idSessionParente */
	@Column(name = "ID_SESSION_PARENTE")
	private Long idSessionParente;

	/** nom */
	@Column(name = "NOM_SESSION")
	private String nomFormation;

	/** dateEntreeFormation */
	@Column(name = "DATE_ENTREE_FORMATION")
	private LocalDate dateEntreeFormation;

	/** Date de sortie : date officielle de sortie de formation.
	 * Cette date peut être différente de la date de fin de session si la stagiaire
	 * termine plus tôt que prévu */
	@Column(name = "DATE_SORTIE_FORMATION")
	private LocalDate dateSortieFormation;

	/** Date d'abandon de la formation. Ne pas confondre avec la date de sortie de formation qui est
	 * la date officielle de sortie de formation */
	@Column(name = "DATE_ABANDON")
	private LocalDate dateAbandon;
	
	/** Motif de l'abandon */
	@Column(name = "MOTIF_ABANDON")
	private String motifAbandon;
	
	/** Remarque portant sur l'abandon */
	@Column(name = "REMARQUE_ABANDON")
	private String remarqueAbandon;
	
	/** Dispositif de formation (PEOI, POEC, RNCP, etc.) */
	@Column(name="DISPOSITIF_FORMATION")
	private String dispositifFormation;
	
	/** Date prévisionnelle d'entrée en stage ou alternance */
	@Column(name="DATE_PREV_ENTREE_STAGE_ALT")
	private LocalDate datePrevEntreeStageAlt;
	
	/** Date du contrat stage ou alternance */
	@Column(name="DATE_CONTRAT_STAGE_ALT")
	private LocalDate dateContratStageAlt;
	
	/** Entreprise de mise en application */
	@ManyToOne
	@JoinColumn(name="ID_SOCIETE")
	private Societe societeApplication;
	
	/** tuteur */
	@ManyToOne
	@JoinColumn(name="ID_TUTEUR")
	private Utilisateur tuteur;

	/** duree */
	@Column(name = "DUREE")
	private Integer duree;

	/** dateMaj */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** userMaj */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** dateEmbaucheFinFormation : LocalDate */
	@Column(name = "DATE_EMBAUCHE_FF")
	private LocalDate dateEmbaucheFinFormation;
	
	/** societeFinDeFormation : String */
	@Column(name = "SOCIETE_FF")
	private String societeFinDeFormation;

	/** societeClientFinaleFinFormation : Societe */
	@Column(name = "REMARQUE_FF")
	private String remarqueFinFormation;

	/** dateEmbauche3MA : LocalDate */
	@Column(name = "DATE_EMBAUCHE_3MA")
	private LocalDate dateEmbauche3MA;
	
	/** societe3MoisApres : Societe */
	@Column(name = "SOCIETE_3MA")
	private String societe3MoisApres;

	/** dateEmbauche6MA : LocalDate */
	@Column(name = "DATE_EMBAUCHE_6MA")
	private LocalDate dateEmbauche6MA;
	
	/** societeClientFinale3MoisApres : String */
	@Column(name = "REMARQUE_3MA")
	private String remarque3MoisApres;

	/** societe6MoisApres : Societe */
	@Column(name = "SOCIETE_6MA")
	private String societe6MoisApres;

	/** remarque6MoisApres : String */
	@Column(name = "REMARQUE_6MA")
	private String remarque6MoisApres;

	@Override
	public String toString() {
		return "utilisateur=" + stagiaire + ", dateEmbaucheFinFormation=" + dateEmbaucheFinFormation
				+ ", societeFinDeFormation=" + societeFinDeFormation + ", remarqueFinFormation=" + remarqueFinFormation
				+ ", dateEmbauche3MA=" + dateEmbauche3MA + ", societe3MoisApres=" + societe3MoisApres
				+ ", dateEmbauche6MA=" + dateEmbauche6MA + ", remarque3MoisApres=" + remarque3MoisApres
				+ ", societe6MoisApres=" + societe6MoisApres + ", remarque6MoisApres=" + remarque6MoisApres;
	}

}
