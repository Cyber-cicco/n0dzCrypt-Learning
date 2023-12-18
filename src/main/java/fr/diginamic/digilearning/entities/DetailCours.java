package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * ???
 *
 * @author Abel Ciccoli
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dl_detail_cours")
public class DetailCours {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** libelle : String */
	private String libelle;

	/** chapitre : Chapitre */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chapitre_id")
	private Chapitre chapitre;

}
