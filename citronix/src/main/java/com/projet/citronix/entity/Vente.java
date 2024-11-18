package com.projet.citronix.entity;



import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Entity
@Table(name = "ventes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de vente est requise.")
    private LocalDate dateVente;

    @NotNull(message = "Le prix unitaire est requis.")
    @Min(value = 0, message = "Le prix unitaire doit être positif.")
    private Double prixUnitaire;

    @NotNull(message = "La quantité est requise.")
    @Min(value = 1, message = "La quantité doit être au moins 1.")
    private Integer quantite;

    @NotNull(message = "Le client est requis.")
    private String client;

    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;

    public Double calculerRevenu() {
        return quantite * prixUnitaire;
    }
}
