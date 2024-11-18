package com.projet.citronix.repository;

import com.projet.citronix.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FermeRepository extends JpaRepository<Ferme, Long> {
    
    boolean existsByNom(String nom);
    
    @Query("SELECT f FROM Ferme f WHERE " +
           "f.nom = :nom OR " +
           "f.dateCreation = :dateCreation OR " +
           "f.localisation = :localisation OR " +
           "f.superficie = :superficie")
    List<Ferme> rechercherFermes(
            @Param("nom") String nom,
            @Param("dateCreation") LocalDate dateCreation,
            @Param("localisation") String localisation,
            @Param("superficie") Double superficie
    );
}
