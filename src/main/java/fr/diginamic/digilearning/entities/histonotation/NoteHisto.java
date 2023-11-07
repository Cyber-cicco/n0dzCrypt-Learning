package fr.diginamic.digilearning.entities.histonotation;

import fr.diginamic.digilearning.entities.notation.Bulletin;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "NOTE_HISTO")
public class NoteHisto {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** valeur : double */
	@Column(name = "VALEUR")
	private String valeur;

	/** valeur : double */
	@Column(name = "VALEUR_PREC")
	private String valeurPrecedente;

	/** critereNotation : CritereNotation */
	@Column(name = "COURS")
	private String cours;

	/** critereNotation : CritereNotation */
	@Column(name = "CRITERE")
	private String critere;

	/** stagiaire : String */
	@Column(name = "STAGIAIRE")
	private String stagiaire;

	/** session : String */
	@Column(name = "SESSION")
	private String session;

	/** bulletin : Bulletin */
	@ManyToOne
	@JoinColumn(name = "ID_BULLETIN")
	private Bulletin bulletin;

	/** userMaj : String */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** dateMaj : LocalDateTime */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** appreciation : String */
	@Column(name = "APPRECIATION")
	private String appreciation;

	/** appreciatonPrec : String */
	@Column(name = "APPRECIATION_PREC")
	private String appreciatonPrec;

}
