package com.projet.citronix.dto.response;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArbreData {
    private Long id;
    private LocalDate datePlantation;
    private String nomChamp;
    private  Double superficie;
    private int age;
    private double productiviteParSaison;
    private String categorieAge;

    public void calculerProductiviteEtCategorie() {
        if (age < 3) {
            this.productiviteParSaison = 2.5;
            this.categorieAge = "Arbre jeune";
        } else if (age <= 10) {
            this.productiviteParSaison = 12.0;
            this.categorieAge = "Arbre mature";
        } else {
            this.productiviteParSaison = 20.0;
            this.categorieAge = "Arbre vieux";
        }
    }
}
