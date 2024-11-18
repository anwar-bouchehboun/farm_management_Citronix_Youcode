package com.projet.citronix.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fermes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ferme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la ferme est obligatoire.")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "La localisation est obligatoire.")
    @Column(nullable = false)
    private String localisation;

    @Positive(message = "La superficie doit être un nombre positif.")
    @DecimalMin(value = "0.1", message = "La superficie doit être d'au moins 0.1 hectare.")
    @Column(nullable = false)
    private Double superficie;

    @PastOrPresent(message = "La date de création doit être dans le passé ou le présent.")
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;

    public boolean isValid() {
        return champs.size() <= 10;
    }

    @OneToMany(mappedBy = "ferme")
    private List<Champ> champs;

    @OneToMany(mappedBy = "ferme")
    private List<Recolte> recoltes;
}
