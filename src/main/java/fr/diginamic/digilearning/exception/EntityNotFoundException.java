package fr.diginamic.digilearning.exception;


import lombok.NoArgsConstructor;

/**
 * Exception lancée lorsque l'on ne trouve pas une entité via
 * un identifiant unique
 */
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message){
        super(message);
    }
}
