package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "LIVRET_STAGIAIRE")
public class LivretStagiaire {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** utilisateur : Utilisateur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

	/** societeFinDeFormation : String */
	@Column(name = "SOCIETE_FF")
	private String societeFinDeFormation;

	/** recruteFinFormation : Boolean */
	@Column(name = "RECRUTE_FF")
	private Boolean recruteFinFormation;

	/** societeClientFinaleFinFormation : Societe */
	@Column(name = "SOCIETE_CLIENT_FINALE_FF")
	private String societeClientFinaleFinFormation;

	/** dateEmbaucheFinFormation : LocalDate */
	@Column(name = "DATE_EMBAUCHE_FF")
	private LocalDate dateEmbaucheFinFormation;

	/** societe3MoisApres : Societe */
	@Column(name = "SOCIETE_3MA")
	private String societe3MoisApres;

	/** recrute3MoisApres : Boolean */
	@Column(name = "RECRUTE_3MA")
	private Boolean recrute3MoisApres;

	/** societeClientFinale3MoisApres : Societe */
	@Column(name = "SOCIETE_CLIENT_FINALE_3MA")
	private String societeClientFinale3MoisApres;

	/** dateEmbauche3MA : LocalDate */
	@Column(name = "DATE_EMBAUCHE_3MA")
	private LocalDate dateEmbauche3MA;

	/** societe6MoisApres : Societe */
	@Column(name = "SOCIETE_6MA")
	private String societe6MoisApres;

	/** recrute6MoisApres : Boolean */
	@Column(name = "RECRUTE_6MA")
	private Boolean recrute6MoisApres;

	/** societeClientFinale6MoisApres : Societe */
	@Column(name = "SOCIETE_CLIENT_FINALE_6MA")
	private String societeClientFinale6MoisApres;

	/** dateEmbauche6MA : LocalDate */
	@Column(name = "DATE_EMBAUCHE_6MA")
	private LocalDate dateEmbauche6MA;

	/** remarque : String */
	@Column(name = "REMARQUE")
	private String remarque;
}
