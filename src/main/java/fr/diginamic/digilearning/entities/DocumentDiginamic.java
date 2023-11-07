package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "DOCUMENT_DIGINAMIC")
@Cacheable(value = true)
public class DocumentDiginamic {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** type : String */
	@Column(name = "TYPE")
	private String type;

	/** contentType : String */
	@Column(name = "CONTENT_TYPE")
	private String contentType;

	/** centre : Centre */
	@ManyToOne
	@JoinColumn(name = "ID_CEN")
	private Centre centre;

	/** file : byte[] */
	@Lob
	@Column(name = "FILE")
	private byte[] file;

	/** selected : boolean */
	@Transient
	private boolean selected;

}
