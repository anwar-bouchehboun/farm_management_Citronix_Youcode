package com.projet.citronix.service;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.utilitaire.ArbreSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArbreInterface {

    ArbreDto creerArbre(ArbreDto arbreDto);

    ArbreDto modifierArbre(Long id, ArbreDto arbreDto);

    Optional<ArbreData> getArbreById(Long id);

    List<ArbreData> getAllArbres(Pageable pageable);

    void supprimerArbre(Long id);

    Page<ArbreData> getAllArbresWithPagination(ArbreSearchCriteria criteria);
}
