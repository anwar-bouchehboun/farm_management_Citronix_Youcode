package com.projet.citronix.controller;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.dto.response.ChampData;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.impl.ArbreService;
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
@RequestMapping("/api/arbres")
@RequiredArgsConstructor
public class ArbreController {

    private final ArbreService arbreService;



    @GetMapping
    public ResponseEntity<List<ArbreData>> getAllArbre(){
        log.info("Récupération de tous les Arbre");
        return ResponseEntity.ok(arbreService.getAllArbres());

    }

    @PostMapping
    public ResponseEntity<ArbreDto> creer(@Valid @RequestBody ArbreDto arbreDto) {
        log.info("Création d'un nouveau Arbre");
        if (arbreDto == null) {
            throw new ValidationException("Les données du champ ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(arbreService.creerArbre(arbreDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbreData> getById(@PathVariable Long id) {
        log.info("Récupération du Arbre avec l'ID: {}", id);
        return arbreService.getArbreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<ArbreDto> modifier(@PathVariable Long id, @Valid @RequestBody ArbreDto arbreDto){
        log.info("Mise à jour du champ avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du champ ne peut pas être nul");
        }
        if (arbreDto == null) {
            throw new ValidationException("Les données du champ ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(arbreService.modifierArbre(id,arbreDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> supprimer(@PathVariable Long id) {
        log.info("Suppression du chmap avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du Arbre ne peut pas être nul");
        }
        arbreService.supprimerArbre(id);

        return ResponseEntity.ok(ResponseMessage.deleteSuccess("La Arbre", id));
    }


}
