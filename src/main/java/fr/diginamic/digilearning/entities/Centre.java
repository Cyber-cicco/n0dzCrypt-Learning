package fr.diginamic.digilearning.entities;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "CENTRE")
@Cacheable(value = true)
public class Centre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long id;
	/**
	 * nom : String
	 */
	@Column(name = "NOM")
	private String nom;
	@OneToMany(mappedBy = "centre")
	private List<Convocation> convocation;

	/**
	 * nom court: String
	 */
	@Column(name = "NOM_COURT")
	private String nomCourt;

	/**
	 * adresse
	 */
	@Column(name = "ADRESSE")
	private String adresse;

	/**
	 * responsable : String
	 */
	@Column(name = "RESPONSABLE")
	private String responsable;

	/**
	 * mailResponsableAdmin : String
	 */
	@Column(name = "MAIL_RESPONSABLE_ADMIN")
	private String mailResponsableAdmin;

	/**
	 * titre du responsable (exemple Directeur, Responsable pédagogique, etc.)
	 */
	@Column(name = "QUALITE")
	private String qualite;

	/**
	 * Nom de l'image (signature numérisée) à insérer dans les documents
	 * administratifs
	 */
	@Column(name = "NOM_IMAGE_SIGNATURE")
	private String nomImageSignature;

	public Centre(String libelle){
		this.nom = libelle;
	}

}