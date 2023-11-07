package fr.diginamic.digilearning.entities.finance;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "LIGNE_COMMANDE")
public class LigneCommande {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** session : Session */
	@ManyToOne
	@JoinColumn(name = "ID_BON")
	private BonCommande bonCommande;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;
	
	/** partiel */
	@Column(name = "PARTIEL")
	private Boolean partiel;

	/** nbJours : Integer */
	@Column(name = "NB_JOURS")
	private Integer nbJours = 0;

	/** tjm : Float */
	@Column(name = "TJM")
	private Float tjm = 0.0f;

	/** tva : Float */
	@Column(name = "TAUX_TVA")
	private Float tauxTva = 0.0f;

	/** montantHT : Float */
	@Column(name = "MONTANT_HT")
	private Float montantHT = 0.0f;

	/** montantHT : Float */
	@Column(name = "MONTANT_TVA")
	private Float montantTVA = 0.0f;

	/** montantTTC : Float */
	@Column(name = "MONTANT_TTC")
	private Float montantTTC = 0.0f;

}
