package com.projet.citronix.service.interfaces;

import com.projet.citronix.dto.VenteDto;
import com.projet.citronix.dto.response.VenteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VenteInterface {



    VenteDto creerVente(VenteDto venteDto);

    VenteDto modifierVente(Long id,VenteDto venteDto);

    Optional<VenteData> getVentetById(Long id);

    List<VenteData> getAllVentes(Pageable pageable);
    Page<VenteData> getAllVent(Pageable pageable);
    void supprimerVente(Long id);
}
