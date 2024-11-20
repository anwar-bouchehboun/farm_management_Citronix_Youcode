package com.projet.citronix.controller;


import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.response.ChampData;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.impl.ChampService;
import com.projet.citronix.utilitaire.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/champ")
@RequiredArgsConstructor
public class ChampController {

private final ChampService champService;


    @GetMapping
    public ResponseEntity<List<ChampData>> getAllFerme(){
        log.info("Récupération de tous les champ");
        return ResponseEntity.ok(champService.getAllChamps());


    }
    @PostMapping
    public ResponseEntity<ChampDto> creer(@Valid @RequestBody ChampDto champDto) {
        log.info("Création d'un nouveau Champ");
        if (champDto == null) {
            throw new ValidationException("Les données du champ ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(champService.creerChamp(champDto));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<ChampDto> modifier(@PathVariable Long id, @Valid @RequestBody ChampDto champDto){
        log.info("Mise à jour du champ avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du champ ne peut pas être nul");
        }
        if (champDto == null) {
            throw new ValidationException("Les données du champ ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(champService.modifierChamp(id,champDto));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> supprimer(@PathVariable Long id) {
        log.info("Suppression du chmap avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du champ ne peut pas être nul");
        }
        champService.supprimerChamp(id);

        return ResponseEntity.ok(ResponseMessage.deleteSuccess("La champ", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChampData> getById(@PathVariable Long id) {
        log.info("Récupération du champ avec l'ID: {}", id);
        return champService.getChampById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
