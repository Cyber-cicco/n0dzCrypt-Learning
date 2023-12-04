package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RatingService<T extends Rated, D extends RelationLiked> {

    private final JpaRepository<D, Long> repository;


    public void likePushed(T rated, Long idUtilisateur, Supplier<D> constructor){
        changeRelationForUtilisateur(rated, idUtilisateur, (relationQuestion) -> {
                    relationQuestion.setLiked(!relationQuestion.getLiked());
                    relationQuestion.setDisliked(false);
                    repository.save(relationQuestion);
                },
                () -> rated.getRelations().add(repository.save(constructor.get()))
        );
    }

    public void dislikePushed(T rated, Long idUtilisateur, Supplier<D> constructor){
        changeRelationForUtilisateur(rated, idUtilisateur, (relationQuestion) -> {
                    relationQuestion.setDisliked(!relationQuestion.getDisliked());
                    relationQuestion.setLiked(false);
                    repository.save(relationQuestion);
                },
                () -> rated.getRelations().add(repository.save(constructor.get()))
        );
    }

    private void changeRelationForUtilisateur(
            Rated<D> rated,
            Long idUtilisateur,
            Consumer<D> saver,
            Runnable orElseAction
    ){
        rated.getRelations().stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .findFirst()
                .ifPresentOrElse(saver , orElseAction);
    }
}
