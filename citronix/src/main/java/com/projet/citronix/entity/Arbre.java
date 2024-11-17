package com.projet.citronix.entity;

import com.projet.citronix.enums.TypeArbre;
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
    @Column(nullable = false)
    private LocalDate datePlantation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeArbre type;

    @Transient
    private int age;

    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;

    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailRecolte> details;

    @PrePersist
    @PreUpdate
    public void calculerAgeEtVerifierPeriodePlantation() {
        this.age =  Period.between(datePlantation, LocalDate.now()).getYears();

        int mois = datePlantation.getDayOfMonth();
        if (mois < 2 || mois > 4) {
            throw new IllegalArgumentException("Les arbres doivent être plantés entre mars et mai.");
        }
    }



    @AssertTrue(message = "L'arbre ne peut être planté qu'entre mars et mai.")
    public boolean isDatePlantationValid() {
        return datePlantation.getMonthValue() >= 3 && datePlantation.getMonthValue() <= 5;
    }

    @AssertTrue(message = "Un arbre ne peut être productif au-delà de 20 ans.")
    public boolean isAgeValid() {
        return getAge() <= 20;
    }

}
