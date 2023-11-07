package fr.diginamic.digilearning.entities;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ds_adresse")
public class Alerte {

	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** code */
	private String code;

	/** description */
	private String description;

	/** sujetMail */
	@Column(name = "SUJET_MAIL")
	private String sujetMail;

	/** message */
	private String message;

	/** delai */
	private Integer delai;

	/** utilisateurs */
	@ManyToMany
	@JoinTable(name = "ALERTE_UTILISATEUR", joinColumns = @JoinColumn(name = "ID_ALERTE", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID"))
	private List<Utilisateur> utilisateurs = new ArrayList<>();

	/**
	 * Getter
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter
	 * 
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Getter
	 * 
	 * @return the utilisateurs
	 */
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	/**
	 * Setter
	 * 
	 * @param utilisateurs the utilisateurs to set
	 */
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
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
	 * @return the sujetMail
	 */
	public String getSujetMail() {
		return sujetMail;
	}

	/**
	 * Setter
	 * 
	 * @param sujetMail the sujetMail to set
	 */
	public void setSujetMail(String sujetMail) {
		this.sujetMail = sujetMail;
	}

	/**
	 * Getter
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter
	 * 
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter
	 * 
	 * @return the delai
	 */
	public Integer getDelai() {
		return delai;
	}

	/**
	 * Setter
	 * 
	 * @param delai the delai to set
	 */
	public void setDelai(Integer delai) {
		this.delai = delai;
	}
}
