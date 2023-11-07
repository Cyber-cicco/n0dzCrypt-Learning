package fr.diginamic.digilearning.entities.notation;

import fr.diginamic.digilearning.entities.TypeNote;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "EVALUATION")
public class Evaluation {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** type */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TYPE_NOTE")
	private TypeNote type;

	/** bulletin */
	@ManyToOne
	@JoinColumn(name = "ID_BULLETIN")
	private Bulletin bulletin;

	@Column(name = "APPRECIATION")
	private String appreciation;

}
