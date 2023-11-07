package fr.diginamic.digilearning.entities.enums;

/**
 * Statut d'une tâche planifiée
 * 
 * @author DIGINAMIC
 *
 */
public enum StatutTache {

	/** A_FAIRE : StatutTache */
	A_FAIRE("A faire"),

	/** EN_COURS : StatutTache */
	EN_COURS("En cours"),

	/** TERMINEE : StatutTache */
	TERMINEE("Terminée"),

	/** ANNULEE */
	ANNULEE("Annulée");

	/** libelle : String */
	private String libelle;

	/**
	 * Constructor
	 * 
	 * @param libelle libellé
	 */
	private StatutTache(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Retourne le statut dont le libellé est passé en paramètre
	 * 
	 * @param libelle libellé
	 * @return {@link StatutTache}
	 */
	public static StatutTache valueByLibelle(String libelle) {
		for (StatutTache statut : values()) {
			if (statut.getLibelle().toLowerCase().equals(libelle.toLowerCase())) {
				return statut;
			}
		}
		return null;
	}

	/**
	 * Getter
	 * 
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter
	 * 
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
