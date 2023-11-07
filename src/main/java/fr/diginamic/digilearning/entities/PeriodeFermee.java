package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusValidation;
import fr.diginamic.digilearning.entities.enums.TypeEvenement;
import jakarta.persistence.*;
import org.dgn.entites.TousCentres;

import java.time.LocalDate;

@Entity
@Table(name = "PERIODE_FERME")
@Cacheable(value = true)
public class PeriodeFermee extends AbstractPeriode implements PlanningElement {

	/** type : représente le type de la période. Exemple: fermeture officielle */
	@Column(name = "TYPE", length = 50, nullable = true)
	@Enumerated(EnumType.STRING)
	private TypeEvenement type;

	/**
	 * Constructeur
	 * 
	 * @param dateDebut date de début
	 * @param dateFin date de fin
	 */
	public PeriodeFermee(LocalDate dateDebut, LocalDate dateFin) {
		super(dateDebut, dateFin);
	}

	public PeriodeFermee() {

	}

	@Override
	public String getRessource() {
		return "";
	}

	@Override
	public Centre getCentre() {
		return new TousCentres();
	}

	@Override
	public String getLibelle() {
		return "Fermeture du centre";
	}

	/**
	 * Getter
	 *
	 * @return the type
	 */
	@Override
	public TypeEvenement getType() {
		return type;
	}

	@Override
	public StatusValidation getStatutValidation() {
		return null;
	}

	/**
	 * Setter
	 *
	 * @param type the type to set
	 */
	public void setType(TypeEvenement type) {
		this.type = type;
	}
}
