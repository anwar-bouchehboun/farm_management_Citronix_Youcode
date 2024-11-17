package com.projet.citronix.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;


@Entity
@Table(name = "champs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Champ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du champ est obligatoire.")
    @Column(nullable = false)
    private String nom;

    @Positive(message = "La superficie doit être un nombre positif.")
    @DecimalMin(value = "0.1", message = "La superficie minimale du champ est de 0,1 hectare")
    @DecimalMax(value = "0.5", message = "Un champ ne peut pas dépasser 50% de la superficie de la ferme")
    @Column(nullable = false)
    private Double superficie;

    @ManyToOne
    @JoinColumn(name = "ferme_id", nullable = false)
    private Ferme ferme;


    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Arbre> arbres;

    @PrePersist
    @PreUpdate
    public void validateChamp() {
        if (ferme.getSuperficie() / 2 < superficie) {
            throw new IllegalArgumentException("La superficie du champ ne peut pas dépasser 50% de la superficie de la ferme.");
        }
    }
    public boolean isSuperficieValid() {
        return superficie >= 0.1 && superficie <= (ferme.getSuperficie() * 0.5);
    }
}
