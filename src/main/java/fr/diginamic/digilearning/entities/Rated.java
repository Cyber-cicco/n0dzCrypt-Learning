package fr.diginamic.digilearning.entities;

import java.util.List;

/**
 * Contrat sur les méthodes à implémenter pour qu'une entité
 * puisse etre like ou dislike
 * @param <T> La relation entre l'utilisateur et l'entité
 *
 * @author Abel Ciccoli
 */
public interface Rated<T extends RelationLiked> {
    Boolean isLiked(Long id);
    Boolean isDisliked(Long id);
    int getLikes();
    int getDislikes();
    List<T> getRelations();
}
