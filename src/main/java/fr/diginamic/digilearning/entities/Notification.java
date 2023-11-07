package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "NOTIFICATION")
public class Notification {

	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** lu */
	private boolean lu;

	/** dateEmission */
	@Column(name = "DATE_EMISSION")
	private LocalDateTime dateEmission;

	/** dateLecture */
	@Column(name = "DATE_LECTURE")
	private LocalDateTime dateLecture;

	/** destinataire */
	@ManyToOne
	@JoinColumn(name = "ID_DESTINATAIRE")
	private Utilisateur destinataire;

	/** message */
	private String message;
}
