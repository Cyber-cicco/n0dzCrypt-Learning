package fr.diginamic.digilearning.entities.taches;

import fr.diginamic.digilearning.entities.Utilisateur;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "THEME")
public class Theme implements Comparable<Theme> {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** code : String */
	@Column(name = "CODE")
	private String code;

	/** ordre */
	@Column(name = "ORDRE")
	private int ordre;

	/** visibilité */
	@Column(name = "VISIBILITE")
	@Enumerated(EnumType.STRING)
	private Visibilite visibilite;
	
	/** proprietaire */
	@ManyToOne
	@JoinColumn(name="ID_PROPRIETAIRE")
	private Utilisateur proprietaire;

	/** projets */
	@OneToMany(mappedBy = "theme")
	private Set<Projet> projets = new HashSet<>();
	
	/** tdbs */
	@ManyToMany
	@JoinTable(name = "TDB_THEMES", joinColumns = @JoinColumn(name = "ID_THEME", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_TDB", referencedColumnName = "ID"))
	private List<TableauBord> tdbs;

	/**
	 * Constructeur
	 * 
	 * @param id identifiant
	 */
	public Theme(Long id) {
		this.id = id;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param id identifiant
	 * @param nom nom
	 */
	public Theme(Long id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	@Override
	public int compareTo(Theme theme) {
		return this.getOrdre() - theme.getOrdre();
	}

}
