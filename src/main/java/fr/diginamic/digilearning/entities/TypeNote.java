package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TYPE_NOTE")
public class TypeNote {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	/** code */
	@Column(name = "CODE")
	private String code;

	/** libelle : String */
	@Column(name = "LIBELLE")
	private String libelle;

	/** descriptif */
	@Column(name = "DESCRIPTIF")
	private String descriptif;

	/** ordre : int */
	@Column(name = "ORDRE")
	private int ordre;

	/**
	 * Constructeur
	 * 
	 * @param id      identifiant
	 * @param code    code
	 * @param libelle libellé
	 * @param ordre   ordre d'affichage
	 */
	public TypeNote(Long id, String code, String libelle, int ordre) {
		this.id = id;
		this.code = code;
		this.libelle = libelle;
		this.ordre = ordre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TypeNote typeNote = (TypeNote) o;
		return Objects.equals(libelle, typeNote.libelle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(libelle);
	}
}
