package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "SOCIETE")
@Cacheable(value = true)
public class Societe implements Comparable<Societe>, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6443363838062338217L;

	/** Raison sociale de la société DIGINAMIC */
	public static final String SOCIETE_DIGINAMIC = "Diginamic";

	/** diginamic */
	public static Societe diginamic;

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** adresse : String */
	@Column(name = "ADRESSE")
	private String adresse;

	/** complementAdresse : String */
	@Column(name = "COMPLEMENT_ADRESSE")
	private String complementAdresse;

	/** codePostal : Integer */
	@Column(name = "CODE_POSTAL")
	private Integer codePostal;

	/** ville : String */
	@Column(name = "VILLE")
	private String ville;

	/** siret : String */
	@Column(name = "SIRET")
	private String siret;

	/** siret : String */
	@Column(name = "NUMERO_OF")
	private String numeroOF;

	/** tvaIntraComm : String */
	@Column(name = "TVA_INTRA_COMM")
	private String tvaIntraComm;

	/** tauxTva : Float */
	@Column(name = "TAUX_TVA")
	private Float tauxTva;

	/** tjm : Integer */
	@OneToMany(mappedBy="societe")
	private Set<TjmSociete> tjms = new HashSet<>();

	/** modalitesPaiement : String */
	@Column(name = "MODALITES_PAIEMENT")
	private String modalitesPaiement;

	/** utilisateurs : List de Utilisateur */
	@OneToMany(mappedBy = "societe")
	private List<Utilisateur> utilisateurs = new ArrayList<>();

	/** sessions : List de Session */
	@OneToMany(mappedBy = "societe")
	private List<Session> sessions = new ArrayList<>();

	/** responsable : Utilisateur */
	@Column(name = "RESPONSABLE")
	private String responsable;

	/** nomLogo : String */
	@Column(name = "NOM_LOGO")
	private String nomLogo;

	/** urlSiteInternet : String */
	@Column(name = "URL_SITE_INTERNET")
	private String urlSiteInternet;

	/** demandeurEmploi : Boolean */
	@Column(name = "DEMANDEUR_EMPLOI")
	private Boolean demandeurEmploi;

	/**
	 * Constructor
	 * 
	 * @param nom nom de la société
	 */
	public Societe(String nom) {
		super();
		this.nom = nom;
	}

	/**
	 * Constructor
	 * 
	 * @param id  identifiant de la société
	 * @param nom nom de la société
	 */
	public Societe(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Societe societe = (Societe) o;
		return Objects.equals(nom, societe.nom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}

	@Override
	public int compareTo(Societe o) {
		return nom.compareTo(o.getNom());
	}

	@Override
	public String toString() {
		return nom;
	}

	/**
	 * Retourne l'adresse complète au format souhaité.
	 * 
	 * @param separator séparateur de formattage
	 * @return String
	 */
	public String getAdresseComplete(String separator) {
		StringBuilder adresseSociete = new StringBuilder();
		adresseSociete.append(nom);
		if (!adresse.isEmpty()) {
			adresseSociete.append(separator).append(adresse);
		}
		if (codePostal != null) {
			adresseSociete.append(separator).append(codePostal);
		}
		if (!ville.isEmpty()) {
			adresseSociete.append(separator).append(ville);
		}
		return adresseSociete.toString();
	}
	
	/** Retourne le Tjm actif à la date actuelle
	 * @return {@link TjmSociete}
	 */
	public TjmSociete getTjm() {
		Optional<TjmSociete> opt = this.tjms.stream().filter(t->t.getDateFinValidite()==null).findFirst();
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	/** Retourne le Tjm actif à la date passée en paramètre
	 * @return {@link TjmSociete}
	 */
	public TjmSociete getTjm(LocalDate date) {
		Optional<TjmSociete> opt = this.tjms.stream().filter(t-> DateUtils.chevauchement(t, date)).findFirst();
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	/** Retourne le Tjm dont l'identifiant est passé en paramètre
	 * @return {@link TjmSociete}
	 */
	public TjmSociete getTjm(Long id) {
		Optional<TjmSociete> opt = this.tjms.stream().filter(t->t.getId()!=null && t.getId().equals(id)).findFirst();
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

}
