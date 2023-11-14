package fr.diginamic.digilearning.utils.calendrier;

import fr.diginamic.digilearning.entities.CoursPlanifie;
import fr.diginamic.digilearning.entities.Evenement;
import fr.diginamic.digilearning.utils.DateUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une semaine ouvrable du calendrier (du lundi au vendredi)
 *
 * @author DIGINAMIC
 *
 */
public class Semaine {

    /** numero : numéro de semaine officiel */
    private int numero;
    /** jours : List de LocalDate */
    private List<Jour> jours = new ArrayList<>();
    /** cours : List<CoursPlanifie> */
    private List<CoursPlanifie> cours;
    /** fermes : List de Evenement */
    private List<Evenement> fermes;

    /**
     * Constructor
     *
     * @param numero numéro de semaine
     * @param fermes liste des périodes fermées
     */
    public Semaine(int numero, List<CoursPlanifie> cours, List<Evenement> fermes) {
        this.numero = numero;
        this.cours = cours;
        this.fermes = fermes;
    }

    /**
     * Retourne le nombre de jours ouvrables dans la semaine
     *
     * @return int
     */
    public int size() {
        return jours.size();
    }

    /**
     * Retourne si oui ou non la semaine est complète. La semaine est pleine si le
     * dernier jour de la liste de jours est un vendredi.
     *
     * @return boolean
     */
    public boolean isFull() {
        return jours.size() > 0 && jours.get(jours.size() - 1).getDate().get(ChronoField.DAY_OF_WEEK) == 5;
    }

    /**
     * Getter
     *
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Setter
     *
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Getter
     *
     * @return the jours
     */
    public List<Jour> getJours() {
        return jours;
    }

    /**
     * Setter
     *
     * @param jours the jours to set
     */
    public void setJours(List<Jour> jours) {
        this.jours = jours;
    }

    /**
     * Retourne le premier jours de la semaine
     *
     * @return LocalDate
     */
    public Jour getPremier() {
        return jours.get(0);
    }

    /**
     * Retourne le dernier jour de la semaine
     *
     * @return LocalDate
     */
    public Jour getDernier() {
        return jours.get(jours.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        jours.stream().forEach(j -> builder.append(j).append("\n"));
        return builder.toString();
    }

    /**
     * Getter
     *
     * @return the fermes
     */
    public List<Evenement> getFermes() {
        return fermes;
    }

    /**
     * Setter
     *
     * @param fermes the fermes to set
     */
    public void setFermes(List<Evenement> fermes) {
        this.fermes = fermes;
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
