package fr.diginamic.digilearning.entities.taches;

/**
 * Représente le code d'un responsable pour une tâche
 * 
 * @author RichardBONNAMY
 *
 */
public enum CodeResponsable {

	/** RAF */
	RAF("Responsable Admin."),
	/** Assistante Admin. */
	ASS_RAF("Assistant(e) Admin."),
	/** Assistante Admin. CENTRE */
	ASS_RAF_CENTRE("Assistant(e) Admin. Centre"),
	/** COORDINATEUR */
	COORDINATEUR("Coordinateur pédagogique"),
	/** DIRECTEUR */
	DIRECTEUR("Directeur"),
	/** DIRECTEUR_PROD */
	DIRECTEUR_PROD("Directeur de la prod."),
	/** RECRUTEMENT */
	RECRUTEMENT("Recrutement"),
	/** COMMERCE */
	COMMERCE("Commerce"),
	/** MARKETING */
	MARKETING("Marketing"),
	/** ASS_MARKETING */
	ASS_MARKETING("Communication"),
	/** Non attribué */
	NON_ATTRIBUE("Non attribué");

	private String libelle;

	/**
	 * Constructeur
	 * 
	 * @param libelle
	 */
	private CodeResponsable(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Retourne un code responsable en fonction de son libellé
	 * 
	 * @param label libellé
	 * @return {@link CodeResponsable}
	 */
	public static CodeResponsable valueByLabel(String label) {
		for (CodeResponsable cr : values()) {
			if (cr.getLibelle().equals(label)) {
				return cr;
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
