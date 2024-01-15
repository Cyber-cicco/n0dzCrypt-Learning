package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusChapitre;
import fr.diginamic.digilearning.entities.enums.StatusPublication;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Représente le chapitre d'un cours
 *
 * @author Abel Ciccoli
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "dl_chapitre")
public class Chapitre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String libelle;
	@Column(columnDefinition = "LONGTEXT")
	private String contenu;
	@Column(columnDefinition = "LONGTEXT")
	private String contenuNonPublie;
	private String lienVideo;
	private Integer ordre;
	private StatusPublication statusPublication;
	private StatusChapitre statusChapitre;
	@OneToMany(mappedBy = "qcm")
	@Builder.Default
	private List<QCMQuestion> qcmQuestions = new ArrayList<>();
	@OneToMany(mappedBy = "qcmPublie")
	@Builder.Default
	private List<QCMQuestion> qcmQuestionsPublies = new ArrayList<>();
	@OneToMany(mappedBy = "chapitre")
	private List<Question> questions;
	@ManyToOne
	@JoinColumn(name = "cours_id")
	private Cours cours;

	@Override
	public String toString() {
		return libelle + "\n";
	}

	public List<Question> getQuestionsNonSuppr() {
		return questions.stream().filter(question -> !question.getSupprimee()).toList();
	}

	public List<QCMQuestion> getRawQCMQuestions() {
		return qcmQuestions;
	}
	public List<QCMQuestion> getQcmQuestions() {
		return qcmQuestions.stream().sorted(Comparator.comparing(QCMQuestion::getOrdre)).toList();
	}
}
