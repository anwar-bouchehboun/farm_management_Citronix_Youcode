package com.projet.citronix.repository;

import com.projet.citronix.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FermeRepository extends JpaRepository<Ferme, Long> {
    

    
    boolean existsByNom(String nom);
    

}
