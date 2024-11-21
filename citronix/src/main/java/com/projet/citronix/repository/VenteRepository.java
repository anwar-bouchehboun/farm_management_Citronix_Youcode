package com.projet.citronix.repository;

import com.projet.citronix.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteRepository extends JpaRepository<Vente,Long> {
    @Query("SELECT SUM(v.quantite) FROM Vente v WHERE v.recolte.id = :recolteId")
    Double sumQuantiteByRecolteId(@Param("recolteId") Long recolteId);
}
