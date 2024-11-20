package com.projet.citronix.repository;

import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ChampRepository extends JpaRepository<Champ,Long> {


    @Query("SELECT DISTINCT c FROM Champ c LEFT JOIN c.arbres a")
    List<Champ> findAllWith();

}
