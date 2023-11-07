package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "FORMATION")
@Cacheable
public class Formation {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom de la formation */
	@Column(name = "NOM")
	private String nom;

	/** nom de la formation */
	@Column(name = "NOM_COURT")
	private String nomCourt;

	/** reference : String */
	@Column(name = "REFERENCE", length = 10)
	private String reference;
	/**
	 * Indique par OUI ou par NON si la formation donne lieu à la délivrance d'un
	 * titre pro.
	 */
	@Column(name = "TITRE")
	private String titre;

	/**
	 * Date de fin de la formation dans le cas où un admin supprime une formation
	 * pour laquelle des sessions ont déjà eu lieu
	 */
	@Column(name = "DATE_FIN")
	private LocalDate dateFin;

	/** Indique si la formation est avec module ou non */
	@Column(name = "AVEC_MODULE")
	private boolean avecModule;

	/**
	 * Liste des modules (une formation sans module a malgré tout un unique module,
	 * masqué à l'affichage, qui s'appelle Default)
	 */
	@OneToMany(mappedBy = "formation", fetch = FetchType.LAZY)
	private Set<Module> modules = new HashSet<>();

	/** Date de dernière mise à jour */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** Auteur de la dernière mise à jour */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** version : Integer */
	@Column(name = "VERSION")
	private String version;

	/** Durée en jours : Integer */
	@Column(name = "DUREE")
	private Integer duree;

	/** Durée facturable */
	@Column(name = "DUREE_FACTURABLE")
	private Integer dureeFacturable;

	/**
	 * Représente la version précédente d'une formation (si la formation est la
	 * première alors previous vaut NULL)
	 */
	@Column(name = "ID_PREVIOUS")
	private Long idPrevious;

	/**
	 * Représente la version d'origine de la formation (celle pour laquelle id ==
	 * idParent)
	 */
	@Column(name = "ID_PARENT")
	private Long idParent;

	/**
	 * Représente la version suivante d'une formation (si la formation est la
	 * dernière alors next vaut NULL)
	 */
	@Column(name = "ID_NEXT")
	private Long idNext;

	/** objectifsPedagogiques : objectifs pédagogiques */
	@Column(name = "OBJS_PEDAGOGIQUES")
	private String objectifsPedagogiques;

	/** Texte pré-requis apparaissant dans le syllabus */
	@Column(name = "PRE_REQUIS")
	private String preRequis;

	/** Texte de présentation formation apparaissant dans le syllabus */
	@Column(name = "INTRO_SYLLABUS")
	private String introSyllabus;

	/** projetFin : projet de fin de formation */
	@Column(name = "PROJET_FIN")
	private String projetFin;

	/**
	 * evalAmont : Phrase concernant l'évaluation amont et qui apparait dans le
	 * bilan de fin de formation du stagiaire en entête de document
	 */
	@Column(name = "EVAL_AMONT")
	private String evalAmont;

	/** nom de la certification (facultatif) */
	@Column(name = "REFERENCE_EXAMEN")
	private String referenceExamen;

	/** editeurCertification : String */
	@Column(name = "EDITEUR_CERTIFICATION")
	private String editeurCertification;

	/** nom de la certification (facultatif) */
	@Column(name = "NOM_CERTIF")
	private String nomCertification;

	/** nom de la certification (facultatif) */
	@Column(name = "NB_CERTIFS_BLANCHES")
	private Integer nbCertifsBlanches;

	/**
	 * scoreObtentionCertif : Score permettant l'obtention de la certification
	 */
	@Column(name = "SCORE_OBTENTION_CERTIF")
	private Integer scoreObtentionCertif;

	/**
	 * infosCertif : Documentation concernant la certification
	 */
	@Column(name = "INFOS_CERTIF")
	private String infosCertif;

	/** nom du logo de la certification (facultatif) */
	@Column(name = "NOM_LOGO_CERTIF")
	private String nomLogoCertification;

	/**
	 * Retourne true si la formation contient le cours passé en paramètre
	 * 
	 * @param cours cours à rechercher dans la formation
	 * @return boolean
	 */
	public boolean contientCoursByLibelle(Cours cours) {
		return modules.stream().filter(m -> m.getCours().contains(cours)).count() > 0;
	}

	/**
	 * Retourne la liste des cours dont le libellé racine est passé en paramètre
	 * 
	 * @param libelleRacine libellé racine des cours à rechercher
	 * @return List de {@link Cours}
	 */
	public List<Cours> getCoursByLibelleRacine(String libelleRacine) {
		return modules.stream().flatMap(m -> m.getCours().stream())
				.filter(c -> c.getLibelleRacine().equals(libelleRacine)).toList();
	}
	
	/** Retourne un cours par libellé
	 * @param libelle libellé du cours
	 * @return Optional
	 */
	public Optional<Cours> getCoursByLibelle(String libelle) {
		return modules.stream().flatMap(m -> m.getCours().stream())
				.filter(c -> c.getLibelle().equals(libelle)).findFirst();
	}

	/**
	 * Récupère la liste de tous les cours d'une formation
	 * 
	 * @return List de Cours
	 */
	public List<Cours> getCours() {
		return modules.stream().flatMap(m -> m.getCours().stream()).toList();
	}

	/**
	 * Retourne true si la formation contient le cours passé en paramètre
	 * 
	 * @param cours cours à rechercher dans la formation
	 * @return boolean
	 */
	public boolean contientCoursById(Cours cours) {
		if (cours.getId() == null) {
			return false;
		}
		for (Module module : modules) {
			for (Cours oldCours : module.getCours()) {
				if (oldCours.getId() != null && oldCours.getId().equals(cours.getId())) {
					return true;
				}
			}
		}
		return false;
	}
}
