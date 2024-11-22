package com.projet.citronix.dto;



import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsRecolteDto {

    @NotNull(message = "La id  de recolte est requise.")
    private Long recolteid;

    @NotNull(message = "La id  de arbre est requise.")
    private Long arbreid;

    @Positive(message = "La quantité par arbre doit être positive.")
    @Column(nullable = false)
    private Double quantiteParArbre;
}
