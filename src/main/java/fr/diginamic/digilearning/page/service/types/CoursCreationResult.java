package fr.diginamic.digilearning.page.service.types;

import fr.diginamic.digilearning.entities.Cours;

import java.util.Map;
import java.util.Optional;

public record CoursCreationResult(Cours cours, CoursCreationDiagnostics diagnostics) {
}
