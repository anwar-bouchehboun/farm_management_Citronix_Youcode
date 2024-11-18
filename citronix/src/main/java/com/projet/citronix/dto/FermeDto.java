package com.projet.citronix.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeDto {

    @NotBlank(message = "Le nom de la ferme est obligatoire.")
    private String nom;

    @NotBlank(message = "La localisation est obligatoire.")
    private String localisation;

    @Positive(message = "La superficie doit être un nombre positif.")
    @DecimalMin(value = "0.1", message = "La superficie doit être d'au moins 0.1 hectare.")
    private Double superficie;

    @PastOrPresent(message = "La date de création doit être dans le passé ou le présent.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;
}
