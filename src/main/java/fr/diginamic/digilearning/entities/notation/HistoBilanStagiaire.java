package fr.diginamic.digilearning.entities.notation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "HISTO_BILAN_STAGIAIRE")
public class HistoBilanStagiaire {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	/** datePrevue : LocalDate */
	@Column(name = "DATE_PREVUE")
	private LocalDate datePrevue;

	/** dateEffective : LocalDate */
	@Column(name = "DATE_EFFECTIVE")
	private LocalDate dateEffective;

	/** numero : int */
	@Column(name = "NUMERO")
	private int numero;

	/** appreciation : String */
	@Column(name = "APPRECIATION")
	private String appreciation;

	/** indiceConfiance : int */
	@Column(name = "INDICE_CONFIANCE")
	private int indiceConfiance;

	/** email : String */
	@Column(name = "EMAIL")
	private String email;

	/** nomStagiaire : String */
	@Column(name = "NOM_STAGIAIRE")
	private String nomStagiaire;

	/** prenomStagiaire : String */
	@Column(name = "PRENOM_STAGIAIRE")
	private String prenomStagiaire;

	/** nomSession : String */
	@Column(name = "NOM_SESSION")
	private String nomSession;

	/** userMaj : String */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** dateMaj : LocalDateTime */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;
	/**
	 * Constructor
	 * 
	 * @param bilan bilan stagiaire
	 */
	public HistoBilanStagiaire(BilanStagiaire bilan) {
		this.appreciation = bilan.getAppreciation();
		this.dateEffective = bilan.getDateEffective();
		this.datePrevue = bilan.getDatePrevue();
		this.dateMaj = LocalDateTime.now();
		this.indiceConfiance = bilan.getIndiceConfiance();
		if (bilan.getBulletin() != null) {
			if (bilan.getBulletin().getSession() != null) {
				this.nomSession = bilan.getBulletin().getSession().getNom();
			}
		}
		this.numero = bilan.getNumero();
	}
}
