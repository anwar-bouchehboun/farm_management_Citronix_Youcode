package com.projet.citronix.dto.response;




import com.projet.citronix.entity.Recolte;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecolteData {

    private String saison;

    private LocalDate dateRecolte;

    private Double quantiteTotale ;




    public  static RecolteData tochampData(Recolte recolte){


        return RecolteData.builder()
                .dateRecolte(recolte.getDateRecolte())
                .saison(recolte.getSaison().name())
                .quantiteTotale(recolte.getQuantiteTotale())
                .build();
    }
}

