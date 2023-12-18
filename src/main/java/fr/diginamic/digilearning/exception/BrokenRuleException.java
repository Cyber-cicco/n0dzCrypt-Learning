package fr.diginamic.digilearning.exception;


/**
 * Exception lancée lorsqu'un input de l'utilisateur
 * ne répond pas aux règles métier
 *
 */
public class BrokenRuleException extends RuntimeException {
    public BrokenRuleException(){
        super();
    }

    public BrokenRuleException(String message){
        super(message);
    }
}
