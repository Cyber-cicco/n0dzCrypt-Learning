package fr.diginamic.digilearning.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

/**
 * Représente une période abstraite
 * 
 * @author DIGINAMIC
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPeriode implements Evenement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long id;
	/** nom : String */
	@Column(name = "NOM", length = 50)
	protected String nom;

	/** dateDebut : LocalDate */
	@Column(name = "DATE_DEB")
	protected LocalDate dateDebut;

	/** dateFin : LocalDate */
	@Column(name = "DATE_FIN")
	protected LocalDate dateFin;

	public AbstractPeriode(LocalDate dateDebut, LocalDate dateFin) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	public AbstractPeriode(LocalDate date) {
		this.dateDebut = date;
		this.dateFin = date;
	}
}
