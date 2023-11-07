package fr.diginamic.digilearning.entities.enums;

public enum StatutPreparation {
    /**
     * BROUILLON : Phase durant laquelle les formateurs la phase de validation
     * est inactive
     */
    BROUILLON("Brouillon", 0),
    /** PLANIFIEE : Planifiée mais non confirmée */
    PLANIFIEE("Planifiée", 1),
    /** CONFIRMEE : Confirmée par le client */
    CONFIRMEE("Confirmée", 2),
    /** ANNULEE : Annulée par le client */
    ANNULEE("Annulée", 0);

    /** libelle du statut : String */
    private String libelle;

    /** criticite : int */
    private int criticite;

    /**
     * Constructor
     *
     * @param libelle
     *            libellé du statut
     * @param criticite
     *            criticité de la session
     */
    private StatutPreparation(String libelle, int criticite) {
        this.libelle = libelle;
        this.criticite = criticite;
    }


    /** Recherche un statut de préparation en fonction de son libellé
     * @param libelle libellé
     * @return {@link StatutPreparation}
     */
    public static StatutPreparation valueOfLibelle(String libelle) {
        StatutPreparation[] statuts = values();
        for (StatutPreparation statut: statuts) {
            if (statut.getLibelle().equals(libelle)) {
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
     * @param libelle
     *            the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Getter
     *
     * @return the criticite
     */
    public int getCriticite() {
        return criticite;
    }

    /**
     * Setter
     *
     * @param criticite
     *            the criticite to set
     */
    public void setCriticite(int criticite) {
        this.criticite = criticite;
    }
}