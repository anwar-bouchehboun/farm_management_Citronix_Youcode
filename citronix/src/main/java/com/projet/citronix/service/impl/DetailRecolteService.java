package com.projet.citronix.service.impl;

import com.projet.citronix.dto.DetailsRecolteDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.dto.response.DetaailRecolteData;
import com.projet.citronix.entity.Arbre;
import com.projet.citronix.entity.DetailRecolte;
import com.projet.citronix.entity.Recolte;
import com.projet.citronix.enums.CategorieAge;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.ArbreMapper;
import com.projet.citronix.mapper.DetailsMapper;
import com.projet.citronix.repository.ArbreRepository;
import com.projet.citronix.repository.DetailRecolteRepository;
import com.projet.citronix.repository.RecolteRepository;
import com.projet.citronix.service.DetailRecolteInterface;
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
public class DetailRecolteService implements DetailRecolteInterface {
    private final RecolteRepository recolteRepository;
    private final ArbreRepository arbreRepository;
    private final DetailRecolteRepository detailRecolteRepository;
    private final DetailsMapper detailsMapper = DetailsMapper.INSTANCE;

    private final ArbreMapper arbreMapper = ArbreMapper.INSTANCE;
    private final RecolteService recolteService;

    @Override

    public DetailsRecolteDto creerDetailsRecolte(DetailsRecolteDto detailsRecolteDto) {
        Recolte recolte = recolteRepository.findById(detailsRecolteDto.getRecolteid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Récolte non trouvée"));

        Arbre arbre = arbreRepository.findById(detailsRecolteDto.getArbreid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Arbre non trouvé"));

        validateArbreAge(arbre);

      //  validateChampSaison(arbre.getChamp().getId(), recolte);

        validateArbreSaison(arbre.getId(), recolte);

        validateProductivite(arbre,detailsRecolteDto.getQuantiteParArbre());


        DetailRecolte detailRecolte = detailsMapper.toEntity(detailsRecolteDto);
        detailRecolte.setArbre(arbre);

        detailRecolte.setRecolte(recolte);

        DetailRecolte savedDetail = detailRecolteRepository.save(detailRecolte);
        
        recolteService.updateQuantiteTotal(detailsRecolteDto.getRecolteid());
        
        return detailsMapper.toDto(savedDetail);
    }



    @Override
    public DetailsRecolteDto modifierDetailsRecolte(Long id, DetailsRecolteDto detailsRecolteDto) {
        DetailRecolte existingDetail = detailRecolteRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Détail récolte non trouvé avec l'id: " + id));

        Arbre arbre = arbreRepository.findById(detailsRecolteDto.getArbreid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Arbre non trouvé"));

        validateArbreAge(arbre);
        validateProductivite(arbre, detailsRecolteDto.getQuantiteParArbre());

        existingDetail.setQuantiteParArbre(detailsRecolteDto.getQuantiteParArbre());
        existingDetail.setArbre(arbre);

        DetailRecolte updatedDetail = detailRecolteRepository.save(existingDetail);
        recolteService.updateQuantiteTotal(existingDetail.getRecolte().getId());
        
        return detailsMapper.toDto(updatedDetail);
    }

    @Override
    public Optional<DetaailRecolteData> getRecoletById(Long id) {
        return detailRecolteRepository.findById(id)
                .map(detail -> DetaailRecolteData.builder()
                        .quantiteParArbre(detail.getQuantiteParArbre())
                        .recolteDate(detail.getRecolte().getDateRecolte().toString())
                        .arbreName("Arbre " + detail.getArbre().getId())
                        .build());
    }

    @Override
    public List<DetaailRecolteData> getAllDetailsRecoltes(Pageable pageable) {
        return detailRecolteRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(detail -> DetaailRecolteData.builder()
                        .quantiteParArbre(detail.getQuantiteParArbre())
                        .recolteDate(detail.getRecolte().getDateRecolte().toString())
                        .arbreName("Arbre : " + detail.getArbre().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerDetailsRecolte(Long id) {
      if (!detailRecolteRepository.existsById(id)){
          throw new ValidationException(("DetailsRecolte non trouvé"));

      //   DetailRecolte detailRecolte=detailRecolteRepository.getReferenceById(id);
        //  recolteService.updateQuantiteForDeleteArbre(detailRecolte.getRecolte().getId());
      }
        detailRecolteRepository.deleteById(id);

    }

    //validation for Ajout et modifier
    public void   validateProductivite(Arbre arbre, Double getQuantiteParArbre){
        ArbreData arbreData=arbreMapper.arbreTOdata(arbre);
        arbreData.calculerProductiviteEtCategorie();
        
        if (getQuantiteParArbre >  arbreData.getProductiviteParSaison()) {
            throw new ValidationException("Quantité invalide. Maximum autorisé: " +  arbreData.getProductiviteParSaison() + " kg pour un " + arbreData.getCategorieAge() + " (" + arbreData.getAge() + " ans)");
        }
    }

    private void validateChampSaison(Long champId, Recolte recolte) {
        boolean champDejaRecolte = detailRecolteRepository.existsByChampAndSaison(
                champId,
                recolte.getSaison(),
                recolte.getDateRecolte().getYear()
        );

        if (champDejaRecolte) {
            throw new ValidationException(
                    String.format("Le champ a déjà été récolté pour la saison %s de l'année %d",
                            recolte.getSaison(),
                            recolte.getDateRecolte().getYear())
            );
        }
    }

    private void validateArbreSaison(Long arbreId, Recolte recolte) {
        boolean arbreDejaRecolte = detailRecolteRepository.existsByArbreAndSaison(
                arbreId,
                recolte.getSaison(),
                recolte.getDateRecolte().getYear()
        );

        if (arbreDejaRecolte) {
            throw new ValidationException(
                    String.format("L'arbre a déjà été récolté pour la saison %s de l'année %d",
                            recolte.getSaison(),
                            recolte.getDateRecolte().getYear())
            );
        }
    }

    private void validateArbreAge(Arbre arbre) {
        if (arbre.getAge() >= 20) {
            throw new ValidationException(
                    String.format("Impossible de récolter cet arbre. L'âge minimum requis est de 20 ans. Age actuel: %d ans",
                            arbre.getAge())
            );
        }
    }
}
