package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "CONVOCATION")
public class Convocation {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** centre : Centre */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CENTRE")
	private Centre centre;

	/** documentConvocation : DocumentDiginamic */
	@OneToOne
	@JoinColumn(name = "ID_DOC")
	private DocumentDiginamic documentConvocation;

	/** documents : List */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DOCUMENTS_PAR_CONVOCATION", joinColumns = @JoinColumn(name = "ID_CONVOC", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_DOC", referencedColumnName = "ID"))
	private List<DocumentDiginamic> documents = new ArrayList<>();

	/** mail : String */
	@Column(name = "SUJET_MAIL")
	private String sujetMail;

	/** mail : String */
	@Column(name = "TEXTE_MAIL")
	private String texteMail;

	/** heureArrivee : String */
	@Column(name = "HEURE_ARRIVEE")
	private String heureArrivee;
}
