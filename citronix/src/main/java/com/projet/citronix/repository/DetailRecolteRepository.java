package com.projet.citronix.repository;

import com.projet.citronix.entity.DetailRecolte;
import com.projet.citronix.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DetailRecolteRepository extends JpaRepository<DetailRecolte,Long> {


    @Query("SELECT COUNT(d) > 0 FROM DetailRecolte d " +
           "JOIN d.recolte r " +
           "WHERE d.arbre.id = :arbreId " +
           "AND r.saison = :saison " +
           "AND YEAR(r.dateRecolte) = :annee")
    boolean existsByArbreAndSaison(
        @Param("arbreId") Long arbreId,
        @Param("saison") Saison saison,
        @Param("annee") int annee
    );

    @Query("SELECT SUM(d.quantiteParArbre) FROM DetailRecolte d WHERE d.recolte.id = :recolteId")
    Double sumQuantiteByRecolteId(@Param("recolteId") Long recolteId);



    @Query("SELECT COUNT(d) > 0 FROM DetailRecolte d " +
           "WHERE d.arbre.champ.id = :champId " +
           "AND d.recolte.saison = :saison " +
           "AND YEAR(d.recolte.dateRecolte) = :annee " +
           "AND d.recolte.id <> :recolteId")
    boolean existsByChampAndSaisonAndDifferentRecolte(
        @Param("champId") Long champId,
        @Param("saison") Saison saison,
        @Param("annee") int annee,
        @Param("recolteId") Long recolteId
    );

}
