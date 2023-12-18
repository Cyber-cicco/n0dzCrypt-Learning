package fr.diginamic.digilearning.entities;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Représente un bareme de notation pour une session
 *
 * @author diginamic
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BAREME")
public class Bareme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** noteMax : Integer */
	@Column(name = "NOTE_MAX")
	private Integer noteMax;

	/** informations : String */
	@Column(name = "INFORMATIONS")
	private String informations;
	
	/** type */
	@Column(name="TYPE_BAREME")
	private String type;

	/** details : List de DetailBareme */
	@OneToMany(mappedBy = "bareme", fetch = FetchType.LAZY)
	List<DetailBareme> details = new ArrayList<>();

	/**
	 * Recherche un détail du barême en fonction de son id
	 * 
	 * @param id id du détail
	 * @return {@link DetailBareme}
	 */
	public Optional<DetailBareme> getDetail(Long id) {
		return details.stream().filter(d -> d.getId() != null && d.getId().equals(id)).findFirst();
	}

	/**
	 * Getter
	 * 
	 * @return the details
	 */
	public List<DetailBareme> getDetails() {
		return details;
	}

	/**
	 * Setter
	 * 
	 * @param details the details to set
	 */
	public void setDetails(List<DetailBareme> details) {
		this.details = details;
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
	 * @return the informations
	 */
	public String getInformations() {
		return informations;
	}

	/**
	 * Setter
	 * 
	 * @param informations the informations to set
	 */
	public void setInformations(String informations) {
		this.informations = informations;
	}

	/**
	 * Getter
	 * 
	 * @return the noteMax
	 */
	public Integer getNoteMax() {
		return noteMax;
	}

	/**
	 * Setter
	 * 
	 * @param noteMax the noteMax to set
	 */
	public void setNoteMax(Integer noteMax) {
		this.noteMax = noteMax;
	}

	/** Getter
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/** Setter
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
