package fr.diginamic.digilearning.entities.taches;

import fr.diginamic.digilearning.entities.Utilisateur;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TDB")
public class TableauBord {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;
	
	/** themes */
	@ManyToMany
	@JoinTable(name = "TDB_THEMES", joinColumns = @JoinColumn(name = "ID_TDB", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_THEME", referencedColumnName = "ID"))
	private List<Theme> themes;
	
	/** visibilite */
	@Enumerated(EnumType.STRING)
	private Visibilite visibilite;
	
	/** proprietaire */
	@ManyToOne
	@JoinColumn(name="ID_PROPRIETAIRE")
	private Utilisateur proprietaire;

	/** Getter
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/** Setter
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter
	 * @return the themes
	 */
	public List<Theme> getThemes() {
		return themes;
	}

	/** Setter
	 * @param themes the themes to set
	 */
	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	/** Getter
	 * @return the visibilite
	 */
	public Visibilite getVisibilite() {
		return visibilite;
	}

	/** Setter
	 * @param visibilite the visibilite to set
	 */
	public void setVisibilite(Visibilite visibilite) {
		this.visibilite = visibilite;
	}

	/** Getter
	 * @return the proprietaire
	 */
	public Utilisateur getProprietaire() {
		return proprietaire;
	}

	/** Setter
	 * @param proprietaire the proprietaire to set
	 */
	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}
}
