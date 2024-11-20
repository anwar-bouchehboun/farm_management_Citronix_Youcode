package com.projet.citronix.entity;

import com.projet.citronix.enums.Saison;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "recoltes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La saison est obligatoire.")
    private Saison saison;

    @NotNull(message = "La date de récolte est obligatoire.")
    private LocalDate dateRecolte;

    @PositiveOrZero(message = "La quantité totale doit être positive ou zéro.")
    private Double quantiteTotale;


    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailRecolte> details;
}
