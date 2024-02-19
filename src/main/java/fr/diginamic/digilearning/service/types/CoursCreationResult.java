package fr.diginamic.digilearning.service.types;

import fr.diginamic.digilearning.entities.Cours;

public record CoursCreationResult(Cours cours, CoursCreationDiagnostics diagnostics) {
}
