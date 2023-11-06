package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeCours;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "COURS_REF")
@Cacheable(value = true)
public class CoursRef {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	@OneToMany(mappedBy = "coursRef")
	private List<Cours> cours;

	/** libelleCourt : String */
	@Column(name = "LIBELLE_COURT")
	private String libelleCourt;

	/** typeCours : TypeCours */
	@Column(name = "TYPE_COURS")
	@Enumerated(EnumType.STRING)
	private TypeCours typeCours;

	/** dureeDefaut : Integer*/
	@Column(name = "DUREE_DEFAUT", length = 3)
	private Integer dureeDefaut;

	/** coeffDefaut : String */
	@Column(name = "COEFF_DEFAUT", length = 3)
	private Integer coeffDefaut;

	/** prerequis : String */
	@Column(name = "PREREQUIS")
	private String prerequis;

	/** description : String */
	@Column(name = "DESCRIPTION")
	private String description;

	/** objectifsPedagogiques : objectifs pédagogiques */
	@Column(name = "OBJS_PEDAGOGIQUES")
	private String objectifsPedagogiques;

	@ManyToMany
	@JoinTable(name = "COMPETENCE_COURS",
			joinColumns = @JoinColumn(name = "ID_COURS", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "ID_COMPETENCE", referencedColumnName = "ID")
	)
	private List<Competence> competences;
}
