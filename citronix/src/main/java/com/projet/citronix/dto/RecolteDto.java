package com.projet.citronix.dto;

import com.projet.citronix.enums.Saison;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecolteDto {


    @Enumerated(EnumType.STRING)
    @NotNull(message = "La saison est obligatoire.")
    private Saison saison;

    @NotNull(message = "La date de récolte est obligatoire.")
    @FutureOrPresent(message = "le date ne pas creer in en avant mais in date L'histoire maintenant ou apres \n ")
    private LocalDate dateRecolte;

    @PositiveOrZero(message = "La quantité totale doit être positive ou zéro.")
    @Builder.Default
    private Double quantiteTotale = 0.0;

}
