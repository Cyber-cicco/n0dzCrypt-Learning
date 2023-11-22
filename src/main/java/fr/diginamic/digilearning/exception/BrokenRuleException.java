package fr.diginamic.digilearning.exception;


public class BrokenRuleException extends RuntimeException {
    public BrokenRuleException(){
        super();
    }

    public BrokenRuleException(String message){
        super(message);
    }
}
