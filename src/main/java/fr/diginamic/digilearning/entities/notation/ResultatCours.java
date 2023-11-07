package fr.diginamic.digilearning.entities.notation;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.CritereNotation;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "RESULTAT_COURS")
public class ResultatCours {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** cours : Cours */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COURS")
	private Cours cours;

	/** bulletin : Bulletin */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BULLETIN")
	private Bulletin bulletin;

	/** notes : List de Note */
	@OneToMany(mappedBy = "idResultat", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Note> notes = new HashSet<>();

	/** appreciation : String */
	@Column(name = "APPRECIATION", length = 1000)
	private String appreciation;

	/** publication : Boolean */
	@Column(name = "PUBLICATION")
	private Boolean publication;

	/** sauvegarde : boolean */
	@Transient
	private boolean sauvegarde;

	/**
	 * Constructor
	 * 
	 * @param id           identifiant
	 * @param appreciation appréciation
	 */
	public ResultatCours(Long id, String appreciation) {
		this.id = id;
		this.appreciation = appreciation;
	}

	/**
	 * Retourne la note dont le critère est passé en paramètre
	 * 
	 * @param critere critère de notation
	 * @return Optional
	 */
	public Optional<Note> getNote(CritereNotation critere) {
		return notes.stream().filter(n -> n.getCritereNotation().equals(critere)).findAny();
	}

	/**
	 * Compare le critere de notation à une liste de critères de référence. Si le
	 * critère passé en parmètre existe dans la liste alors la méthode retourne true
	 * sinon elle retourne false
	 * 
	 * @param critere     critère
	 * @param criteresRef critères de référence
	 * @return boolean
	 */
	public boolean critereExiste(CritereNotation critere, Set<CritereNotation> criteresRef) {
		return criteresRef.stream().filter(cref -> cref.equals(critere)).count() >= 1;
	}

	/**
	 * Retourne la liste triée des notes selon les types de critère de notation
	 * 
	 * @return the notes
	 */
	public List<Note> getSortedNotes() {

		List<Note> notes = new ArrayList<>();
		notes.addAll(this.notes);

		Collections.sort(notes);

		return notes;
	}

}
