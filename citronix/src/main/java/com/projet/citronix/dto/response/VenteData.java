package com.projet.citronix.dto.response;


import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenteData {
    private LocalDate dateVente;
    private Double prixUnitaire;
    private Double quantite;
    private String client;
    private LocalDate recoltedate;
    private String saison;
    private Double revenu;

    public Double getRevenu() {
        return (quantite != null && prixUnitaire != null) ? quantite * prixUnitaire : 0.0;
    }
}
