package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Cacheable(value = true)
@Table(name="COMPETENCE_BLOC")
public class CompetenceBloc{

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

    /** obligatoire : boolean */
	@Column(name = "OBLIGATOIRE")
    private Boolean obligatoire;

	/**Competence : competence */
	@ManyToOne
	@JoinColumn(name = "ID_COMPETENCE")
	private Competence competence;

	/**Bloc : bloc*/
	@ManyToOne
	@JoinColumn(name = "ID_BLOC")
	private Bloc bloc;

}
