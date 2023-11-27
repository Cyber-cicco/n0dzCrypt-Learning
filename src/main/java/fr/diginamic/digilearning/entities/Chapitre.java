package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusChapitre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "dl_chapitre")
public class Chapitre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String libelle;
	@Column(columnDefinition = "LONGTEXT")
	private String contenu;
	private String lienVideo;
	private Integer ordre;
	@Enumerated
	private StatusChapitre statusChapitre;
	@ManyToOne
	@JoinColumn(name = "cours_id")
	private Cours cours;

	@Override
	public String toString() {
		return libelle + "\n";
	}
}
