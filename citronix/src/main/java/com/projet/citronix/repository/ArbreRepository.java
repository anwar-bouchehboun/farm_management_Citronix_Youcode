package com.projet.citronix.repository;

import com.projet.citronix.entity.Arbre;
import com.projet.citronix.entity.Champ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ArbreRepository  extends JpaRepository<Arbre,Long> {
    @Query("SELECT DISTINCT c FROM Arbre c LEFT JOIN c.champ a")
    List<Champ> findAllWith();
}
