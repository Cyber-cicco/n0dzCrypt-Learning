package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeLogiciel;
import fr.diginamic.digilearning.entities.enums.TypeRessource;
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
@Table(name = "COMPTE_VIRTUEL")
public class CompteVirtuel implements Ressource {


	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	/** user */
	@Column(name = "USER")
	private String user;

	/** password */
	@Column(name = "PASSWORD")
	private String password;

	/** logiciel */
	@Column(name = "LOGICIEL")
	@Enumerated(EnumType.STRING)
	private TypeLogiciel logiciel = TypeLogiciel.ADOBE_CONNECT_MEETING;

	@OneToMany(mappedBy = "compteVirtuel")
	private List<CoursPlanifie> coursPlannifies;
	@Override
	public TypeRessource getTypeRessource() {
		return TypeRessource.COMPTE_CLASSE_VIRTUEL;
	}

	@Override
	public String getAttribute() {
		return "compte.user";
	}

	@Override
	public String getValue() {
		return this.user;
	}

	@Override
	public List<? extends AbstractPeriode> getIndisponibilites() {
		return new ArrayList<AbstractPeriode>();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
