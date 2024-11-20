package com.projet.citronix.controller;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.impl.ArbreService;
import com.projet.citronix.utilitaire.ArbreSearchCriteria;
import com.projet.citronix.utilitaire.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<ArbreData>> getAllArbre(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Récupération de tous les Arbre");
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(arbreService.getAllArbres(PageRequest.of(page, size, Sort.by(sortDirection, sort))));
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

    @GetMapping("/paginated")
    public ResponseEntity<Page<ArbreData>> getAllArbresPaginated(
        @RequestParam(required = false) Long champId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "3") Integer size
    ) {
        ArbreSearchCriteria criteria = ArbreSearchCriteria.builder()
            .champId(champId)
            .page(page)
            .size(size)
            .build();
        
        return ResponseEntity.ok(arbreService.getAllArbresWithPagination(criteria));
    }

}
