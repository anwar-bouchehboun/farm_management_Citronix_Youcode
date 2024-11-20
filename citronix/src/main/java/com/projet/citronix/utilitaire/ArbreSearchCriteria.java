package com.projet.citronix.utilitaire;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArbreSearchCriteria {
    private Long champId;
    private Integer page;
    private Integer size;
}