package com.projet.citronix.repository;

import com.projet.citronix.entity.Recolte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  RecolteRepository  extends JpaRepository<Recolte,Long> {
}
