package com.projet.citronix.service;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.response.ArbreData;

import java.util.List;
import java.util.Optional;

public interface ArbreInterface {

    ArbreDto creerArbre(ArbreDto arbreDto);

    ArbreDto modifierArbre(Long id, ArbreDto arbreDto);

    Optional<ArbreData> getArbreById(Long id);

    List<ArbreData> getAllArbres();

    void supprimerArbre(Long id);
}
