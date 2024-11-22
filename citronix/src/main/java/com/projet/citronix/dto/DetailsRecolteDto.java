package com.projet.citronix.dto;



import lombok.*;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.DecimalMin;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsRecolteDto {

    @NotNull(message = "L'ID de la récolte ne peut pas être null")
    private Long recolteid;

    @NotNull(message = "L'ID de l'arbre ne peut pas être null")
    private Long arbreid;

    @NotNull(message = "La quantité est requise")
    @Positive(message = "La quantité par arbre doit être positive")
    @DecimalMin(value = "0.1", message = "La quantité minimale est de 0.1 kg")
    private Double quantiteParArbre;
}
