package fr.diginamic.digilearning.entities.notation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BILAN_STAGIAIRE")
public class BilanStagiaire implements Comparable<BilanStagiaire> {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	/** datePrevue : LocalDate */
	@Column(name = "DATE_PREVUE")
	private LocalDate datePrevue;

	/** dateEffective : LocalDate */
	@Column(name = "DATE_EFFECTIVE")
	private LocalDate dateEffective;

	/** numero : int */
	@Column(name = "NUMERO")
	private int numero;

	/** appreciation : String */
	@Column(name = "APPRECIATION")
	private String appreciation;

	/** indiceConfiance : int */
	@Column(name = "INDICE_CONFIANCE")
	private int indiceConfiance;

	/** bulletin : Bulletin */
	@ManyToOne
	@JoinColumn(name = "ID_BULLETIN")
	private Bulletin bulletin;

	/** publication : Boolean */
	@Column(name = "PUBLICATION")
	private Boolean publication;

	/** userMaj : String */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** dateMaj : LocalDateTime */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	@Override
	public int compareTo(BilanStagiaire o) {
		if (this.getNumero() > o.getNumero()) {
			return 1;
		} else if (this.getNumero() < o.getNumero()) {
			return -1;
		}
		return 0;
	}

}
