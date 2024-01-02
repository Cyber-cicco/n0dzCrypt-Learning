package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CoursRepositoryTest {

    @Autowired
    private CoursRepository coursRepository;

    @Test
    public void firstTest(){
        System.out.println("test");
        List<String[]> cours =  coursRepository.getAllCoursForUser(1997L);
        assertEquals(3, 3);
    }

}