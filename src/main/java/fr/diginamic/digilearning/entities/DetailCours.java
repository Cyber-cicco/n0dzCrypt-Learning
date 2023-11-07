package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "DETAIL_COURS")
@Cacheable(value = true)
public class DetailCours {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** chapitre : Chapitre */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CHAPITRE")
	private Chapitre chapitre;

}
