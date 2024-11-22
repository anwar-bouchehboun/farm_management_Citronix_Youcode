package com.projet.citronix.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projet.citronix.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fermes")
@Data
@Getter 
@Setter
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
    @DecimalMin(value = "0.2", message = "La superficie doit être d'au moins 0.2 hectare.")
    @Column(nullable = false)
    private Double superficie;

    @PastOrPresent(message = "La date de création doit être dans le passé ou le présent.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Size(max = 10, message = "Une ferme ne peut pas avoir plus de 10 champs")
    private List<Champ> champs;



    @PrePersist
    @PreUpdate
    public void validateFerme() {
        if (champs != null && champs.size() > 10) {
            throw new ValidationException("Une ferme ne peut pas avoir plus de 10 champs");
        }
    }

}
