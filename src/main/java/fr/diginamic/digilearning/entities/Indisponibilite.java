package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusValidation;
import fr.diginamic.digilearning.entities.enums.TypeEvenement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "INDISPONIBILITE")
@Cacheable(value = true)
public class Indisponibilite extends AbstractPeriode {

	/** utilisateur : Utilisateur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

	/**
	 * Constructeur
	 */
	public Indisponibilite() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param dateDebut date de début
	 * @param dateFin date de fin
	 */
	public Indisponibilite(LocalDate dateDebut, LocalDate dateFin) {
		super(dateDebut, dateFin);
	}

	@Override
	public String getRessource() {
		return null;
	}

	@Override
	public Centre getCentre() {
		return null;
	}

	@Override
	public TypeEvenement getType() {
		return null;
	}

	@Override
	public StatusValidation getStatutValidation() {
		return null;
	}
}
