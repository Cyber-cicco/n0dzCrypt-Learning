package fr.diginamic.digilearning.entities.taches;

import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.entities.enums.StatutTache;
import fr.diginamic.digilearning.exception.FunctionalException;
import fr.diginamic.digilearning.utils.parser.Expression;
import fr.diginamic.digilearning.utils.parser.Parser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TACHE_PLANIFIEE")
public class TachePlanifiee {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** description : String */
	@Column(name = "DESCRIPTION")
	private String description;

	/** codeDateFin : String */
	@Column(name = "CODE_DATE_FIN")
	private String codeDateFin;

	/** dateCreation : Date de création */
	@Column(name = "DATE_CREATION")
	private LocalDate dateCreation;

	/** dateEcheanceInitiale : LocalDate */
	@Column(name = "DATE_ECH_INITIALE")
	private LocalDate dateEcheanceInitiale;

	/** dateEcheanceModifiee : LocalDate */
	@Column(name = "DATE_ECH_MODIFIEE")
	private LocalDate dateEcheanceModifiee;

	/** dateEcheanceRealisee : LocalDateTime */
	@Column(name = "DATE_ECH_REALISEE")
	private LocalDateTime dateEcheanceRealisee;

	/** dateSupression : LocalDateTime */
	@Column(name = "DATE_SUPPRESSION")
	private LocalDateTime dateSuppression;

	/** commentaires : String */
	@OneToMany(mappedBy = "tachePlanifiee")
	private List<TacheCommentaire> commentaires;

	/** statutTache : StatutTache */
	@Column(name = "STATUT")
	@Enumerated(EnumType.STRING)
	private StatutTache statut;

	/** listeOrigine */
	@Column(name = "LISTE_ORIGINE")
	private String listeOrigine;

	/** projet : Projet */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROJET")
	private Projet projet;

	/** rapporteur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RAPPORTEUR")
	private Utilisateur rapporteur;

	/** responsable */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RESPONSABLE")
	private Utilisateur responsable;

	/** visibilite */
	@Enumerated(EnumType.STRING)
	@Column(name = "VISIBILITE")
	private Visibilite visibilite;

	/** dureeEstimee */
	@Column(name = "DUREE_ESTIMEE")
	private String dureeEstimee;

	/** dureeReelle */
	@Column(name = "DUREE_REELLE")
	private String dureeReelle;

	@Column(name = "ICONE")
	private String icone;

	/** couleurIcone */
	@Column(name = "COULEUR_ICONE")
	private String couleurIcone;

	/** dateMaj */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** userMaj */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** retard */
	@Transient
	private boolean retard;

	/** procheEcheance */
	@Transient
	private boolean procheEcheance;

	/** enCours */
	@Transient
	private boolean enCours;

	/** aFaire */
	@Transient
	private boolean aFaire;

	/** terminee */
	@Transient
	private boolean terminee;

	/** indique si oui ou non la tâche a été terminée ce jour */
	@Transient
	private boolean termineeJour;


	/**
	 * Retourne la date d'échéance de la tâche
	 * 
	 * @return {@link LocalDate}
	 */
	public LocalDate getDateEcheance() {
		LocalDate dateEcheance = dateEcheanceInitiale;
		if (dateEcheanceModifiee != null) {
			dateEcheance = dateEcheanceModifiee;
		}
		return dateEcheance;
	}

	/**
	 * Retourne si oui ou non la tâche est mensuelle (code date fin contient M)
	 * 
	 * @return boolean
	 * @throws FunctionalException si le code date fin est renseigné mais incohérent
	 */
	public boolean isMensuelle() throws FunctionalException {
		if (codeDateFin == null) {
			return false;
		}
		Expression expr = Parser.parse(codeDateFin);
		if (expr.getMotCle() != null && expr.getMotCle().equals("M")) {
			return true;
		}
		return false;
	}

}
