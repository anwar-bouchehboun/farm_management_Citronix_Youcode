package com.projet.citronix.service.impl;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.dto.response.ChampData;
import com.projet.citronix.entity.Arbre;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.ArbreMapper;
import com.projet.citronix.repository.ArbreRepository;
import com.projet.citronix.repository.ChampRepository;
import com.projet.citronix.service.ArbreInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArbreService implements ArbreInterface {

    private final ArbreRepository arbreRepository;
    private final ChampRepository champRepository;
    private final ArbreMapper arbreMapper = ArbreMapper.INSTANCE;

    @Override
    public ArbreDto creerArbre(ArbreDto arbreDto) {
        Champ champ = champRepository.findById(arbreDto.getChampid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Champ non trouvé avec l'ID: " + arbreDto.getChampid()));

        validateDensiteArbres(champ);
        
        Arbre arbre = convertToEntity(arbreDto, champ);

        validateAge(arbre);
        validatePeriodePlantation(arbre);
        Arbre savedArbre = arbreRepository.save(arbre);
        return arbreMapper.ArbreToDTO(savedArbre);
    }

    @Override
    public ArbreDto modifierArbre(Long id, ArbreDto arbreDto) {
   arbreRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Arbre non trouvé avec l'ID: " + id));

        Champ champ = champRepository.findById(arbreDto.getChampid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Champ non trouvé avec l'ID: " + arbreDto.getChampid()));

        Arbre arbre = convertToEntity(arbreDto, champ);
        arbre.setId(id);
        validateAge(arbre);
        validatePeriodePlantation(arbre);
        Arbre updatedArbre = arbreRepository.save(arbre);
        return arbreMapper.ArbreToDTO(updatedArbre);
    }


    @Override
    public Optional<ArbreData> getArbreById(Long id) {
        return Optional.of(arbreRepository.findById(id)
                .map(this::convertToData)
                .orElseThrow(() -> new NotFoundExceptionHndler("Arbre non trouvée avec l'ID: " + id)));
    }

    @Override
    public List<ArbreData> getAllArbres() {
        return arbreRepository.findAll().stream()
                .map(this::convertToData)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerArbre(Long id) {
        Arbre arbre = arbreRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Arbre non trouvé avec l'ID: " + id));
        arbreRepository.delete(arbre);
    }

    private void validateDensiteArbres(Champ champ) {
        if (champ.getArbres() != null) {
            double maxArbres = champ.getSuperficie() * 100; // 100 arbres par hectare
            if (champ.getArbres().size() >= maxArbres) {
                throw new ValidationException(String.format(
                    "Densité maximale atteinte (%d arbres pour %.2f hectares)", 
                    champ.getArbres().size(), 
                    champ.getSuperficie()
                ));
            }
        }
    }

   private void validateAge(Arbre arbre) {
        int age = Period.between(arbre.getDatePlantation(), LocalDate.now()).getYears();
        if (age > 20) {
            throw new ValidationException("Un arbre ne peut être productif au-delà de 20 ans");
        }

    }

   private void validatePeriodePlantation(Arbre arbre) {
        int moisPlantation = arbre.getDatePlantation().getMonthValue();
       if (arbre.getDatePlantation().isBefore(LocalDate.now())) {
           throw new ValidationException("La date de plantation ne peut pas être antérieure au  date nouveau\n");
       }
        if (moisPlantation < 3 || moisPlantation > 5) {
            throw new ValidationException("L'arbre ne peut être planté qu'entre mars et mai");
        }
    }

    private Arbre convertToEntity(ArbreDto arbreDto, Champ champ) {
        Arbre arbre = arbreMapper.arbreDTOToEntity(arbreDto);
        arbre.setChamp(champ);
        arbre.calculerAgeEtVerifierPeriodePlantation();
        return arbre;
    }

    private ArbreData convertToData(Arbre arbre) {

        double productivite = arbre.calculerProductiviteAnnuelle();
        String categorie = determinerCategorieAge(arbre.getAge());
        
        return ArbreData.builder()
                .datePlantation(arbre.getDatePlantation())
                .nomChamp(arbre.getChamp().getNom())
                .superficie(arbre.getChamp().getSuperficie())
                .age(arbre.getAge())
                .productiviteParSaison(productivite)
                .categorieAge(categorie)
                .build();
    }

    private double calculerProductivite(int age) {
        if (age < 3) {
            return 2.5;
        } else if (age >= 3 && age <= 10) {
            return 12.0;
        } else {
            return 20.0;
        }
    }

    private String determinerCategorieAge(int age) {
        if (age < 3) {
            return "Arbre jeune";
        } else if (age >= 3 && age <= 10) {
            return "Arbre mature";
        } else {
            return "Arbre vieux";
        }
    }
}
