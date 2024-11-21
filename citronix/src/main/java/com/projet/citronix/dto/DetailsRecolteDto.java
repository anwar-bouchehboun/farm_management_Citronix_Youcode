package com.projet.citronix.dto;



import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsRecolteDto {

    @Positive(message = "La quantité par arbre doit être positive.")
    @Column(nullable = false)
    private Double quantiteParArbre;

    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Long recolteid;

    @NotNull(message = "La id  de arbre est requise.")
    private Long arbreid;
}
