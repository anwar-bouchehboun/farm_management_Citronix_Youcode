package com.projet.citronix.service;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.response.ChampData;

import java.util.List;
import java.util.Optional;

public interface ChampInterface {

    ChampDto creerChamp(ChampDto champDto);

    ChampDto modifierChamp(Long id,ChampDto champDto);

    Optional<ChampData> getChampById(Long id);

    List<ChampData> getAllChamps();

    void supprimerChamp(Long id);
}
