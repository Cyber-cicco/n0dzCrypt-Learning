package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Représente une session pour un groupe donné
 * Possède toutes les informations qui avaient été nécessaires pour le SID
 *
 * @author DIGINAMIC
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "SESSION")
public class Session {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** centre : Centre */
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_CENTRE")
	//private Centre centre;

	/** societe : Societe */
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_SOCIETE")
	//private Societe societe;

	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_SOCIETE_BDC")
	//private EmetteurBon emetteurBDCParDefaut;

	/** Nom de la session */
	@Column(name = "NOM")
	private String nom;

	/** Date de début */
	@Column(name = "DATE_DEB")
	private LocalDate dateDebut;

	/** Date de fin */
	@Column(name = "DATE_FIN")
	private LocalDate dateFin;

	/** Formation */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FOR")
	private Formation formation;

	///** Liste des dates fermées spécifique pour cette session */
	//@OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
	//private Set<PeriodeFermeeSession> fermes = new HashSet<>();

	///** bilans : List de BilanSession */
	//@OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
	//private List<BilanSession> bilans = new ArrayList<>();

	///** Salle d'informatique par défaut dans laquelle aura lieu la formation */
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_SAL")
	//private Salle salle;

	/** dateMaj : LocalDateTime */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** userMaj : String */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** previous : Session */
	@Column(name = "ID_PREVIOUS")
	private Long idPrevious;

	/** parent : Session */
	@Column(name = "ID_PARENT")
	private Long idParent;

	/** next : Session */
	@Column(name = "ID_NEXT")
	private Long idNext;

	/** statutValidation : StatutValidation */
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT_VALIDATION")
	private StatusValidation statutValidation;

	/** statutPreparation : StatutPreparation */
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT_PREPARATION")
	private StatutPreparation statutPreparation;

