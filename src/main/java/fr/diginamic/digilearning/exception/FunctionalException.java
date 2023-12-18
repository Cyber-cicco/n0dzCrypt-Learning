package fr.diginamic.digilearning.exception;

/**
 * Exception lancé lorsqu'une opération censée fonctionner sans accroc échoue
 */
public class FunctionalException extends RuntimeException {

    public FunctionalException(String message){
        super(message);
    }
    public FunctionalException(){
        super();
    }
}
