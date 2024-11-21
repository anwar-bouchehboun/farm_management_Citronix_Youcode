package com.projet.citronix.dto;

import com.projet.citronix.entity.Recolte;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenteDto {
    @NotNull(message = "La date de vente est requise.")
    private LocalDate dateVente;

    @NotNull(message = "Le prix unitaire est requis.")
    @Min(value = 0, message = "Le prix unitaire doit être positif.")
    private Double prixUnitaire;

    @NotNull(message = "La quantité est requise.")
    @Min(value = 1, message = "La quantité doit être au moins 1.")
    private Double quantite;

    @NotNull(message = "Le client est requis.")
    private String client;

    @NotNull(message = "La id  de recolte est requise.")
    private Long recolteid;


    private  Double revenu;


}
