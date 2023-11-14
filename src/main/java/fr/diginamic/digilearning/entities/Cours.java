package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeCours;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "COURS")
@Cacheable
public class Cours implements Comparable<Cours> {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** libelle du cours */
	@Column(name = "LIBELLE")
	private String libelle;

	/** libelleRacine : String */
	@Column(name = "LIBELLE_RACINE")
	private String libelleRacine;

	/** libelle court du cours */
	@Column(name = "LIBELLE_COURT")
	private String libelleCourt;

	/** libelle court racine du cours */
	@Column(name = "LIBELLE_COURT_RACINE")
	private String libelleCourtRacine;

	/** duree en nb de jours */
	@Column(name = "DUREE")
	private int duree;

	/** duree en nb de jours */
	@Column(name = "COEFF")
	private int coefficient;

	/** typeCours : TypeCours */
	@Column(name = "TYPE_COURS")
	@Enumerated(EnumType.STRING)
	private TypeCours typeCours;

	/** coursRef : CoursRef */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COURS_REF")
	private CoursRef coursRef;

	/**
	 * module auquel le cours appartient (Module 'Default' si la formation est sans
	 * module)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODULE")
	private Module module;

	/** formation à laquelle le cours appartient */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FORMATION")
	private Formation formation;

	/**
	 * Liste des chapitres (dans le cadre d'une description du contenu du cours)
	 */
	@OneToMany(mappedBy = "cours", fetch = FetchType.LAZY)
	private List<Chapitre> chapitres = new ArrayList<>();

	/**
	 * Liste des formateurs qui ont les compétences pour donner cette formation
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "COURS_PAR_FORMATEUR", joinColumns = @JoinColumn(name = "ID_COU", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_FORMATEUR", referencedColumnName = "ID"))
	private List<Utilisateur> formateurs = new ArrayList<>();

	/** Date de dernière mise à jour */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** Auteur de la dernière mise à jour */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** criteres : List de CritereNotation */
	//@OneToMany(mappedBy = "cours", fetch = FetchType.LAZY)
	//private Set<CritereNotation> criteres = new HashSet<>();

	/** ordre : Integer */
	@Column(name = "ORDRE")
	private Integer ordre;

	/** supprimer : indique si le cours est à supprimer ou non */
	@Transient
	private boolean supprimer;

	/** modifiable : boolean */
	@Transient
	private boolean modifiable;

	///** modalité pédagogique à laquelle le cours appartient */
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_MODALITE_PEDAGOGIQUE")
	//private ModalitePedagogique modalitePedagogique;

	/**
	 * Lien vers la classe virtuelle dans le cas où la modalité est de type classe
	 * virtuelle
	 */
	@Column(name = "LIEN_CLASSE_VIRTUELLE")
	private String lienClasseVirtuelle;

	@Override
	public int compareTo(Cours other) {
		return ordre.compareTo(other.getOrdre());
	}
}
