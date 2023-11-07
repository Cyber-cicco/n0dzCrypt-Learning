package fr.diginamic.digilearning.utils.calendrier;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente un jour de la semaine avec une date et le libellé de son contenu
 *
 * @author RichardBONNAMY
 *
 */
public class Jour {

    /** date */
    private LocalDate date;
    /** libelle : contenu de la journée (cours, jours fermé) */
    private String contenu;
    /** ouvert : indique si la journée est travaillée ou non */
    private boolean ouvert;
    /** formateur */
    private String intervenant;
    /** modalite */
    private String modalite;
    /** societe */
    private String societe;

    /** compteursComptesVirtuels */
    private Map<String, Integer> compteursComptesVirtuels = new HashMap<>();

    /**
     * Constructeur
     *
     * @param date        date du jour
     * @param contenu     contenu du jour
     * @param ouvert      indique si le jour est travaillé ou non
     * @param societe     societe
     * @param intervenant nom de l'intervenant
     * @param modalite    modalité
     */
    public Jour(LocalDate date, String contenu, boolean ouvert, String societe, String intervenant, String modalite) {
        super();
        this.date = date;
        this.contenu = contenu;
        this.ouvert = ouvert;
        this.societe = societe;
        this.intervenant = intervenant;
        this.modalite = modalite;
    }

    /**
     * Ajoute 1 au compteur de compte virtuel pour le jour courant et le compte
     * virtuel passé en paramètre
     *
     * @param compteVirtuel compte virtuel
     */
    public void comptage(String compteVirtuel) {
        Integer compteur = compteursComptesVirtuels.get(compteVirtuel);
        if (compteur == null) {
            compteur = 0;
        }
        compteur++;
        compteursComptesVirtuels.put(compteVirtuel, compteur);
    }

    /**
     * Retourne le compteur pour un compte virtuel donné
     *
     * @param compteVirtuel compte virtuel
     * @return Integer
     */
    public Integer getCompteur(String compteVirtuel) {
        return compteursComptesVirtuels.get(compteVirtuel);
    }

    @Override
    public String toString() {
        return "date=" + date + ", contenu=" + contenu + ", travaillée=" + ouvert;
    }


    /**
     * Getter
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Setter
     *
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Getter
     *
     * @return the ouvert
     */
    public boolean isOuvert() {
        return ouvert;
    }

    /**
     * Setter
     *
     * @param ouvert the ouvert to set
     */
    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }

    /**
     * Getter
     *
     * @return the intervenant
     */
    public String getIntervenant() {
        return intervenant;
    }

    /**
     * Setter
     *
     * @param intervenant the intervenant to set
     */
    public void setIntervenant(String intervenant) {
        this.intervenant = intervenant;
    }

    /**
     * Getter
     *
     * @return the societe
     */
    public String getSociete() {
        return societe;
    }

    /**
     * Setter
     *
     * @param societe the societe to set
     */
    public void setSociete(String societe) {
        this.societe = societe;
    }

    /**
     * Getter
     *
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Setter
     *
     * @param contenu the contenu to set
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Getter
     *
     * @return the modalite
     */
    public String getModalite() {
        return modalite;
    }

    /**
     * Setter
     *
     * @param modalite the modalite to set
     */
    public void setModalite(String modalite) {
        this.modalite = modalite;
    }

    /**
     * Getter
     *
     * @return the compteursComptesVirtuels
     */
    public Map<String, Integer> getCompteursComptesVirtuels() {
        return compteursComptesVirtuels;
    }

}
