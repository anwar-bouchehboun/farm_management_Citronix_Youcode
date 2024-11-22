package com.projet.citronix.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "arbres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Arbre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de plantation est obligatoire.")
   /* @MaximumAge
    @PlantingPeriod
    *
    */

    @Column(nullable = false)
    private LocalDate datePlantation;

    @Transient
    private int age;

    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;

    @OneToMany(mappedBy = "arbre")
    private List<DetailRecolte> details;

    @PrePersist
    @PreUpdate
    public void calculerAgeEtVerifierPeriodePlantation() {
        this.age =  Period.between(datePlantation, LocalDate.now()).getYears();
    }

    public int getAge() {
        return Period.between(datePlantation, LocalDate.now()).getYears();
    }


    public double calculerProductiviteAnnuelle() {
        if (getAge() < 3) {
            return 2.5;
        } else if (getAge() >= 3 && getAge() <= 10) {
            return 12.0;
        } else {
            return 20.0;
        }
    }


}
