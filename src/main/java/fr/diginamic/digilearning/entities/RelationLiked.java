package fr.diginamic.digilearning.entities;

public interface RelationLiked {
    Boolean getLiked();
    Boolean getDisliked();
    void setLiked(Boolean liked);
    void setDisliked(Boolean disliked);
    Utilisateur getUtilisateur();

}
