package fr.diginamic.digilearning.entities.taches;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TACHE")
public class Tache {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** description */
	private String description;

	/** Code date fin. Exemple: DD-2, DF+3 */
	@Column(name = "CODE_DATE_FIN")
	private String codeDateFin;

	/** codeResponsable : RAF, COORDINATEUR, etc. */
	@Column(name = "CODE_RESPONSABLE", nullable = true)
	@Enumerated(EnumType.STRING)
	private CodeResponsable codeResponsable;

	/** todoListe : TodoListe */
	@ManyToOne
	@JoinColumn(name = "ID_LISTE")
	private ListeModele listeModele;

}
