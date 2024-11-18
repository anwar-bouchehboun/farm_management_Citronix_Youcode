package com.projet.citronix.dto;



import lombok.*;

import javax.persistence.Column;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampDto {

    @NotBlank(message = "Le nom du champ est obligatoire.")
    @Column(nullable = false)
    private String nom;

    @Positive(message = "La superficie doit être un nombre positif.")
    @DecimalMin(value = "0.1", message = "La superficie minimale du champ est de 0,1 hectare")
    @DecimalMax(value = "50", message = "Un champ ne peut pas dépasser 50% de la superficie de la ferme")
    @Column(nullable = false)
    private Double superficie;

    @NotNull(message = "Le femreid  du champ est obligatoire.")
    private Long fermeid;


}
