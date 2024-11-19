package com.projet.citronix.dto.response;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArbreData {

    private LocalDate datePlantation;
    private String nomChamp;
    private  Double superficie;


}
