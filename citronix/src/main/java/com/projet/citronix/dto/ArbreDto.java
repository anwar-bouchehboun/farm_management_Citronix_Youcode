package com.projet.citronix.dto;



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

    @Column(nullable = false)
    private LocalDate datePlantation;

    @NotNull(message = "La Champs id  est obligatoire.")
    private Long champid;


}
