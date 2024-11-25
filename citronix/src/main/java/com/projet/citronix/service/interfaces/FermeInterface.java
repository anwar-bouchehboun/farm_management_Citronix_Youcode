package com.projet.citronix.service.interfaces;

import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.dto.response.FermeData;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FermeInterface {
    
    FermeDto creerFerme(FermeDto fermeDto);
    
    FermeDto modifierFerme(Long id,FermeDto fermeDto);
    
    Optional<FermeData> getFermeById(Long id);
    
    List<FermeData> getAllFermes(Pageable pageable);
    
    void supprimerFerme(Long id);

    List<FermeData> rechercherFermes(String nom, LocalDate dateCreation, String localisation, Double superficie);

}
