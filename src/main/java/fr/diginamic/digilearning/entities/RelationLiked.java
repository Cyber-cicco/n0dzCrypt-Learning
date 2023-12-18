package fr.diginamic.digilearning.entities;

/**
 * Contrat sur les entités servant de relation
 * entre utilisateur et entité pouvant etre
 * likées / dislikées.
 *
 * @author Abel Ciccoli
 */
public interface RelationLiked {
    Boolean getLiked();
    Boolean getDisliked();
    void setLiked(Boolean liked);
    void setDisliked(Boolean disliked);
    Utilisateur getUtilisateur();

}
