package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import fr.diginamic.digilearning.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>  {

}
