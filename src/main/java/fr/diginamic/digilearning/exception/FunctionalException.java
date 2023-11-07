package fr.diginamic.digilearning.exception;

public class FunctionalException extends RuntimeException {

    public FunctionalException(String message){
        super(message);
    }
    public FunctionalException(){
        super();
    }
}
