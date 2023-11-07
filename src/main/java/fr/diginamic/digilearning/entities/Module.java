package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "MODULE")
@Cacheable(value = true)
public class Module {

	/** Identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** Libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** Formation associée */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FORMATION")
	private Formation formation;

	/** Liste des cours du module */
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
	@OrderBy("ordre")
	private SortedSet<Cours> cours = new TreeSet<>();

	/** Date de dernière mise à jour */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** Auteur de la dernière mise à jour */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** numero : ordre d'affichage des modules */
	@Column(name = "NUMERO")
	private Integer numero;

	/** duree : Integer */
	@Transient
	private Integer duree;


	/**
	 * Calcule la durée de la formation
	 * 
	 * @return int
	 */
	public int getDuree() {
		return cours.stream().filter(c -> !c.isSupprimer()).mapToInt(Cours::getDuree).sum();
	}

	/**
	 * Retourne true si la formation contient le cours passé en paramètre
	 * 
	 * @param cours cours à rechercher dans la formation
	 * @return boolean
	 */
	public boolean contientCoursByLibelle(Cours cours) {
		return this.cours.contains(cours);
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
		for (Cours oldCours : this.cours) {
			if (oldCours.getId() != null && oldCours.getId().equals(cours.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet d'ajouter un cours à la formation
	 * 
	 * @param cours cours
	 */
	public void ajouteCours(Cours cours) {
		this.cours.add(cours);

	}

	/**
	 * Supprime un cours de la formation
	 * 
	 * @param cours cours à supprimer
	 */
	public void supprimeCours(Cours cours) {
		this.cours.remove(cours);
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
	 * Getter for cours
	 * 
	 * @return the cours
	 */
	public SortedSet<Cours> getCours() {
		return cours;
	}

	/**
	 * Setter
	 * 
	 * @param cours the cours to set
	 */
	public void setCours(SortedSet<Cours> cours) {
		this.cours = cours;
	}

	/**
	 * Getter for dateMaj
	 * 
	 * @return the dateMaj
	 */
	public LocalDateTime getDateMaj() {
		return dateMaj;
	}

	/**
	 * Setter
	 * 
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(LocalDateTime dateMaj) {
		this.dateMaj = dateMaj;
	}

	/**
	 * Getter for userMaj
	 * 
	 * @return the userMaj
	 */
	public String getUserMaj() {
		return userMaj;
	}

	/**
	 * Setter
	 * 
	 * @param userMaj the userMaj to set
	 */
	public void setUserMaj(String userMaj) {
		this.userMaj = userMaj;
	}

	/**
	 * Getter for libelle
	 * 
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter
	 * 
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Getter for formation
	 * 
	 * @return the formation
	 */
	public Formation getFormation() {
		return formation;
	}

	/**
	 * Setter
	 * 
	 * @param formation the formation to set
	 */
	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	/**
	 * Getter
	 * 
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * Setter
	 * 
	 * @param numero the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * Setter
	 * 
	 * @param duree the duree to set
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

}
