package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="TJM_SOCIETE")
public class TjmSociete implements PlageDate {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** societe */
	@ManyToOne
	@JoinColumn(name="ID_SOCIETE")
	private Societe societe;
	
	/** valeur */
	@Column(name="VALEUR")
	private Float valeur;
	
	/** valeur pour les journées partielles */
	@Column(name="VALEUR_PARTIELLE")
	private Float valeurPartielle;
	
	/** dateDebutValidite */
	@Column(name="DATE_DEBUT_VALIDITE")
	private LocalDate dateDebutValidite;
	
	/** dateFinValidite */
	@Column(name="DATE_FIN_VALIDITE")
	private LocalDate dateFinValidite;
	
	/** Constructeur
	 * @param dateDebutValidite date de début de validité
	 * @param dateFinValidite date de fin de validité
	 */
	public TjmSociete(LocalDate dateDebutValidite, LocalDate dateFinValidite) {
		this.dateDebutValidite = dateDebutValidite;
		this.dateFinValidite = dateFinValidite;
	}

	/** Constructeur
	 * @param valeur valeur
	 */
	public TjmSociete(Float valeur) {
		super();
		this.valeur = valeur;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TjmSociete)) {
			return false;
		}
		TjmSociete autre = (TjmSociete)object;
		return Objects.equals(this.id, autre.getId());
	}

	@Override
	public LocalDate getDateDebut() {
		return dateDebutValidite;
	}

	@Override
	public LocalDate getDateFin() {
		return dateFinValidite;
	}

}
