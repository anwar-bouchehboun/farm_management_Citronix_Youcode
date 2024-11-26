package com.projet.citronix.service.impl;

import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.dto.response.RecolteData;
import com.projet.citronix.entity.*;
import com.projet.citronix.enums.Saison;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.RecolteMapper;
import com.projet.citronix.repository.FermeRepository;
import com.projet.citronix.repository.RecolteRepository;
import com.projet.citronix.repository.DetailRecolteRepository;
import com.projet.citronix.service.interfaces.RecoletInterfce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecolteService implements RecoletInterfce {

    private final RecolteRepository recolteRepository;
    private final DetailRecolteRepository detailRecolteRepository;
    private final FermeRepository fermeRepository;

    private final RecolteMapper recolteMapper = RecolteMapper.INSTANCE;

    @Override
    public RecolteDto creerRecolet(RecolteDto recolteDto) {
        validateRecolteDate(recolteDto);
        Recolte recolte = recolteMapper.RecolteDTOToEntity(recolteDto);
        recolteRepository.save(recolte);
        return recolteMapper.recolteDto(recolte);
    }

    @Override
    public RecolteDto modifierRecolet(Long id, RecolteDto recolteDto) {
        if (!recolteRepository.existsById(id)) {
            throw new ValidationException("Recolte non trouvée avec l'ID: " + id);
        }
        validateRecolteDate(recolteDto);
        Recolte recolte = recolteMapper.RecolteDTOToEntity(recolteDto);
        recolte.setId(id);
        recolteRepository.save(recolte);

        return recolteMapper.recolteDto(recolte);
    }

    @Override
    public Optional<RecolteData> getRecoletById(Long id) {
        return Optional.of(recolteRepository.findById(id)
                .map(RecolteData::tochampData)
                .orElseThrow(() -> new NotFoundExceptionHndler("Recolte non trouvée avec l'ID: " + id)));
    }

    @Override
    public List<RecolteData> getAllRecolets(Pageable pageable) {
        return recolteRepository.findAll(pageable).stream()
                .map(recolteMapper::toDtoData)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerRecolet(Long id) {
        if (!recolteRepository.existsById(id)) {
            throw new ValidationException("Recolte non trouvée avec l'ID: " + id);
        }
        recolteRepository.deleteById(id);
    }

    private void validateRecolteDate(RecolteDto recolteDto) {
        Month month = recolteDto.getDateRecolte().getMonth();
        Saison saison = recolteDto.getSaison();

        boolean isValidSeason;
        switch (saison) {
            case PRINTEMPS:
                isValidSeason = month.getValue() >= 3 && month.getValue() <= 5;
                break;
            case ETE:
                isValidSeason = month.getValue() >= 6 && month.getValue() <= 8;
                break;
            case AUTOMNE:
                isValidSeason = month.getValue() >= 9 && month.getValue() <= 11;
                break;
            case HIVER:
                isValidSeason = month.getValue() == 12 || month.getValue() <= 2;
                break;
            default:
                isValidSeason = false;
        }

        if (!isValidSeason) {
            throw new ValidationException("La date de récolte ne correspond pas à la saison indiquée");
        }
    }

    // update la quantite total de la recolte
    public void updateQuantiteTotal(Long recolteId) {
        Double quantiteTotal = detailRecolteRepository.sumQuantiteByRecolteId(recolteId);
        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new ValidationException("Récolte non trouvée"));

        recolte.setQuantiteTotale(quantiteTotal != null ? quantiteTotal : 0.0);
        recolteRepository.save(recolte);
    }
    /*
     * update Quntite
     * public void updateQuantiteForDeleteArbre(Long recolteId,Double quantiteVente)
     * {
     * Recolte recolte = recolteRepository.findById(recolteId)
     * .orElseThrow(() -> new
     * NotFoundExceptionHndler("Récolte non trouvée avec l'ID: " + recolteId));
     *
     * Double quantite =
     * detailRecolteRepository.sumQuantiteByRecolteId(recolteId)-quantiteVente;
     *
     * recolte.setQuantiteTotale(quantite);
     * recolteRepository.save(recolte);
     * }
     */
    public List<RecolteData> dataFerme(){
        return fermeRepository.findAll()
                .stream()
                .flatMap(ferme -> ferme.getChamps().stream())
                .flatMap(champ -> champ.getArbres().stream())
                .flatMap(arbre -> arbre.getDetails().stream())
                .map(detailRecolte -> detailRecolte.getRecolte())
                .distinct()
                .filter(recolte -> recolte.getId()!=null)
                .map(recolteMapper::toDtoData)
                .collect(Collectors.toList());
    }
    






    }
