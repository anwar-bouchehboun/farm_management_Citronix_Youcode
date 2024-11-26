package com.projet.citronix.controller;

import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.dto.response.RecolteData;
import com.projet.citronix.enums.Saison;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.impl.RecolteService;
import com.projet.citronix.utilitaire.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/recoltes")
@RequiredArgsConstructor
public class RecolteController {

    private final RecolteService recolteService;


    @PostMapping
    public ResponseEntity<RecolteDto>creerRecolte(@Valid @RequestBody RecolteDto recolteDto){
        log.info("Création d'un nouveau Recolte");
        if (recolteDto == null) {
            throw new ValidationException("Les données du Ferme ne peuvent pas être nulles");
        }

        return ResponseEntity.ok(recolteService.creerRecolet(recolteDto));

    }
    @PutMapping("/{id}/edit")
    public ResponseEntity<RecolteDto> modifier(@PathVariable Long id, @Valid @RequestBody RecolteDto recolteDto){
        log.info("Mise à jour du femre avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du ferme ne peut pas être nul");
        }
        if (recolteDto == null) {
            throw new ValidationException("Les données du ferme ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(recolteService.modifierRecolet(id,recolteDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecolteData> getById(@PathVariable Long id) {
        log.info("Récupération du femre avec l'ID: {}", id);
        return recolteService.getRecoletById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


   @GetMapping
    public ResponseEntity<List<RecolteData>> getAllRecolet(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "3") int size,
           @RequestParam(defaultValue = "id") String sort,
           @RequestParam(defaultValue = "asc") String direction) {
       log.info("Récupération de tous les femres");
       Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
       return ResponseEntity.ok(recolteService.getAllRecolets(PageRequest.of(page, size, Sort.by(sortDirection, sort))));
   }
   @DeleteMapping ("/{id}/delete")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
       log.info("Suppression du femre avec l'ID: {}", id);
       if (id == null) {
           log.info("id du femre  null: {}", id);
           throw new ValidationException("L'ID du ferme ne peut pas être nul");
       }
       recolteService.supprimerRecolet(id);

       return ResponseEntity.ok(ResponseMessage.deleteSuccess("La recolte", id));
   }

  /*  @GetMapping("/femresesion")
    public ResponseEntity<List<RecolteData>> getFemre(
            @RequestParam("saison") Saison saison,
            @RequestParam("Femreid") Long femreId) {
        List<RecolteData> result = recolteService.getAllfemre(saison, femreId);


            return ResponseEntity.ok(result);

    }*/

    @GetMapping("/ferme-data")
    public ResponseEntity<List<RecolteData>> getDataFerme() {
        log.info("Récupération des données de récolte par ferme");
        return ResponseEntity.ok(recolteService.dataFerme());
    }

}
