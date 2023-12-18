package fr.diginamic.digilearning.dto;

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
