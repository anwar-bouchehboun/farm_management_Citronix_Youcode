package com.projet.citronix.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
@Table(name = "detailrecoltes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailRecolte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "La quantité par arbre doit être positive.")
    @Column(nullable = false)
    private Double quantiteParArbre;

    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;

    @ManyToOne
    @JoinColumn(name = "arbre_id", nullable = false)
    private Arbre arbre;

    @PrePersist
    public void beforeSave() {
        if (this.recolte != null) {
            Double ancienneQuantite = this.recolte.getQuantiteTotale();
            if (ancienneQuantite == null) {
                ancienneQuantite = 0.0;
            }
            this.recolte.setQuantiteTotale(ancienneQuantite + this.quantiteParArbre);
        }
    }

  
}
