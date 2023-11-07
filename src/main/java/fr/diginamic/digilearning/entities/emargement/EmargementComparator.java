package fr.diginamic.digilearning.entities.emargement;

import fr.diginamic.digilearning.entities.enums.TypeRole;

import java.util.Comparator;

public class EmargementComparator implements Comparator<Emargement> {

	public EmargementComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Emargement o1, Emargement o2) {
		if (o2.getDateJour().isAfter(o1.getDateJour())) {
			return -1;
		} else if (o2.getDateJour().isBefore(o1.getDateJour())) {
			return 1;
		} else {
			if (o2.getUtilisateur().hasRole(TypeRole.ROLE_FORMATEUR)
					&& !o1.getUtilisateur().hasRole(TypeRole.ROLE_FORMATEUR)) {
				return -1;
			} else if (o1.getUtilisateur().hasRole(TypeRole.ROLE_FORMATEUR)
					&& !o2.getUtilisateur().hasRole(TypeRole.ROLE_FORMATEUR)) {
				return 1;
			}
		}
		return 0;
	}

}
