package com.projet.citronix.service.impl;


import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.mapper.FermeMapper;
import com.projet.citronix.repository.FermeRepository;
import com.projet.citronix.service.FermeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FermeService implements FermeInterface {

    private final FermeRepository fermeRepository;
    private FermeMapper fermeMapper=FermeMapper.INSTANCE;


    @Override
    public FermeDto creerFerme(FermeDto fermeDto) {
        if (fermeRepository.existsByNom(fermeDto.getNom())) {
            throw new ValidationException("Une ferme avec ce nom existe déjà.");
        }
        Ferme ferme=fermeMapper.toEntity(fermeDto);
    
        fermeRepository.save(ferme);
        return  fermeMapper.toDto(ferme);
    }

    @Override
    public FermeDto modifierFerme(Long id, FermeDto fermeDto) {

       fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + id));

        if (fermeRepository.existsByNom(fermeDto.getNom())) {
            throw new ValidationException("Une ferme avec ce nom existe déjà.");
        }
        Ferme fermeToUpdate = fermeMapper.toEntity(fermeDto);
        fermeToUpdate.setId(id);
        
        fermeRepository.save(fermeToUpdate);
        return fermeMapper.toDto(fermeToUpdate);
    }


    @Override
    public Optional<FermeDto> getFermeById(Long id) {
        return Optional.of(fermeRepository.findById(id)
                .map(fermeMapper::toDto)
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + id)));
    }

    @Override
    public List<FermeDto> getAllFermes() {
        return   fermeRepository.findAll().stream()
                .map(fermeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerFerme(Long id) {
      Ferme ferme=  fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + id));

        fermeRepository.delete(ferme);

    }
}
