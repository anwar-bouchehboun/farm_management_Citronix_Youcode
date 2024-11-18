package com.projet.citronix.dto.displaydata;


import com.projet.citronix.entity.Ferme;
import lombok.*;
import lombok.extern.java.Log;

import java.time.LocalDate;

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


}
