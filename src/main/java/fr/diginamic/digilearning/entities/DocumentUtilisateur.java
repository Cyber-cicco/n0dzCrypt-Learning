package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "DOCUMENT_UTILISATEUR")
public class DocumentUtilisateur {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** contentType : String */
	@Column(name = "CONTENT_TYPE")
	private String contentType;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** libelle : String */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;
}
