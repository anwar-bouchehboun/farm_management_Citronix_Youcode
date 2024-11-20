package com.projet.citronix.repository;

import com.projet.citronix.entity.Champ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ChampRepository extends JpaRepository<Champ, Long> {

    @Query(value = "SELECT c FROM Champ c LEFT JOIN FETCH c.ferme",
           countQuery = "SELECT COUNT(c) FROM Champ c")
    Page<Champ> findAllWith(Pageable pageable);

}
