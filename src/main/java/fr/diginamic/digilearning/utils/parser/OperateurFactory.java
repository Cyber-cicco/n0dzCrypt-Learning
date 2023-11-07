package fr.diginamic.digilearning.utils.parser;

import fr.diginamic.digilearning.exception.FunctionalException;

/**
 * Retourne l'opérateur pour une expression donnée méthode
 *
 * @author DIGINAMIC
 *
 */
public class OperateurFactory {

    /**
     * Retourne l'opérateur utilisé dans l'expression
     *
     * @param expression expression
     * @return String
     */
    public static String getOperateur(String expression) throws FunctionalException {
        if (expression.indexOf('+') != -1) {
            return "+";
        } else if (expression.indexOf('-') != -1) {
            return "-";
        }
        return null;
    }
}