	/** stagiaires : List de Utilisateur */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SESSION_STAGIAIRE", joinColumns = @JoinColumn(name = "ID_SES", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_STAG", referencedColumnName = "ID"))
	private Set<Utilisateur> stagiaires = new HashSet<>();

	/** planificateurs : List de Utilisateur */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SESSION_PLANIFICATEUR", joinColumns = @JoinColumn(name = "ID_SES", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_USER", referencedColumnName = "ID"))
	private Set<Utilisateur> planificateurs = new HashSet<>();

	/** staffé sans : boolean */
	@Column(name = "SANS_CONFLIT")
	private boolean staffeSansConflit;

	/** nbBilans : Integer */
	@Column(name = "NB_BILANS")
	private Integer nbBilans = 3;

	/** synthese : String */
	@Column(name = "SYNTHESE")
	private String synthese;

	/** synthese : String */
	@Column(name = "TYPE_EMARGEMENT")
	@Enumerated(EnumType.STRING)
	private TypeEmargement typeEmargement;

	/** nbStagiaires : Integer */
	@Transient
	private Integer nbStagiaires;

	/** calculMoyenneParBlocs : boolean */
	@Transient
	private String calculMoyenneParBlocs;

	@OneToMany(mappedBy = "session")
	private List<AdministrationSession> administrationSessions = new ArrayList<>();

	///** listeModele */
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_LISTE")
	//private ListeModele listeModele;

	///** projet : Projet */
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_PROJET")
	//private Projet projet;

	/** dureeFacturable */
	@Column(name="DUREE_FACTURABLE")
	private int dureeFacturable;

	@ManyToMany
	@JoinTable(name = "dl_post_session",
			joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
	)
	private List<Post> postList;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Session session = (Session) o;
		return Objects.equals(nom, session.nom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}

	/**
	 * Retourne si oui ou non la personne dont l'email est passé en paramètre est
	 * planificateur sur la session.
	 *
	 * @param email email du planificateur
	 * @return boolean
	 */
	public boolean hasPlanificateur(String email) {

		return !email.isEmpty() && !planificateurs.isEmpty()
				&& planificateurs.stream().anyMatch(p -> p.getEmail().equals(email));
	}

	/**
	 * Recherche un stagiaire de la session en fonction de son email
	 * 
	 * @param email email du stagiaire
	 * @return {@link Utilisateur}
	 */
	public Utilisateur getStagiaire(String email) {
		Optional<Utilisateur> stagiaire = stagiaires.stream().filter(s -> s.getEmail().equals(email)).findFirst();
		if (stagiaire.isPresent()) {
			return stagiaire.get();
		}
		return null;
	}

	///**
	// * Retourne si oui ou non la session est affectée à la société passée en
	// * paramètre (i.e. la session est commandée par la société ou un des stagiaires
	// * est associée à la société)
	// *
	// * @param societe société
	// * @return boolean
	// */
	//public boolean hasSociete(Societe societe) {

	//	return (this.societe != null && societe != null && (this.societe.equals(societe))) || (societe != null
	//			&& stagiaires.stream().filter(s -> s.getSociete().equals(societe)).findAny().isPresent());
	//}

	//@Override
	//public TypeEvenement getType() {
	//	return TypeEvenement.SESSION;
	//}

	//@Override
	//public String getRessource() {
	//	if (centre != null) {
	//		return centre.getNom();
	//	}
	//	return "";
	//}
	/**
	 * Getter
	 * 
	 * @return the stagiaires
	 */
	public Set<Utilisateur> getStagiaires() {
		return stagiaires;
	}

	/**
	 * Setter
	 * 
	 * @param stagiaires the stagiaires to set
	 */
	public void setStagiaires(Set<Utilisateur> stagiaires) {
		this.stagiaires = stagiaires;
	}

	/**
	 * Getter
	 * 
	 * @return the nbBilans
	 */
	public Integer getNbBilans() {
		return nbBilans;
	}

	/**
	 * Setter
	 * 
	 * @param nbBilans the nbBilans to set
	 */
	public void setNbBilans(Integer nbBilans) {
		this.nbBilans = nbBilans;
	}

	///**
	// * Getter
	// *
	// * @return the bilans
	// */
	//public List<BilanSession> getBilans() {
	//	return bilans;
	//}

	///**
	// * Setter
	// *
	// * @param bilans the bilans to set
	// */
	//public void setBilans(List<BilanSession> bilans) {
	//	this.bilans = bilans;
	//}

	/**
	 * Getter
	 * 
	 * @return the planificateurs
	 */
	public Set<Utilisateur> getPlanificateurs() {
		return planificateurs;
	}

	/**
	 * Setter
	 * 
	 * @param planificateurs the planificateurs to set
	 */
	public void setPlanificateurs(Set<Utilisateur> planificateurs) {
		this.planificateurs = planificateurs;
	}

	/**
	 * Getter
	 * 
	 * @return the statutPreparation
	 */
	public StatutPreparation getStatutPreparation() {
		return statutPreparation;
	}

	/**
	 * Setter
	 * 
	 * @param statutPreparation the statutPreparation to set
	 */
	public void setStatutPreparation(StatutPreparation statutPreparation) {
		this.statutPreparation = statutPreparation;
	}

	/**
	 * Getter
	 * 
	 * @return the nbStagiaires
	 */
	public Integer getNbStagiaires() {
		return nbStagiaires;
	}

	/**
	 * Setter
	 * 
	 * @param nbStagiaires the nbStagiaires to set
	 */
	public void setNbStagiaires(Integer nbStagiaires) {
		this.nbStagiaires = nbStagiaires;
	}

	/**
	 * Getter
	 * 
	 * @return the idParent
	 */
	public Long getIdParent() {
		return idParent;
	}

	/**
	 * Setter
	 * 
	 * @param idParent the idParent to set
	 */
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	/**
	 * Getter
	 * 
	 * @return the idNext
	 */
	public Long getIdNext() {
		return idNext;
	}

	/**
	 * Setter
	 * 
	 * @param idNext the idNext to set
	 */
	public void setIdNext(Long idNext) {
		this.idNext = idNext;
	}

	/**
	 * Getter
	 * 
	 * @return the idPrevious
	 */
	public Long getIdPrevious() {
		return idPrevious;
	}

	/**
	 * Setter
	 * 
	 * @param idPrevious the idPrevious to set
	 */
	public void setIdPrevious(Long idPrevious) {
		this.idPrevious = idPrevious;
	}

	/**
	 * Getter
	 * 
	 * @return the synthese
	 */
	public String getSynthese() {
		return synthese;
	}

	/**
	 * Setter
	 * 
	 * @param synthese the synthese to set
	 */
	public void setSynthese(String synthese) {
		this.synthese = synthese;
	}

	/**
	 * Getter
	 *
	 * @return the calculMoyenneParBlocs
	 */
	public String getCalculMoyenneParBlocs() {
		return calculMoyenneParBlocs;
	}

	/**
	 * Setter
	 *
	 * @param calculMoyenneParBlocs the calculMoyenneParBlocs to set
	 */
	public void setCalculMoyenneParBlocs(String calculMoyenneParBlocs) {
		this.calculMoyenneParBlocs = calculMoyenneParBlocs;
	}

	/**
	 * Getter
	 * 
	 * @return the typeEmargement
	 */
	public TypeEmargement getTypeEmargement() {
		return typeEmargement;
	}

	/**
	 * Setter
	 * 
	 * @param typeEmargement the typeEmargement to set
	 */
	public void setTypeEmargement(TypeEmargement typeEmargement) {
		this.typeEmargement = typeEmargement;
	}

	///**
	// * Getter
	// *
	// * @return the listeModele
	// */
	//public ListeModele getListeModele() {
	//	return listeModele;
	//}

	///**
	// * Getter
	// *
	// * @return the projet
	// */
	//public Projet getProjet() {
	//	return projet;
	//}

	///**
	// * Setter
	// *
	// * @param listeModele the listeModele to set
	// */
	//public void setListeModele(ListeModele listeModele) {
	//	this.listeModele = listeModele;
	//}

	///**
	// * Setter
	// *
	// * @param projet the projet to set
	// */
	//public void setProjet(Projet projet) {
	//	this.projet = projet;
	//}

	/** Getter
	 * @return the dureeFacturable
	 */
	public int getDureeFacturable() {
		return dureeFacturable;
	}

	/** Setter
	 * @param dureeFacturable the dureeFacturable to set
	 */
	public void setDureeFacturable(int dureeFacturable) {
		this.dureeFacturable = dureeFacturable;
	}
}
