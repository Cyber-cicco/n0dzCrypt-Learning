package fr.diginamic.digilearning.entities.notation;

import fr.diginamic.digilearning.entities.Session;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BILAN_SESSION")
public class BilanSession {

	/** id : Long */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	/** datePrevue : LocalDate */
	@Column(name = "DATE_PREVUE")
	private LocalDate datePrevue;

	/** dateEffective : LocalDate */
	@Column(name = "DATE_EFFECTIVE")
	private LocalDate dateEffective;

	/** numero : int */
	@Column(name = "NUMERO")
	private int numero;

	/** appreciation : String */
	@Column(name = "APPRECIATION")
	private String appreciation;
	
	/** pointRoomMaster */
	@Column(name = "POINT_ROOM_MASTER")
	private String pointRoomMaster;

	/** session : Session */
	@ManyToOne
	@JoinColumn(name = "ID_SESSION")
	private Session session;

	/** publication : Boolean */
	@Column(name = "PUBLICATION")
	private Boolean publication;

}
