package com.projet.citronix.controller;


import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.dto.response.FermeData;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.impl.FermeService;
import com.projet.citronix.utilitaire.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/ferme")
@RequiredArgsConstructor
public class FermeController {

    private final FermeService fermeService;


    @GetMapping
    public ResponseEntity<List<FermeData>> getAllFerme(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction){
        log.info("Récupération de tous les femres");
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(fermeService.getAllFermes(PageRequest.of(page, size, Sort.by(sortDirection, sort))));


    }
    @PostMapping
    public ResponseEntity<FermeDto> creer(@Valid @RequestBody FermeDto fermeDto) {
        log.info("Création d'un nouveau femre");
        if (fermeDto == null) {
            throw new ValidationException("Les données du Ferme ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(fermeService.creerFerme(fermeDto));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<FermeDto> modifier(@PathVariable Long id, @Valid @RequestBody FermeDto fermeDto){
        log.info("Mise à jour du femre avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du ferme ne peut pas être nul");
        }
        if (fermeDto == null) {
            throw new ValidationException("Les données du ferme ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(fermeService.modifierFerme(id,fermeDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> supprimer(@PathVariable Long id) {
        log.info("Suppression du femre avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du ferme ne peut pas être nul");
        }
        fermeService.supprimerFerme(id);

        return ResponseEntity.ok(ResponseMessage.deleteSuccess("La ferme", id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<FermeData> getById(@PathVariable Long id) {
        log.info("Récupération du femre avec l'ID: {}", id);
        return fermeService.getFermeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rechercheferme")
    public ResponseEntity<List<FermeData>> rechercher(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateCreation,
            @RequestParam(required = false) String localisation,
            @RequestParam(required = false) Double superficie) {
        
        log.info("Recherche de fermes avec les critères: nom={}, dateCreation={}, localisation={}, superficie={}", 
                nom, dateCreation, localisation, superficie);
        
        return ResponseEntity.ok(fermeService.rechercherFermes(nom, dateCreation, localisation, superficie));
    }
}
