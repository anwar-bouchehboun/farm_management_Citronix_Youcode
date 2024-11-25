package com.projet.citronix.controller;

import com.projet.citronix.dto.VenteDto;
import com.projet.citronix.dto.response.VenteData;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.interfaces.VenteInterface;
import com.projet.citronix.utilitaire.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
public class VenteController {

    private final VenteInterface venteInterface;

    @PostMapping
    public ResponseEntity<VenteDto> creerVente(@Valid @RequestBody VenteDto venteDto) {
        log.info("Création d'une nouvelle vente");
        return ResponseEntity.ok(venteInterface.creerVente(venteDto));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<VenteDto> modifierVente(@PathVariable Long id, @Valid @RequestBody VenteDto venteDto) {
        log.info("Modification de la vente avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID de la vente ne peut pas être nul");
        }
        return ResponseEntity.ok(venteInterface.modifierVente(id, venteDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenteData> getVenteById(@PathVariable Long id) {
        log.info("Récupération de la vente avec l'ID: {}", id);
        return venteInterface.getVentetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<VenteData>> getAllVentes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Récupération de toutes les ventes - Page: {}, Taille: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(venteInterface.getAllVentes(pageable));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String,String>> supprimerVente(@PathVariable Long id) {
        log.info("Suppression de la vente avec l'ID: {}", id);
        venteInterface.supprimerVente(id);
        return ResponseEntity.ok(ResponseMessage.deleteSuccess("La vente", id));
    }
}
