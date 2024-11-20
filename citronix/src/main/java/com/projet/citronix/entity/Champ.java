package com.projet.citronix.entity;
import com.projet.citronix.exception.ValidationException;
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
    @Column(nullable = false)
    private Double superficie;

    @ManyToOne
    @JoinColumn(name = "ferme_id", nullable = false)
    private Ferme ferme;

    @OneToMany(mappedBy = "champ",fetch = FetchType.EAGER)
    @Size(message = "Le nombre d'arbres doit respecter la densité maximale de 100 arbres par hectare")
    private List<Arbre> arbres;

    public boolean isSuperficieValid() {
        return superficie >= 0.1 && superficie <= (ferme.getSuperficie() * 0.5);
    }



    
    public boolean isValid() {
        return isSuperficieValid() && 
               (ferme == null || ferme.getChamps() == null || ferme.getChamps().size() <10);
    }

    @AssertTrue(message = "La densité d'arbres dépasse la limite maximale de 100 arbres par hectare")
    public boolean isDensiteArbresValid() {
        if (arbres == null || superficie == null) {
            return true;
        }
        double maxArbres = superficie * 100;
        return arbres.size() <= maxArbres;
    }
}
