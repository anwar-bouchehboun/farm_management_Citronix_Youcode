package com.projet.citronix.utilitaire;

import com.projet.citronix.entity.Ferme;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FermeSpecification {

    public static Specification<Ferme> avecNom(String nom) {
        return (root, query, cb) -> {
            return nom == null ? null : cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%");
        };
    }

    public static Specification<Ferme> avecDateCreation(LocalDate dateCreation) {
        return (root, query, cb) -> {
            return dateCreation == null ? null : cb.equal(root.get("dateCreation"), dateCreation);
        };
    }

    public static Specification<Ferme> avecLocalisation(String localisation) {
        return (root, query, cb) -> {
            return localisation == null ? null : cb.like(cb.lower(root.get("localisation")), "%" + localisation.toLowerCase() + "%");
        };
    }

    public static Specification<Ferme> avecSuperficie(Double superficie) {
        return (root, query, cb) -> {
            return superficie == null ? null : cb.equal(root.get("superficie"), superficie);
        };
    }
}