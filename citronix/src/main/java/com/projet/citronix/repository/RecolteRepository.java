package com.projet.citronix.repository;

import com.projet.citronix.dto.response.RecolteData;
import com.projet.citronix.entity.Recolte;
import com.projet.citronix.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RecolteRepository extends JpaRepository<Recolte, Long> {
    @Query("SELECT  r.saison, r.dateRecolte, " +
            " SUM(rd.quantiteParArbre) " +
            "FROM Recolte r " +
            "JOIN r.details rd " +
            "JOIN rd.arbre a " +
            "JOIN a.champ c " +
            "JOIN c.ferme f " +
            "WHERE r.saison = :saison " +
            "AND f.id = :fermeId " +
            "GROUP BY  r.saison, r.dateRecolte, f.id, f.nom")
          List<RecolteData> findRecoltesByFermeAndSaison(Saison saison, Long fermeId);

    @Query("SELECT r FROM Recolte r " +
           "JOIN r.details rd " +
           "JOIN rd.arbre a " +
           "JOIN a.champ c " +
           "JOIN c.ferme f " +
           "WHERE f.id = :fermeId")
    List<Recolte> findAllbyFemreId(Long fermeId);

    List<Recolte> findByDateRecolteAfter(LocalDate date);
}
