package com.projet.citronix.service.interfaces;

import com.projet.citronix.dto.DetailsRecolteDto;
import com.projet.citronix.dto.response.DetaailRecolteData;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DetailRecolteInterface {

    DetailsRecolteDto creerDetailsRecolte(DetailsRecolteDto detailsRecolteDto);

    DetailsRecolteDto modifierDetailsRecolte(Long id,DetailsRecolteDto detailsRecolteDto);

    Optional<DetaailRecolteData> getRecoletById(Long id);

    List<DetaailRecolteData> getAllDetailsRecoltes(Pageable pageable);

    void supprimerDetailsRecolte(Long id);
}
