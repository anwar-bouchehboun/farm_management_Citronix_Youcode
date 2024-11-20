package com.projet.citronix.service.impl;


import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.dto.response.FermeData;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.FermeMapper;
import com.projet.citronix.repository.FermeRepository;
import com.projet.citronix.service.FermeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FermeService implements FermeInterface {

    private final FermeRepository fermeRepository;
    private final FermeMapper fermeMapper=FermeMapper.INSTANCE;


    @Override
    public FermeDto creerFerme(FermeDto fermeDto) {
        validateSuperficie(fermeDto.getSuperficie());
        Ferme ferme = fermeMapper.toEntity(fermeDto);
        fermeRepository.save(ferme);
        return fermeMapper.toDto(ferme);
    }

    private void validateSuperficie(Double superficie) {
        if (superficie == null || superficie < 0.2) {
            throw new ValidationException("La superficie doit être d'au moins 0.2 hectare");
        }
    }

    @Override
    public FermeDto modifierFerme(Long id, FermeDto fermeDto) {
       fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + id));
                

        Ferme fermeToUpdate = fermeMapper.toEntity(fermeDto);
        fermeToUpdate.setId(id);
        fermeRepository.save(fermeToUpdate);
        return fermeMapper.toDto(fermeToUpdate);
    }


    @Override
    public Optional<FermeData> getFermeById(Long id) {
        return Optional.of(fermeRepository.findById(id)
                .map(FermeData::toFemreData)
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + id)));
    }

    @Override
    public List<FermeData> getAllFermes(Pageable pageable) {
        return   fermeRepository.findAllWithChamps(pageable).stream()
                .map(fermeMapper::toDtoData)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerFerme(Long id) {
      Ferme ferme=  fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + id));

        fermeRepository.delete(ferme);

    }

    @Override
    public List<FermeData> rechercherFermes(String nom, LocalDate dateCreation, String localisation, Double superficie) {
        return fermeRepository.rechercherFermes(nom, dateCreation, localisation, superficie)
                .stream()
                .map(fermeMapper::toDtoData)
                .collect(Collectors.toList());
    }


}
