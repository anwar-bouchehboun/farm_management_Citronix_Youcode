package com.projet.citronix.repository;

import com.projet.citronix.entity.Arbre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbreRepository extends JpaRepository<Arbre, Long>  {
    @Query(value = "SELECT DISTINCT a FROM Arbre a LEFT JOIN a.champ c",
           countQuery = "SELECT COUNT(DISTINCT a) FROM Arbre a")
    Page<Arbre> findAllWithPagination(Pageable pageable);
    
    @Query(value = "SELECT DISTINCT a FROM Arbre a LEFT JOIN a.champ c WHERE c.id = :champId",
           countQuery = "SELECT COUNT(DISTINCT a) FROM Arbre a LEFT JOIN a.champ c WHERE c.id = :champId")
    Page<Arbre> findAllByChampId(@Param("champId") Long champId, Pageable pageable);
}
