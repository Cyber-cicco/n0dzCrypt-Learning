package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusAse;
import fr.diginamic.digilearning.entities.enums.TypeRessource;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur implements Comparable<Utilisateur> {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** valideur : Utilisateur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VALIDEUR")
	private Utilisateur valideur;

	/** Pour les formateurs, indique si le formateur est certifié */
	@Column(name = "CERTIF_DGN", length = 3)
	private String certifieDgn;

	@Column(name = "ds_statusAse")
	private StatusAse statusAse;

	@OneToMany(mappedBy = "utilisateur")
	private List<AdministrationSession> administrationSessions = new ArrayList<>();

	/**
	 * Type de profil: administrateur, planificateur, formateur, visiteur ou
	 * stagiaire
	 */
	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private TypeRole defaultRole;

	/** Adresse mail */
	@Column(nullable = false, unique = true, length = 30)
	private String email;

	/** Mot de passe */
	@Column(nullable = true, length = 80)
	private String password;

	/** Nom */
	@Column(nullable = false, length = 50)
	private String nom;

	/** Prénom */
	@Column(nullable = false, length = 30)
	private String prenom;

	/** Téléphone */
	@Column(nullable = true, length = 15)
	private String telephone;
	
	/** dateNaissance */
	@Column(name="DATE_NAISSANCE")
	private LocalDate dateNaissance;

	/** Indique si le compte de l'utilisateur est activé ou non */
	@Column(nullable = false)
	private boolean enabled;

	/**
	 * checkRGPD : Indique que l'utilisateur a bien accepté les conditions
	 * d'utilisation de ses données
	 */
	@Column(name = "CHECK_RGPD", nullable = false)
	private boolean checkRGPD = true;

	/**
	 * Token unique envoyé par mail à l'utilisateur dans un lien HTTP et qui permet
	 * l'activation de son compte.
	 */
	@Column(name = "TOKEN_INIT")
	private String tokenInit;

	/** Date de création du compte */
	@Column(name = "DATE_CREATION")
	private LocalDateTime dateCreation;

	/** Date de révocation du compte (sur action d'un administrateur) */
	@Column(name = "DATE_DESACTIVATION")
	private LocalDateTime dateDesactivation;

	/**
	 * Date à laquelle le compte a été vérrouillé suite à plus de 3 tentatives de
	 * connexion infructueuses
	 */
	@Column(name = "DATE_LOCKED")
	private LocalDateTime dateLocked;

	/**
	 * Compteur du nombre de tentatives de connexion infructueuses successives. Ce
	 * nb est remis à 0 si l'utilisateur se connecte correctement
	 */
	@Column(name = "NB_ESSAIS")
	private Integer nbEssais;

	/** Date de dernière mise à jour */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** Auteur de la dernière mise à jour */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/**
	 * Liste des profils de l'utilisateur (exemple: administrateur et formateur)
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_UTILISATEUR", joinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID"))
	private Set<Role> roles = new HashSet<>();

	///** indisponibilites : List de Indisponibilite */
	//@OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
	//private Set<Indisponibilite> indisponibilites = new HashSet<>();

	/**
	 * Uniquement pour un profil stagiaire: un stagiaire peut être rattaché à
	 * plusieurs sessions
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SESSION_STAGIAIRE", joinColumns = @JoinColumn(name = "ID_STAG", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_SES", referencedColumnName = "ID"))
	private Set<Session> sessionsStagiaire = new HashSet<>();

	/**
	 * Uniquement pour un planificateur : Liste des sessions pour lesquelles il est
	 * planificateur
	 */
	@Transient
	private List<Session> sessions = new ArrayList<>();
	@ManyToMany
	@JoinTable(name = "dl_utilisateur_cours",
			inverseJoinColumns = @JoinColumn(name = "id_cours", referencedColumnName = "id"),
			joinColumns = @JoinColumn(name = "id_utilisateur", referencedColumnName = "ID")
	)
	private List<Cours> coursPrepares;

	/** supprimable : boolean */
	@Transient
	private boolean supprimable;

	/** recevoirNotification */
	@Column(name = "RECEVOIR_NOTIFICATIONS")
	private boolean recevoirNotifications;


	/** dateAbandon */
	@Transient
	private LocalDate dateAbandon;

	@ManyToMany
	@JoinTable(name = "ROLE_UTILISATEUR",
			joinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID")
	)
	private List<Role> roleList;
	@ManyToOne
	@JoinColumn(name = "ds_adresse_id")
	private Adresse adresse;
	@ManyToMany
	@JoinTable(name = "dl_utilisateur_conversation",
			joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id")
	)
	private List<Conversation> conversationList = new ArrayList<>();
	@OneToMany(mappedBy = "emetteur")
	private List<Post> postList;
	@OneToMany(mappedBy = "emetteur")
	private List<Message> messageList;

	@OneToMany(mappedBy = "auteur")
	private List<PostForum> postSurForum;

	@ManyToMany
	@JoinTable(name = "whitelist",
			joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id")
	)
	private List<Salon> salonsSurWhiteList;
	@ManyToMany
	@JoinTable(name = "blacklist",
			inverseJoinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
			joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "ID")
	)
	private List<Salon> salonsSurBlacklist;

	@ManyToMany
	@JoinTable(name = "salon_moderateurs",
			inverseJoinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
			joinColumns = @JoinColumn(name = "moderateur_id", referencedColumnName = "id")
	)
	private List<Salon> salonsModeres;

	@OneToMany(mappedBy = "stagiaire")
	private List<FlagCours> flagCours;

	@OneToMany(mappedBy = "auteur")
	private List<Question> questions;

	/**
	 * Constructor
	 * 
	 */
	public Utilisateur() {

	}

	/**
	 * Constructor
	 * 
	 * @param id identifiant
	 */
	public Utilisateur(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Constructor
	 * 
	 * @param id     identifiant
	 * @param email  email
	 * @param nom    nom
	 * @param prenom prénom
	 */
	public Utilisateur(Long id, String email, String nom, String prenom) {
		super();
		this.id = id;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
	}

	public TypeRessource getTypeRessource() {
		return TypeRessource.UTILISATEUR;
	}

	public String getAttribute() {
		return "formateur.email";
	}

	public String getValue() {
		return this.email;
	}

	public Optional<Session> getSessionStagiaire(){
		return sessionsStagiaire.stream().filter(session -> session.getDateFin().isAfter(LocalDate.now())).findFirst();
	}

	public String getFullName(){
		return nom.toUpperCase() + " " + prenom;
	}

	/**
	 * Retourne si oui ou non l'utilisateur a le type de rôle passé en paramètre
	 * 
	 * @param type de role
	 * @return boolean
	 */
	public boolean hasRole(TypeRole type) {
		return roles.stream().filter(r -> r.getAuthority().equals(type.name())).count() >= 1;
	}

	/**
	 * Retourne si oui ou non l'utilisateur est un admin
	 * 
	 * @return boolean
	 */
	public boolean isAdministrateur() {
		return hasRole(TypeRole.ROLE_ADMINISTRATEUR);
	}

	/**
	 * Retourne si oui ou non l'utilisateur est un planificateur
	 * 
	 * @return boolean
	 */
	public boolean isPlanificateur() {
		return hasRole(TypeRole.ROLE_PLANIFICATEUR);
	}

	/**
	 * Retourne si oui ou non l'utilisateur est un formateur
	 * 
	 * @return boolean
	 */
	public boolean isFormateur() {
		return hasRole(TypeRole.ROLE_FORMATEUR);
	}

	/**
	 * Retourne si oui ou non l'utilisateur est un stagiaire
	 * 
	 * @return boolean
	 */
	public boolean isStagiaire() {
		return hasRole(TypeRole.ROLE_STAGIAIRE);
	}

	/**
	 * Retourne si oui ou non l'utilisateur est un visiteur
	 * 
	 * @return boolean
	 */
	public boolean isVisiteur() {
		return hasRole(TypeRole.ROLE_VISITEUR);
	}

	/**
	 * Retourne si oui ou non l'utilisateur est un visiteur admin
	 * 
	 * @return boolean
	 */
	public boolean isVisiteurAdmin() {
		return hasRole(TypeRole.ROLE_VISITEUR_ADMIN);
	}

	/** Retourne si oui ou non l'utilisateur est un contact
	 * @return boolean
	 */
	public boolean isContact() {
		return hasRole(TypeRole.ROLE_CONTACT);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Utilisateur that = (Utilisateur) o;
		return Objects.equals(email, that.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return "Utilisateur [email=" + email + "]";
	}

	@Override
	public int compareTo(Utilisateur o) {
		Comparator<Utilisateur> employeeNameComparator = Comparator.comparing(Utilisateur::getNom)
				.thenComparing(Utilisateur::getPrenom);
		return employeeNameComparator.compare(this, o);
	}
}
