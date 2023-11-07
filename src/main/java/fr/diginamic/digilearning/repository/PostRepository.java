package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.old.Post;

public interface PostRepository extends JpaRepository<Post, Long>  {

}
