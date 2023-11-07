package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MappedSuperclass
public abstract class CoursReel implements PlageDate {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected Long id;

	/** cours */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COURS")
	protected Cours cours;

	/** libelle */
	@Column(name = "LIBELLE")
	protected String libelle;

	/** dateDebut */
	@Column(name = "DATE_DEBUT")
	protected LocalDate dateDebut;

	/** dateFin */
	@Column(name = "DATE_FIN")
	protected LocalDate dateFin;

	/** duree */
	@Column(name = "DUREE")
	protected int duree;

	/** modalitePedagogique */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODALITE_PEDAGOGIQUE")
	protected ModalitePedagogique modalitePedagogique;

	/** formateur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FORMATEUR")
	protected Utilisateur formateur;

	/**
	 * Salle d'informatique dans laquelle aura lieu le cours: par défaut c'est la
	 * salle de la session.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SAL")
	protected Salle salle;

	/**
	 * Lien vers la classe virtuelle dans le cas où la modalité est de type classe
	 * virtuelle
	 */
	@Column(name = "LIEN_CLASSE_VIRTUELLE")
	private String lienClasseVirtuelle;

}
