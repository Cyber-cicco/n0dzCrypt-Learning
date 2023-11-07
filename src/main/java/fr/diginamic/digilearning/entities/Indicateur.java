package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "INDICATEUR")
public class Indicateur {
	/** id : int */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	/** formateur : Utilisateur */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FORMATEUR")
	private Utilisateur formateur;

	/** nom : String */
	@Column(name = "NOM")
	private String nom;

	/** valeur : double */
	@Column(name = "VALEUR")
	private double valeur;

}
