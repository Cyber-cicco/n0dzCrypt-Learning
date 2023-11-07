package fr.diginamic.digilearning.utils.calendrier;

import java.time.LocalDate;

/**
 * Représente une période délimitée par une date de début, une date de fin, un
 * contenu (libellé) et une durée. Cette période peut représenter une période
 * fermée ou une période de cours. Cette période a également une société et un
 * intervenant donné.
 *
 * @author RichardBONNAMY
 *
 */
public class Periode {

    /** Date de début */
    private LocalDate dateDebut;
    /** Date de fin */
    private LocalDate dateFin;
    /** Libellé */
    private String libelle;
    /** societe */
    private String societe;
    /** intervenant */
    private String intervenant;
    /** modalite */
    private String modalite;
    /** Durée */
    private int duree;

    /**
     * Constructeur
     *
     * @param dateDebut date de début
     */
    public Periode(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Indique si oui ou non la période accepte le jour ou non. Le jour n'est
     * accepté que si le contenu de la journée correspond au contenu de la période.
     *
     * @param jour jour
     * @return boolean
     */
    public boolean accept(Jour jour) {
        return libelle.equals(jour.getContenu()) && modalite.equals(jour.getModalite())
                && societe.equals(jour.getSociete());
    }

    /**
     * Augmente la durée de la période de 1
     */
    public void incDuree() {
        this.duree += 1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dateDebut).append(" - ").append(dateFin).append(" - ").append(duree).append(" - ")
                .append(intervenant).append(" - ").append(libelle);
        return builder.toString();
    }

    /**
     * Getter
     *
     * @return the dateDebut
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Setter
     *
     * @param dateDebut the dateDebut to set
     */
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Getter
     *
     * @return the dateFin
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * Setter
     *
     * @param dateFin the dateFin to set
     */
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
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

    /**
     * Getter
     *
     * @return the duree
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Setter
     *
     * @param duree the duree to set
     */
    public void setDuree(int duree) {
        this.duree = duree;
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

}
