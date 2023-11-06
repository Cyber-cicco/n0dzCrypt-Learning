package fr.diginamic.digilearning.entities.enums;

public enum RoleEnum {

    ROLE_ADMINISTRATEUR("Administrateur", 1L),
    ROLE_PLANIFICATEUR("Planificateur", 2L),
    ROLE_FORMATEUR("Formateur", 3L),
    ROLE_STAGIAIRE("Stagiaire", 4L),
    ROLE_VISITEUR("Visiteur", 5L),
    ROLE_VISITEUR_ADMIN("Visiteur admin", 6L),
    ROLE_RESPONSABLE("Visiteur admin", 6L),
    ROLE_CONTACT("Contact", 7L);

    private Long id;

    private String libelle;

    RoleEnum(String libelle, Long id) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

}

