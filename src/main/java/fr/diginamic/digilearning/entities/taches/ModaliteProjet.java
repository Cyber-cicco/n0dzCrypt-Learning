package fr.diginamic.digilearning.entities.taches;


/**
 * Représente la manière dont le projet a été généré, soit à partir de
 * l'association d'une session et d'une liste modèle (GENERE), soit manuellement
 * (MANUEL)
 * 
 * @author RichardBONNAMY
 *
 */
public enum ModaliteProjet {

	/** GENERE */
	GENERE("Généré"),
	/** MANUEL */
	MANUEL("Manuel");

	/** libelle */
	private String libelle;

	/**
	 * Constructeur
	 * 
	 * @param libelle libellé
	 */
	private ModaliteProjet(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Retourne une modalité en fonction d'un libellé passé en paramètre
	 * 
	 * @param libelle libellé
	 * @return {@link ModaliteProjet}
	 */
	public static ModaliteProjet valueByLibelle(String libelle) {

		if (libelle.isEmpty()) {
			return null;
		}

		ModaliteProjet[] modalites = values();
		for (ModaliteProjet modalite : modalites) {
			if (modalite.getLibelle().equals(libelle)) {
				return modalite;
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
