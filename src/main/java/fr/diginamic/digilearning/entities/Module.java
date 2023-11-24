package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
	@ManyToMany
	@JoinTable(name = "dl_module_formation",
			joinColumns = @JoinColumn(name = "id_module", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_formation", referencedColumnName = "ID")
	)
	private List<Formation> formations;
	@ManyToMany
	@JoinTable(name = "dl_module_smodule",
			joinColumns = @JoinColumn(name = "id_module", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_smodule", referencedColumnName = "id")
	)
	private List<SousModule> sousModules = new ArrayList();

}
