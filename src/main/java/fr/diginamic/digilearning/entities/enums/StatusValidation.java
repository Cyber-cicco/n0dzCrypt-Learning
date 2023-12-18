package fr.diginamic.digilearning.entities.enums;

/**
 * Status de validation d'une session
 * Artefact du SID
 */
public enum StatusValidation {
    /** A_VALIDER : StatutValidation */
    A_VALIDER("A Valider"),
    /** VALIDE : StatutValidation */
    VALIDE("Validé"),
    /** REFUSE : StatutValidation */
    REFUSE("Refusé");

    /** libelle du statut : String */
    private String libelle;

    /**
     * Constructor
     *
     * @param libelle
     *            libellé du statut
     */
    StatusValidation(String libelle) {
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
     * @param libelle
     *            the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
