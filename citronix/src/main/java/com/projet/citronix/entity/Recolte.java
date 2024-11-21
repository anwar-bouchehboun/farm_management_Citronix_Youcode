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
import java.util.ArrayList;
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
    private Double quantiteTotale = 0.0;


    @OneToMany(mappedBy = "recolte",fetch = FetchType.EAGER)
    private List<DetailRecolte> details;

    @OneToMany(mappedBy = "recolte",fetch = FetchType.LAZY)
    private List<Vente> ventes;

    @PrePersist
    public void beforeSave() {
        if (this.quantiteTotale == null) {
            this.quantiteTotale = 0.0;
        }
    }

    public void addDetailRecolte(DetailRecolte detail) {
        if (details == null) {
            details = new ArrayList<>();
        }
        details.add(detail);
        this.quantiteTotale += detail.getQuantiteParArbre();
    }
}
