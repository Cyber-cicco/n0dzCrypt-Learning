package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusValidation;
import fr.diginamic.digilearning.entities.enums.TypeEvenement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "COURS_PLANIFIE")
public class CoursPlanifie extends CoursReel implements Evenement, PlanningElement {

	/** Coefficient pour la notation */
	@Column(name = "COEFF")
	private int coefficient;

	/** Session associée */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SESSION")
	private Session session;

	/** formateurDispo : boolean */
	@Column(name = "FORMATEUR_DISPO")
	private boolean formateurDispo;

	/** salleDispo : boolean */
	@Column(name = "SALLE_DISPO")
	private boolean salleDispo;

	/** compteVirtuelDispo : boolean */
	@Column(name = "COMPTE_VIRTUEL_DISPO")
	private boolean compteVirtuelDispo;

	/** statutValidation : StatutValidation */
	@Column(name = "STATUT_VALIDATION")
	@Enumerated(EnumType.STRING)
	private StatusValidation statutValidation;

	/** commentaires : String */
	@Column(name = "COMMENTAIRES")
	private String commentaires;

	/**
	 * Compte d'animation d'une classe virtuelle dans le cas où la modalité est de
	 * type classe virtuelle
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CPV")
	private CompteVirtuel compteVirtuel;

	/** formateursDisponibles : List de Utilisateur */
	@Transient
	private List<Utilisateur> formateursDisponibles = new ArrayList<>();

	/**
	 * Constructeur
	 */
	public CoursPlanifie() {
		super();
	}

	/**
	 * Constructeur
	 * 
	 * @param id identifiant
	 */
	public CoursPlanifie(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Constructeur
	 * 
	 * @param libelle   libellé
	 * @param dateDebut date de début
	 * @param dateFin   date de fin
	 * @param session   session
	 */
	public CoursPlanifie(String libelle, LocalDate dateDebut, LocalDate dateFin, Session session) {
		super();
		this.libelle = libelle;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.session = session;
	}

	/**
	 * Constructeur
	 * 
	 * @param id        identifiant
	 * @param libelle   libellé
	 * @param dateDebut date de début
	 * @param dateFin   date de fin
	 * @param session   session
	 */
	public CoursPlanifie(Long id, String libelle, LocalDate dateDebut, LocalDate dateFin, Session session) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.session = session;
	}

	/**
	 * Constructor
	 * 
	 * @param id          identifiant
	 * @param libelle     libellé
	 * @param coefficient coefficient
	 * @param dateDebut   date de début
	 * @param dateFin     date de fin
	 * @param session     Session
	 * @param formateur   formateur
	 */
	public CoursPlanifie(Long id, String libelle, int coefficient, LocalDate dateDebut, LocalDate dateFin,
			Session session, Utilisateur formateur) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.coefficient = coefficient;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.session = session;
		this.formateur = formateur;
	}

	/**
	 * Constructor
	 * 
	 * @param id          identifiant
	 * @param libelle     libellé
	 * @param coefficient coefficient
	 * @param dateDebut   date de début
	 * @param dateFin     date de fin
	 * @param cours       cours
	 * @param formateur   formateur
	 */
	public CoursPlanifie(Long id, String libelle, int coefficient, LocalDate dateDebut, LocalDate dateFin, Cours cours,
			Utilisateur formateur) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.coefficient = coefficient;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.cours = cours;
		this.formateur = formateur;
	}
	
	/** Retourne le TJM pour ce cours (dépend du formateur et de la modalité pédagogique).
	 * @return float
	 */
	public float getTjm() {
		if (this.formateur!=null) {
			if (this.modalitePedagogique.isTjmPartiel()) {
				return this.formateur.getSociete().getTjm(this.session.getDateDebut()).getValeurPartielle();
			}
			else {
				return this.formateur.getSociete().getTjm(this.session.getDateDebut()).getValeur();
			}
		}
		return 0.0f;
	}

	public boolean isSuivi() {
		return true;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CoursPlanifie [libelle=" + libelle + ", duree=" + duree + ", dateDebut=" + dateDebut + ", dateFin="
				+ dateFin + ", formateur=" + formateur + ", salle=" + salle + "]";
	}

	@Override
	public String getNom() {
		return cours.getLibelleCourt();
	}

	@Override
	public String getRessource() {
		return null;
	}

	@Override
	public Centre getCentre() {
		return salle.getCentre();
	}

	@Override
	public TypeEvenement getType() {
		return TypeEvenement.COURS;
	}

	/**
	 * Classe qui construit des instances de {@link CoursPlanifie}
	 * 
	 * @author DIGINAMIC
	 *
	 */
	public static class CoursPlanifieBuilder {

		/**
		 * Construit une nouvelle instance de cours planifié à partir d'une instance
		 * passée en paramètre
		 * 
		 * @param origin origine
		 * @return {@link CoursPlanifie}
		 */
		public static CoursPlanifie getInstance(CoursPlanifie origin) {
			CoursPlanifie coursPlanifie = new CoursPlanifie();
			coursPlanifie.setId(origin.getId());
			coursPlanifie.setLibelle(origin.getLibelle());
			coursPlanifie.setCoefficient(origin.getCoefficient());
			coursPlanifie.setDuree(origin.getDuree());
			coursPlanifie.setDateDebut(origin.getDateDebut());
			coursPlanifie.setDateFin(origin.getDateFin());
			coursPlanifie.setFormateur(origin.getFormateur());
			coursPlanifie.setFormateurDispo(origin.isFormateurDispo());
			coursPlanifie.setSalle(origin.getSalle());
			coursPlanifie.setSalleDispo(origin.isSalleDispo());
			coursPlanifie.setCours(origin.getCours());
			coursPlanifie.setStatutValidation(origin.getStatutValidation());
			coursPlanifie.setCommentaires(origin.getCommentaires());
			coursPlanifie.setModalitePedagogique(origin.getModalitePedagogique());
			coursPlanifie.setLienClasseVirtuelle(origin.getLienClasseVirtuelle());
			return coursPlanifie;
		}

		/**
		 * Construit une instance de cours planifié à partir d'un cours, d'une salle et
		 * d'un statut de validation
		 * 
		 * @param cours  cours
		 * @param salle  salle
		 * @param statut statut de validation
		 * @return {@link CoursPlanifie}
		 */
		public static CoursPlanifie getInstance(Cours cours, Salle salle, StatusValidation statut) {
			CoursPlanifie coursPlanifie = new CoursPlanifie();
			coursPlanifie.setSalle(salle);
			coursPlanifie.setLibelle(cours.getLibelle());
			coursPlanifie.setCoefficient(cours.getCoefficient());
			coursPlanifie.setDuree(cours.getDuree());
			coursPlanifie.setCours(cours);
			coursPlanifie.setStatutValidation(statut);
			coursPlanifie.setModalitePedagogique(cours.getModalitePedagogique());
			coursPlanifie.setLienClasseVirtuelle(cours.getLienClasseVirtuelle());
			return coursPlanifie;
		}
	}
}
