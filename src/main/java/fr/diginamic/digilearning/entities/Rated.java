package fr.diginamic.digilearning.entities;

import java.util.List;

public interface Rated<T> {
    Boolean isLiked(Long id);
    Boolean isDisliked(Long id);
    int getLikes();
    int getDislikes();
    List<T> getRelations();
}
