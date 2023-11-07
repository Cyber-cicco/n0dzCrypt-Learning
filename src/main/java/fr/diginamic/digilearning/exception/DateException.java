package fr.diginamic.digilearning.exception;


public class DateException extends RuntimeException {
    public DateException(){
        super();
    }
    public DateException(String message){
        super(message);
    }
}
