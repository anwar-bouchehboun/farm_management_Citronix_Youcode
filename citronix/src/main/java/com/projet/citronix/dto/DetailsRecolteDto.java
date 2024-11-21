package com.projet.citronix.dto;



import lombok.*;

import javax.validation.constraints.NotNull;
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
}
