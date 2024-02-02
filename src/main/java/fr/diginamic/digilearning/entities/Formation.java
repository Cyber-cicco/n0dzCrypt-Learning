package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeCoursElement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Représente une formation délivrée par Diginamic
 *
 * @author DIGINAMIC
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "FORMATION")
@Cacheable
public class Formation implements CoursElement {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom de la formation */
	@Column(name = "NOM")
	private String nom;

	/** nom de la formation */
	@Column(name = "NOM_COURT")
	private String nomCourt;

	/** reference : String */
	@Column(name = "REFERENCE", length = 10)
	private String reference;
	/**
	 * Indique par OUI ou par NON si la formation donne lieu à la délivrance d'un
	 * titre pro.
	 */
	@Column(name = "TITRE")
	private String titre;

	/**
	 * Liste des modules (une formation sans module a malgré tout un unique module,
	 * masqué à l'affichage, qui s'appelle Default)
	 */
	@ManyToMany
	@JoinTable(name = "dl_module_formation",
			joinColumns = @JoinColumn(name = "id_formation", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "id_module", referencedColumnName = "id")
	)
	private List<Module> modules = new ArrayList<>();


	@Override
	public TypeCoursElement getTypeElement() {
		return TypeCoursElement.FORMATION;
	}
}
