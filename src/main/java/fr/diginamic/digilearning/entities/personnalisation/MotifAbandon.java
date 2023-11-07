package fr.diginamic.digilearning.entities.personnalisation;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "MOTIF_ABANDON")
public class MotifAbandon {
	
	/** Id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** Libellé */
	@Column(name = "LIBELLE")
	private String libelle;

}
