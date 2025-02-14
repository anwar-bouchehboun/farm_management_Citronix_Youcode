package com.projet.citronix.service.impl;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.dto.response.ChampData;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.ChampsMapper;
import com.projet.citronix.repository.ChampRepository;
import com.projet.citronix.repository.FermeRepository;
import com.projet.citronix.service.interfaces.ChampInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChampService implements ChampInterface {

    private final ChampRepository champRepository;
    private final FermeRepository fermeRepository;

    private ChampsMapper champsMapper = ChampsMapper.INSTANCE;

    @Override
    public ChampDto creerChamp(ChampDto champDto) {
        Ferme ferme = fermeRepository.findById(champDto.getFermeid())
                .orElseThrow(
                        () -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + champDto.getFermeid()));

        Champ champ = convertToEntity(champDto, ferme);

        validateChampBeforeSave(champ);

        Champ savedChamp = champRepository.save(champ);
        return champsMapper.champsToDTO(savedChamp);
    }

    @Override
    public ChampDto modifierChamp(Long id, ChampDto champDto) {
        Champ existingChamp = champRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Champ non trouvé avec l'ID: " + id));

        if (champDto.getSuperficie() < existingChamp.getSuperficie()) {
            throw new ValidationException(String.format(
                    "La nouvelle superficie (%.2f) ne peut pas être supérieure à la superficie actuelle (%.2f) du champ '%s'",
                    champDto.getSuperficie(),
                    existingChamp.getSuperficie(),
                    existingChamp.getNom()));
        }
        Ferme ferme = fermeRepository.findById(champDto.getFermeid())
                .orElseThrow(
                        () -> new NotFoundExceptionHndler("Ferme non trouvée avec l'ID: " + champDto.getFermeid()));

        Champ champ = convertToEntity(champDto, ferme);
        champ.setId(id);

        if (!champ.isValid()) {
            if (!champ.isSuperficieValid()) {
                throw new ValidationException("La superficie doit être entre 0,1 et 50% de la superficie de la ferme");
            }
            throw new ValidationException("La ferme a déjà atteint le nombre maximum de champs (10)");
        }

        Champ updatedChamp = champRepository.save(champ);
        return champsMapper.champsToDTO(updatedChamp);
    }

    @Override
    public Optional<ChampData> getChampById(Long id) {
        return Optional.of(champRepository.findById(id)
                .map(ChampData::tochampData)
                .orElseThrow(() -> new NotFoundExceptionHndler("champ non trouvée avec l'ID: " + id)));
    }

    @Override
    public List<ChampData> getAllChamps(Pageable pageable) {
        return champRepository.findAllWith(pageable).stream()
                .map(champsMapper::champDataDto).collect(Collectors.toList());
    }

    @Override
    public void supprimerChamp(Long id) {
        Champ champ = champRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("champ non trouvée avec l'ID: " + id));

        champRepository.delete(champ);
    }

    private ChampData convertToData(Champ champ) {
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

    private void validateChampBefore(Champ champ) {
        if (champ.getFerme() != null) {
            double maxSuperficie = champ.getFerme().getSuperficie() * 0.5;
            if (champ.getSuperficie() > maxSuperficie) {

                throw new ValidationException(String.format(
                        "La superficie du champ (%.2f) ne peut pas dépasser 50%% de la superficie de la ferme (%.2f)",
                        champ.getSuperficie(),
                        champ.getFerme().getSuperficie()));
            }

            if (champ.getFerme().getChamps() != null &&
                    champ.getFerme().getChamps().size() >= 10) {
                throw new ValidationException("La ferme a déjà atteint le nombre maximum de champs (10)");
            }
        }
    }

    private void validateChampBeforeSave(Champ champ) {
        if (champ.getFerme() != null) {
            Ferme ferme = champ.getFerme();
            double maxSuperficie = ferme.getSuperficie() * 0.5;

            // la somme
            double superficieTotaleExistante = ferme.getChamps().stream()
                    .mapToDouble(Champ::getSuperficie)
                    .sum();

            // la superficie du nouveau champ
            double superficieTotaleApresAjout = superficieTotaleExistante + champ.getSuperficie();

            if (superficieTotaleApresAjout > maxSuperficie) {
                throw new ValidationException(String.format(
                        "La superficie totale des champs (%.2f) dépasserait 50%% de la superficie de la ferme (%.2f)",
                        superficieTotaleApresAjout,
                        ferme.getSuperficie()));
            }
            if (ferme.getChamps().size() >= 10) {
                throw new ValidationException("La ferme a déjà atteint le nombre maximum de champs (10)");
            }
        }
    }

    public List<ChampData> get() {
        return champRepository.findAll()
                .stream()
                .filter(champ -> champ.getArbres().stream()
                        .flatMap(arbre -> arbre.getDetails().stream())
                        .anyMatch(detailRecolte -> detailRecolte.getQuantiteParArbre()==100))
                .map(champsMapper::champDataDto)
                .collect(Collectors.toList());
    }

}
