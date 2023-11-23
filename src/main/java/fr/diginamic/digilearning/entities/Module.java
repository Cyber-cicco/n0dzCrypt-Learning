package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dl_module")
public class Module {

	/** Identifiant */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String libelle;
	private String photo;
	@ManyToOne
	@JoinColumn(name = "formation_id")
	private Formation formation;

	@OneToMany(mappedBy = "module")
	@Builder.Default
	private List<SousModule> sousModules = new ArrayList();

}
