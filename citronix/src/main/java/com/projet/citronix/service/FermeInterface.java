package com.projet.citronix.service;

import com.projet.citronix.dto.FermeDto;

import java.util.List;
import java.util.Optional;

public interface FermeInterface {
    
    FermeDto creerFerme(FermeDto fermeDto);
    
    FermeDto modifierFerme(Long id,FermeDto fermeDto);
    
    Optional<FermeDto> getFermeById(Long id);
    
    List<FermeDto> getAllFermes();
    
    void supprimerFerme(Long id);

}
