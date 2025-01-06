package fr.diginamic.digilearning.DTO;

import lombok.*;

/**
 * Correspond à une représentation d'un objet transféré du front
 * ne contenant qu'un simple message.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MessageDto {
    private String message;
}
