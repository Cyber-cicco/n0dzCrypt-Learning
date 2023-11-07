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
@Table(name = "INDISPONIBILITE_SALLE")
@Cacheable(value = true)
public class IndisponibiliteSalle extends AbstractPeriode {

	/**
	 * salle : Salle
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SALLE")
	private Salle salle;

	/**
	 * Constructeur
	 */
	public IndisponibiliteSalle() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param dateDebut date de début
	 * @param dateFin   date de fin
	 */
	public IndisponibiliteSalle(LocalDate dateDebut, LocalDate dateFin) {
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