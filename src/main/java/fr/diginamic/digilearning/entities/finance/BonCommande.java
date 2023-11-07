package fr.diginamic.digilearning.entities.finance;

import fr.diginamic.digilearning.entities.EmetteurBon;
import fr.diginamic.digilearning.entities.Societe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BON_COMMANDE")
public class BonCommande {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** session : Session */
	@Column(name = "ID_SESSION_PARENT")
	private Long idSessionParent;

	/** societeDestinataire : Societe envers laquelle le bon de commande est créé
	 *TODO : changer nom de la colonne en base de données
	 * */
	@ManyToOne
	@JoinColumn(name = "ID_SOCIETE_DESTINATAIRE")
	private Societe societeDestinataire;

	/** societeEmittrice : Société ayant émis le bon de commande. Par défaut, diginamic ou Tecken
	 * TODO:créer la colonne en base de données, rajouter une clé étrangère dans Societe. Pas besoin de rajouter un nouveau champ dans l'entité
	 * */
	@ManyToOne
	@JoinColumn(name = "ID_SOCIETE_EMETTRICE")
	private EmetteurBon societeEmettrice;

	/** lignes : List de LigneCommande */
	@OneToMany(mappedBy = "bonCommande")
	private List<LigneCommande> lignes = new ArrayList<>();

	/** modalitesPaiement : String */
	@Column(name = "MODALITES_PAIEMENT")
	private String modalitesPaiement;

	/** duree : Integer */
	@Column(name = "NB_JOURS")
	private Integer nbJours;

	/** Date de dernière mise à jour */
	@Column(name = "DATE_MAJ")
	private LocalDateTime dateMaj;

	/** Auteur de la dernière mise à jour */
	@Column(name = "USER_MAJ")
	private String userMaj;

	/** montantHT : double */
	@Transient
	private double montantHt;

	/** montantTva : double */
	@Transient
	private double montantTva;

	/** montantTTC : double */
	@Transient
	private double montantTtc;

	/**
	 * Retourne la ligne dont le libellé est passé en paramètre
	 *
	 * @param libelle libellé de la ligne
	 * @return {@link Optional}
	 */
	public Optional<LigneCommande> getLigne(String libelle) {
		return lignes.stream().filter(l -> l.getLibelle().equals(libelle)).findFirst();
	}

	/**
	 * Supprime la ligne dont le libellé est passé en paramètre
	 *
	 * @param libelle libellé de la ligne
	 */
	public void supprimerLigne(String libelle) {
		Iterator<LigneCommande> iter = getLignes().iterator();
		while (iter.hasNext()) {
			LigneCommande ligne = iter.next();
			if (ligne.getLibelle().equals(libelle)) {
				iter.remove();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(societeDestinataire.getNom()).append("\n");
		for (LigneCommande lc : lignes) {
			builder.append(" - ").append(lc).append("\n");
		}
		return builder.toString();
	}
}