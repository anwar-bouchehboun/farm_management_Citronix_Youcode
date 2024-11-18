package com.projet.citronix.service.impl;


import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.displaydata.ChampData;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.ChampsMapper;
import com.projet.citronix.repository.ChampRepository;
import com.projet.citronix.repository.FermeRepository;
import com.projet.citronix.service.ChampInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChampService implements ChampInterface {

    private final ChampRepository champRepository;
    private final FermeRepository fermeRepository;

    private ChampsMapper champsMapper=ChampsMapper.INSTANCE;


    @Override
    public ChampDto creerChamp(ChampDto champDto) {
        Ferme ferme = fermeRepository.findById(champDto.getFermeid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + champDto.getFermeid()));
        
        Champ champ = convertToEntity(champDto, ferme);
        
        if (!champ.isValid()) {
            if (!champ.isSuperficieValid()) {
                throw new ValidationException("La superficie doit être entre 0,1 et 50% de la superficie de la ferme");
            }
            throw new ValidationException("La ferme a déjà atteint le nombre maximum de champs (10)");
        }
        
        Champ savedChamp = champRepository.save(champ);
        return convertToDto(savedChamp);
    }

    @Override
    public ChampDto modifierChamp(Long id, ChampDto champDto) {
        Champ existingChamp = champRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Champ non trouvé avec l'ID: " + id));
        
        Ferme ferme = fermeRepository.findById(champDto.getFermeid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + champDto.getFermeid()));
        
        Champ champ = convertToEntity(champDto, ferme);
        champ.setId(id);
        
        if (!champ.isValid()) {
            if (!champ.isSuperficieValid()) {
                throw new ValidationException("La superficie doit être entre 0,1 et 50% de la superficie de la ferme");
            }
            throw new ValidationException("La ferme a déjà atteint le nombre maximum de champs (10)");
        }
        
        Champ updatedChamp = champRepository.save(champ);
        return convertToDto(updatedChamp);
    }

    @Override
    public Optional<ChampData> getChampById(Long id) {
        return Optional.of(champRepository.findById(id)
                .map(this::convertToData)
                .orElseThrow(() -> new NotFoundExceptionHndler("champ non trouvée avec l'ID: " + id)));
    }

    @Override
    public List<ChampData> getAllChamps() {
        return champRepository.findAll().stream()
                .map(this::convertToData).collect(Collectors.toList());
    }
    @Override
    public void supprimerChamp(Long id) {
        Champ champ=  champRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("champ non trouvée avec l'ID: " + id));

        champRepository.delete(champ);
    }
    private ChampDto convertToDto(Champ champ) {
        return ChampDto.builder()
                .fermeid(champ.getFerme().getId())
                .nom(champ.getNom())
                .superficie(champ.getSuperficie())
                .build();
    }

    private ChampData convertToData(Champ champ){
        return ChampData.builder()
                .fermename(champ.getFerme().getNom())
                .nom(champ.getNom())
                .superficie(champ.getSuperficie())
                .dateCreation(champ.getFerme().getDateCreation())
                .localisation(champ.getFerme().getLocalisation())
                .superficieFerme(champ.getFerme().getSuperficie())
                .build();
    }

    private Champ convertToEntity(ChampDto champDto, Ferme ferme) {
        Champ champ = champsMapper.champsDTOToEntity(champDto);
        champ.setFerme(ferme);
        return champ;
    }

    private void validateChampBeforeSave(Champ champ) {
        if (champ.getFerme() != null) {
            if (champ.getFerme().getSuperficie() / 2 < champ.getSuperficie()) {
                throw new ValidationException("La superficie du champ ne peut pas dépasser 50% de la superficie de la ferme");
            }
            
            if (champ.getFerme().getChamps() != null &&
                champ.getFerme().getChamps().size() >= 10) {
                throw new ValidationException("La ferme a déjà atteint le nombre maximum de champs (10)");
            }
        }
    }
}
