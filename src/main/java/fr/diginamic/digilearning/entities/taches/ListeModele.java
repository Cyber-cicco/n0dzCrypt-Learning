package fr.diginamic.digilearning.entities.taches;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "LISTE_MODELE")
@Cacheable(value = true)
public class ListeModele {

	/** identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** Nom de la liste */
	@Column(name = "NOM")
	private String nom;

	/** Liste des tâches à effectuer */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "listeModele")
	private List<Tache> taches = new ArrayList<>();

	/** dateMaj : LocalDateTime */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** userMaj : String */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** previous : TodoListe */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PREVIOUS")
	private ListeModele previous;

	/** parent : TodoListe */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PARENT")
	private ListeModele parent;

	/** next : TodoListe */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_NEXT")
	private ListeModele next;

}
