package fr.diginamic.digilearning.utils.parser;

import lombok.Getter;
import lombok.Setter;

/**
 * Représente une expression simple avec un mot clé à gauche, un opérateur (+ ou
 * -) et une valeur à droite, exemple: DD+3
 *
 * @author DIGINAMIC
 *
 */
@Getter
@Setter
public class Expression {

    /**
     * motCle : partie gauche de l'expression par exemple DD dans l'expression DD+3
     */
    private String motCle;
    /**
     * operateur : opérateur de l'expression par exemple + dans l'expression DD+3
     */
    private String operateur;
    /**
     * valeur : partie entière de l'expression par exemple 3 dans l'expression DD+3
     */
    private Integer valeur;

    /**
     * COnstructeur
     *
     * @param motCle    mot clé
     * @param operateur opérateur
     * @param valeur    valeur entière
     */
    public Expression(String motCle, String operateur, Integer valeur) {
        super();
        this.motCle = motCle;
        this.operateur = operateur;
        this.valeur = valeur;
    }
}