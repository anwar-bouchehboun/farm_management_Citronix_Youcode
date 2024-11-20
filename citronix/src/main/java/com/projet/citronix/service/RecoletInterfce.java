package com.projet.citronix.service;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.dto.response.ChampData;
import com.projet.citronix.dto.response.RecolteData;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecoletInterfce {

    RecolteDto creerRecolet(RecolteDto recolteDto);

    RecolteDto modifierRecolet(Long id,RecolteDto recolteDto);

    Optional<RecolteData> getRecoletById(Long id);

    List<RecolteData> getAllRecolets(Pageable pageable);

    void supprimerRecolet(Long id);
}
