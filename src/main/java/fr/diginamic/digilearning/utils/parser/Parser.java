package fr.diginamic.digilearning.utils.parser;

import java.util.Arrays;
import java.util.regex.Pattern;

import fr.diginamic.digilearning.exception.FunctionalException;
import fr.diginamic.digilearning.utils.StringUtils;

/**
 * Parse une expression simple contenant un mot clé, un opérateur arithmétique
 * (+ ou -) et une valeur entière
 *
 * @author RichardBONNAMY
 *
 */
public class Parser {

    /** AUTH_KEYWORDS */
    private static final String[] AUTH_KEYWORDS = { "DD", "DF", "B", "M" };

    /**
     * Parse l'expression infix passée en paramètre
     *
     * @param infix expression contenant un calcul
     * @return Expression
     * @throws FunctionalException si l'expression est incorrecte
     */
    public static void valide(String infix) throws FunctionalException {

        if (infix.isEmpty()) {
            throw new FunctionalException("Une de vos tâches ne possède pas de code date fin.");
        }

        String operateur = OperateurFactory.getOperateur(infix);
        String motCle = infix;
        String valeur = "0";
        if (operateur != null) {
            String[] morceaux = infix.split(Pattern.quote(operateur));
            if (morceaux.length != 2) {
                throw new FunctionalException(
                        "L'expression doit commencer par un mot clé (DD, DF, B ou M), suivi d'un opérateur (+ ou -) et finir par un nombre entier.");
            }
            motCle = morceaux[0].trim();
            valeur = morceaux[1].trim();
        }
        if (!isMotCleValide(motCle)) {
            throw new FunctionalException("L'expression doit commencer par un mot clé (DD, DF, B ou M).");
        }
        if (StringUtils.isDigits(valeur)) {
            throw new FunctionalException(
                    "L'expression doit commencer par un mot clé (DD, DF, B ou M), suivi d'un opérateur (+ ou -) et finir par un nombre entier.");
        }
    }

    /**
     * Parse l'expression infix passée en paramètre
     *
     * @param infix expression contenant un calcul
     * @return Expression
     * @throws FunctionalException si l'expression est incorrecte
     */
    public static Expression parse(String infix) throws FunctionalException {

        // Validation de l'expression infix
        valide(infix);

        // Traitement de l'expression infix
        String operateur = OperateurFactory.getOperateur(infix);
        if (operateur != null) {
            String[] morceaux = infix.split(Pattern.quote(operateur));
            String motCle = morceaux[0].trim();
            String valeur = morceaux[1].trim();
            return new Expression(motCle, operateur, Integer.parseInt(valeur));
        } else {
            return new Expression(infix, "+", 0);
        }
    }

    /**
     * Retourne si oui ou non le mot clé passé en paramètre est valide
     *
     * @param motCle mot clé
     * @return boolean
     */
    private static boolean isMotCleValide(final String motCle) {
        return Arrays.asList(AUTH_KEYWORDS).stream().filter(k -> k.equals(motCle)).count() > 0;
    }
}
