package fr.diginamic.digilearning.entities.enums;

/**
 * Représente les 2 types d'émargements mis en oeuvre dans SID
 * 
 * @author DIGINAMIC
 *
 */
public enum TypeEmargement {

	/** AM_PM : TypeEmargement */
	AM_PM("Matin/Après-midi"),
	/** TECHNIQUE_PRATIQUE : TypeCours */
	CONN_DECO("Connexion/Déconnexion"),
	DECO_RECO("Déconnexion/Reconnexion");

	/** libelle : String */
	private String libelle;

	/**
	 * Constructor
	 * 
	 * @param libelle libellé
	 */
	private TypeEmargement(String libelle) {
		this.libelle = libelle;
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
