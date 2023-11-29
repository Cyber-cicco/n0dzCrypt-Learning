package fr.diginamic.digilearning.entities;

public interface Rated {
    Boolean isLiked(Long id);
    Boolean isDisliked(Long id);
    int getLikes();
    int getDislikes();
}
