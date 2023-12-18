package fr.diginamic.digilearning.exception;

/**
 * Exception lancée lorsqu'un utilisateur tente de consulter une
 * ressource à laquelle il n'est pas censé avoir accès
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message){
        super(message);
    }

    public UnauthorizedException(){}
}
