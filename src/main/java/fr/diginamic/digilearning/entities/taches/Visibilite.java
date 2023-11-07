package fr.diginamic.digilearning.entities.taches;


/**
 * Représente la visibilité d'une tâche ou d'un projet:<br>
 * - PUBLIC: Tout le monde <br>
 * - PRIVE: contributeurs uniquement
 * 
 * @author RichardBONNAMY
 *
 */
public enum Visibilite {

	/** PUBLIC */
	PUBLIC("Public"),
	/** PRIVE */
	PRIVE("Privé");

	/** libelle */
	private String libelle;

	/**
	 * Constructeur
	 * 
	 * @param libelle libellé
	 */
	private Visibilite(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Retourne une visibilité en fonction d'un libellé passé en paramètre
	 * 
	 * @param libelle libellé
	 * @return {@link Visibilite}
	 */
	public static Visibilite valueByLibelle(String libelle) {

		if (libelle.isEmpty()) {
			return null;
		}

		Visibilite[] visibilites = values();
		for (Visibilite visibilite : visibilites) {
			if (visibilite.getLibelle().toLowerCase().equals(libelle.toLowerCase())) {
				return visibilite;
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
}
