package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.PostForum;

public interface PostForumRepository extends JpaRepository<PostForum, Long>  {

}
