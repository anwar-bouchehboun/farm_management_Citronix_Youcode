package com.projet.citronix.dto;


import com.projet.citronix.validation.MaximumAge;
import com.projet.citronix.validation.PlantingPeriod;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArbreDto {


    @NotNull(message = "La date de plantation est obligatoire.")
    @MaximumAge
    @PlantingPeriod
    @Column(nullable = false)
    private LocalDate datePlantation;

    @NotNull(message = "La Champs id  est obligatoire.")
    private Long champid;


}
