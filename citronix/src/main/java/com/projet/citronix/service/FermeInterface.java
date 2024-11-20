package com.projet.citronix.service;

import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.dto.response.FermeData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FermeInterface {
    
    FermeDto creerFerme(FermeDto fermeDto);
    
    FermeDto modifierFerme(Long id,FermeDto fermeDto);
    
    Optional<FermeData> getFermeById(Long id);
    
    List<FermeData> getAllFermes();
    
    void supprimerFerme(Long id);

    List<FermeData> rechercherFermes(String nom, LocalDate dateCreation, String localisation, Double superficie);

}
