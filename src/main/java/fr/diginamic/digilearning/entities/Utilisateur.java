package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusAse;
import fr.diginamic.digilearning.entities.enums.TypeRessource;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Représente une utilisateur de l'application
 *
 * @author DIGINAMIC
 */
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

	@OneToMany(mappedBy = "utilisateur")
	private List<AdministrationSession> administrationSessions = new ArrayList<>();


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

	/**
	 * Liste des profils de l'utilisateur (exemple: administrateur et formateur)
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_UTILISATEUR", joinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID"))
	private Set<Role> roles = new HashSet<>();
	/**
	 * Uniquement pour un profil stagiaire: un stagiaire peut être rattaché à
	 * plusieurs sessions
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SESSION_STAGIAIRE", joinColumns = @JoinColumn(name = "ID_STAG", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_SES", referencedColumnName = "ID"))
	private Set<Session> sessionsStagiaire = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "dl_utilisateur_cours",
			inverseJoinColumns = @JoinColumn(name = "id_cours", referencedColumnName = "id"),
			joinColumns = @JoinColumn(name = "id_utilisateur", referencedColumnName = "ID")
	)
	private List<Cours> coursPrepares;

	@ManyToMany
	@JoinTable(name = "ROLE_UTILISATEUR",
			joinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID")
	)
	private List<Role> roleList;
	@ManyToMany
	@JoinTable(name = "dl_utilisateur_conversation",
			joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id")
	)
	private List<Conversation> conversationList = new ArrayList<>();
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

	@Column(name = "dl_banned")
	private Boolean banned;

	@OneToMany(mappedBy = "auteur")
	private List<Question> questions;

	@OneToMany(mappedBy = "utilisateur")
	private List<QCMPasse> qcmPasses;

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

	public Optional<Session> getSessionCourante(){
		return sessionsStagiaire.stream().filter(session -> session.getDateFin().isAfter(LocalDate.now())).findFirst();
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

	public String getPersonalDirectory(){
		return nom + id + "/";
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
