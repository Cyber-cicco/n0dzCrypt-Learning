package fr.diginamic.digilearning.utils.calendrier;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.digilearning.entities.CoursPlanifie;
import fr.diginamic.digilearning.entities.Evenement;
import fr.diginamic.digilearning.utils.DateUtils;

/**
 * Représente un calendrier de jours ouvrés. Une semaine de ce calendrier ne
 * peut contenir des jours que du lundi au vendredi.
 *
 * @author DIGINAMIC
 *
 */
public class Calendrier {

    /** dateDebut : LocalDate */
    private LocalDate dateDebut;
    /** dateFin : LocalDate */
    private LocalDate dateFin;
    /** cours : List<CoursPlanifie> */
    private List<CoursPlanifie> cours;
    /** periodesFermees : List de Evenement */
    private List<Evenement> periodesFermees;

    /** semaines : List de Semaine */
    private List<Semaine> semaines = new ArrayList<>();

    /**
     * Constructor
     *
     * @param dateDebut       date de début
     * @param dateFin         date de fin
     * @param cours           cours
     * @param periodesFermees périodes fermées
     */
    public Calendrier(LocalDate dateDebut, LocalDate dateFin, List<CoursPlanifie> cours,
                      List<Evenement> periodesFermees) {
        super();
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cours = cours;
        this.periodesFermees = periodesFermees;
        completeSemaines();
    }

    /**
     * Retourne la semaine en fonction de son index et non de son numéro
     *
     * @param index index
     * @return Semaine
     */
    public Semaine getSemaine(int index) {
        return semaines.get(index);
    }

    /**
     * Calcule les différentes semaines entre la date de début et la date de fin
     */
    private void completeSemaines() {
        LocalDate curseur = dateDebut;
        do {
            add(curseur);
            curseur = curseur.plus(1, ChronoUnit.DAYS);
        } while (!curseur.isAfter(dateFin));

    }

    /**
     * Retourne si le calendrier a une semaine courante ou non: pour avoir une
     * semaine courante il faut que la liste des semaines soit non vide et que la
     * dernière semaine de la liste ne soit pas pleine
     *
     * @return boolean
     */
    private boolean hasSemaineCourante() {
        if (semaines.size() > 0 && !getSemaineCourante().isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Retourne la semaine courante
     *
     * @return Semaine
     */
    private Semaine getSemaineCourante() {
        return semaines.get(semaines.size() - 1);
    }

    /**
     * Permet d'ajouter un jour au calendrier. On n'ajoute que les jours du lundi au
     * vendredi, qu'ils soient ouverts ou non.
     *
     * @param date date à ajouter
     */
    private void add(LocalDate date) {
        if (date.get(ChronoField.DAY_OF_WEEK) <= 5) {
            if (!hasSemaineCourante()) {
                semaines.add(new Semaine(DateUtils.getNumeroSemaine(date), cours, periodesFermees));
            }
            Semaine semaineCourante = getSemaineCourante();
            //semaineCourante.add(date);
        }
    }

    /**
     * Récupère la liste de toutes les périodes avec comptage de jours pour une
     * société donnée
     *
     * @param societe société
     * @return List de Periode
     */
    public List<Periode> getPeriodes(String societe) {
        List<Periode> periodes = new ArrayList<>();
        Periode periodeCourante = null;
        for (Semaine semaine : semaines) {
            for (Jour jour : semaine.getJours()) {
                if (periodeCourante == null || !periodeCourante.accept(jour)) {
                    periodeCourante = new Periode(jour.getDate());
                    periodeCourante.setDateFin(jour.getDate());
                    periodeCourante.setLibelle(jour.getContenu());
                    periodeCourante.setIntervenant(jour.getIntervenant());
                    periodeCourante.setSociete(jour.getSociete());
                    periodeCourante.setModalite(jour.getModalite());
                    periodes.add(periodeCourante);
                } else {
                    periodeCourante.setDateFin(jour.getDate());
                }
                if (periodeCourante.getSociete().equals(societe) && jour.getSociete().equals(societe)) {
                    periodeCourante.incDuree();
                }
            }
        }
        return periodes;
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
     * @return the periodesFermees
     */
    public List<Evenement> getPeriodesFermees() {
        return periodesFermees;
    }

    /**
     * Setter
     *
     * @param periodesFermees the periodesFermees to set
     */
    public void setPeriodesFermees(List<Evenement> periodesFermees) {
        this.periodesFermees = periodesFermees;
    }

    /**
     * Getter
     *
     * @return the semaines
     */
    public List<Semaine> getSemaines() {
        return semaines;
    }

    /**
     * Setter
     *
     * @param semaines the semaines to set
     */
    public void setSemaines(List<Semaine> semaines) {
        this.semaines = semaines;
    }

    /**
     * Getter
     *
     * @return the cours
     */
    public List<CoursPlanifie> getCours() {
        return cours;
    }

    /**
     * Setter
     *
     * @param cours the cours to set
     */
    public void setCours(List<CoursPlanifie> cours) {
        this.cours = cours;
    }
}
