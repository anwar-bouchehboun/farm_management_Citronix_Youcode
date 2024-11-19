package com.projet.citronix.dto.response;


import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.entity.Champ;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampData {


    private String nom;
    private Double superficie;
    private String fermename;
    private String localisation;
    private Double superficieFerme;
    private LocalDate dateCreation;
    List<ArbreDto> arberDtos;



    public  static ChampData tochampData(Champ champ){
        List<ArbreDto>
                arberDtoList=
                champ.getArbres() != null ?
                        champ.getArbres().stream()
                                .map(arbre -> ArbreDto.builder()
                                        .datePlantation(arbre.getDatePlantation())
                                        .champid(arbre.getChamp().getId())
                                        .build())
                                .collect(Collectors.toList()) :
                        Collections.emptyList();

        return ChampData.builder()
                .nom(champ.getNom())
                .dateCreation(champ.getFerme().getDateCreation())
                .nom(champ.getNom())
                .superficie(champ.getSuperficie())
                .localisation(champ.getFerme().getLocalisation())
                .fermename(champ.getFerme().getNom())
                .superficieFerme(champ.getFerme().getSuperficie())
                .arberDtos(arberDtoList)
                .build();
    }


}
