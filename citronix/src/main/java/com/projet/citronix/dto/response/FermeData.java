package com.projet.citronix.dto.response;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.entity.Ferme;
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
public class FermeData {


    private String nom;

    private String localisation;

    private Double superficie;

    private LocalDate dateCreation;
    List<ChampDto> champDtoList;


    public  static FermeData toFemreData(Ferme ferme){
        List<ChampDto>
                champDataList=
                ferme.getChamps() != null ?
                ferme.getChamps().stream()
                        .map(champ -> ChampDto.builder()
                                .nom(champ.getNom())
                                .superficie(champ.getSuperficie())
                                .build())
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        return FermeData.builder()
                .nom(ferme.getNom())
                .dateCreation(ferme.getDateCreation())
                .localisation(ferme.getLocalisation())
                .superficie(ferme.getSuperficie())
                .champDtoList(champDataList)
                .build();
    }


}
