package fr.diginamic.digilearning.entities.notation;

import fr.diginamic.digilearning.entities.CritereNotation;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "NOTE")
public class Note implements Comparable<Note>{

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** valeur : double */
	@Column(name = "VALEUR")
	private String valeur;

	/** critereNotation : CritereNotation */
	@ManyToOne
	@JoinColumn(name = "ID_CRITERE")
	private CritereNotation critereNotation;

	/** resultat : ResultatCours */
	@Column(name = "ID_RESULTAT")
	private Long idResultat;

	/**
	 * Constructor
	 *
	 * @param critereNotation
	 *            critère de notation
	 * @param valeur
	 *            valeur de la note
	 */
	public Note(CritereNotation critereNotation, String valeur) {
		this.critereNotation = critereNotation;
		this.valeur = valeur;
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            identifiant
	 * @param valeur
	 *            valeur de la note
	 */
	public Note(Long id, String valeur) {
		this.id = id;
		this.valeur = valeur;
	}
	

	@Override
	public int compareTo(Note o) {
		return this.critereNotation.compareTo(o.getCritereNotation());
	}

	@Override
	public String toString() {
		return critereNotation.getCours().getLibelle() + " - Type: " + critereNotation.getTypeNote().getLibelle()
				+ " - Coefficient: " + critereNotation.getCours().getCoefficient() + " - note:" + getValeur();
	}
}
