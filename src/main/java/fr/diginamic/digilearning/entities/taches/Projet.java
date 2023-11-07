package fr.diginamic.digilearning.entities.taches;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.diginamic.digilearning.entities.PlageDate;
import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.entities.enums.StatutPreparation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "PROJET")
public class Projet implements PlageDate {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** description */
	@Column(name = "DESCRIPTION")
	private String description;

	/** dateDebut */
	@Column(name = "DATE_DEBUT")
	private LocalDate dateDebut;

	/** dateFin */
	@Column(name = "DATE_FIN")
	private LocalDate dateFin;
	
	/** dateMaj */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;
	
	/** userMaj */
	@Column(name="USER_MAJ")
	private String userMaj;

	/** ordre */
	@Column(name = "ORDRE")
	private int ordre;

	/** modalite */
	@Column(name = "MODALITE")
	@Enumerated(EnumType.STRING)
	private ModaliteProjet modalite;

	/** visibilite */
	@Enumerated(EnumType.STRING)
	private Visibilite visibilite;
	
	/** statut */
	@Column(name = "STATUT_PREPARATION")
	@Enumerated(EnumType.STRING)
	private StatutPreparation statut;

	/** dateSuppression */
	@Column(name = "DATE_SUPPRESSION")
	private LocalDate dateSuppression;

	/**
	 * Liste des tâches planifiées pour la session: cette liste peut évoluer par
	 * rapport au template avec + ou - de tâches
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projet")
	private Set<TachePlanifiee> taches = new HashSet<>();

	/** theme */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_THEME")
	private Theme theme;

	/** contributeurs */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PROJET_CONTRIBUTEURS", joinColumns = @JoinColumn(name = "ID_PROJET", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_UTL", referencedColumnName = "ID"))
	private Set<Utilisateur> contributeurs = new HashSet<>();

	/** responsable */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RESP")
	private Utilisateur responsable;

	/** archivé : oui ou non */
	@Column(name = "ARCHIVE")
	private boolean archive;

	/** label */
	@Column(name = "LABEL")
	private String label;

	/** session */
	@OneToMany(mappedBy = "projet")
	private List<Session> sessions;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Projet projet = (Projet) o;
		return Objects.equals(id, projet.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return nom + " " + ordre;
	}

	/**
	 * Getter for id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter
	 * 
	 * @return the taches
	 */
	public Set<TachePlanifiee> getTaches() {
		return taches;
	}

	/**
	 * Setter
	 * 
	 * @param taches the taches to set
	 */
	public void setTaches(Set<TachePlanifiee> taches) {
		this.taches = taches;
	}

	/**
	 * Getter
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter
	 * 
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter
	 * 
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * Getter
	 * 
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * Getter
	 * 
	 * @return the ordre
	 */
	public int getOrdre() {
		return ordre;
	}

	/**
	 * Getter
	 * 
	 * @return the modalite
	 */
	public ModaliteProjet getModalite() {
		return modalite;
	}

	/**
	 * Getter
	 * 
	 * @return the visibilite
	 */
	public Visibilite getVisibilite() {
		return visibilite;
	}

	/**
	 * Setter
	 * 
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Setter
	 * 
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Setter
	 * 
	 * @param ordre the ordre to set
	 */
	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}

	/**
	 * Setter
	 * 
	 * @param modalite the modalite to set
	 */
	public void setModalite(ModaliteProjet modalite) {
		this.modalite = modalite;
	}

	/**
	 * Setter
	 * 
	 * @param visibilite the visibilite to set
	 */
	public void setVisibilite(Visibilite visibilite) {
		this.visibilite = visibilite;
	}

	/**
	 * Getter
	 * 
	 * @return the theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Setter
	 * 
	 * @param theme the theme to set
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
	 * Getter
	 * 
	 * @return the contributeurs
	 */
	public Set<Utilisateur> getContributeurs() {
		return contributeurs;
	}

	/**
	 * Setter
	 * 
	 * @param contributeurs the contributeurs to set
	 */
	public void setContributeurs(Set<Utilisateur> contributeurs) {
		this.contributeurs = contributeurs;
	}

	/**
	 * Getter
	 * 
	 * @return the dateSuppression
	 */
	public LocalDate getDateSuppression() {
		return dateSuppression;
	}

	/**
	 * Setter
	 * 
	 * @param dateSuppression the dateSuppression to set
	 */
	public void setDateSuppression(LocalDate dateSuppression) {
		this.dateSuppression = dateSuppression;
	}

	/**
	 * Getter
	 * 
	 * @return the responsable
	 */
	public Utilisateur getResponsable() {
		return responsable;
	}

	/**
	 * Setter
	 * 
	 * @param responsable the responsable to set
	 */
	public void setResponsable(Utilisateur responsable) {
		this.responsable = responsable;
	}

	/**
	 * Getter
	 * 
	 * @return the archive
	 */
	public boolean isArchive() {
		return archive;
	}

	/**
	 * Setter
	 * 
	 * @param archive the archive to set
	 */
	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	/**
	 * Getter
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter
	 * 
	 * @return the sessions
	 */
	public List<Session> getSessions() {
		return sessions;
	}

	/**
	 * Setter
	 * 
	 * @param sessions the sessions to set
	 */
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	/**
	 * Getter
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/** Getter
	 * @return the statut
	 */
	public StatutPreparation getStatut() {
		return statut;
	}

	/** Setter
	 * @param statut the statut to set
	 */
	public void setStatut(StatutPreparation statut) {
		this.statut = statut;
	}

	/** Getter pour dateMaj
	 * @return the dateMaj
	 */
	public LocalDateTime getDateMaj() {
		return dateMaj;
	}

	/** Setter pour dateMaj
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(LocalDateTime dateMaj) {
		this.dateMaj = dateMaj;
	}

	/** Getter pour userMaj
	 * @return the userMaj
	 */
	public String getUserMaj() {
		return userMaj;
	}

	/** Setter pour userMaj
	 * @param userMaj the userMaj to set
	 */
	public void setUserMaj(String userMaj) {
		this.userMaj = userMaj;
	}
}
