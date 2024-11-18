package com.projet.citronix.repository;

import com.projet.citronix.entity.Champ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ChampRepository extends JpaRepository<Champ,Long> {

}
