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

	/**
	 * identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/**
	 * Nom de la session
	 */
	@Column(name = "NOM")
	private String nom;

	/**
	 * Date de début
	 */
	@Column(name = "DATE_DEB")
	private LocalDate dateDebut;

	/**
	 * Date de fin
	 */
	@Column(name = "DATE_FIN")
	private LocalDate dateFin;

	/**
	 * Formation
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FOR")
	private Formation formation;

	/**
	 * stagiaires : List de Utilisateur
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SESSION_STAGIAIRE", joinColumns = @JoinColumn(name = "ID_SES", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_STAG", referencedColumnName = "ID"))
	private Set<Utilisateur> stagiaires = new HashSet<>();


	/**
	 * nbStagiaires : Integer
	 */
	@Transient
	private Integer nbStagiaires;

	/**
	 * calculMoyenneParBlocs : boolean
	 */
	@Transient
	private String calculMoyenneParBlocs;

	@OneToMany(mappedBy = "session")
	private List<AdministrationSession> administrationSessions = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "dl_post_session",
			joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
	)
	private List<Post> postList;
	@OneToMany(mappedBy = "session")
	@Builder.Default
	private List<CoursSession> coursSessions = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "dl_salon_session",
			joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id")
	)
	private List<Salon> salonsAutorises = new ArrayList<>();

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
}