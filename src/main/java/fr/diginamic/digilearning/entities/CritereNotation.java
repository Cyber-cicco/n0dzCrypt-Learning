package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "CRITERE_NOTATION")
@Cacheable(value = true)
public class CritereNotation implements Comparable<CritereNotation> {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** noteMax : Integer */
	@Column(name = "NOTE_MAX")
	private Integer noteMax;

	/** typeNote : TypeNote */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TYPE_NOTE")
	private TypeNote typeNote;

	/** bareme : String */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BAREME")
	private Bareme bareme;

	/** cours : Cours */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COURS")
	private Cours cours;

	@Override
	public int compareTo(CritereNotation o) {
		int compare = this.getTypeNote().getOrdre() - o.getTypeNote().getOrdre();
		if (compare == 0) {
			compare = this.getNom().compareTo(o.getNom());
		}
		return compare;
	}
}
