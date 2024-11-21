package com.projet.citronix.controller;

import com.projet.citronix.dto.DetailsRecolteDto;
import com.projet.citronix.dto.response.DetaailRecolteData;
import com.projet.citronix.service.DetailRecolteInterface;
import com.projet.citronix.utilitaire.ResponseMessage;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detailsrecolte")
@RequiredArgsConstructor
public class DetailRecolteController {

    private final DetailRecolteInterface detailRecolteService;

    @PostMapping
    public ResponseEntity<DetailsRecolteDto> creerDetailRecolte(@RequestBody DetailsRecolteDto detailsRecolteDto) {
        return ResponseEntity.ok(detailRecolteService.creerDetailsRecolte(detailsRecolteDto));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<DetailsRecolteDto> modifierDetailRecolte(
            @PathVariable Long id,
            @RequestBody DetailsRecolteDto detailsRecolteDto) {
        return ResponseEntity.ok(detailRecolteService.modifierDetailsRecolte(id, detailsRecolteDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetaailRecolteData> getDetailRecolte(@PathVariable Long id) {
        return detailRecolteService.getRecoletById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DetaailRecolteData>> getAllDetailsRecoltes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(detailRecolteService.getAllDetailsRecoltes(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> supprimerDetailRecolte(@PathVariable Long id) {
        detailRecolteService.supprimerDetailsRecolte(id);
        return ResponseEntity.ok(ResponseMessage.deleteSuccess("La DetailRecolte", id));
    }
}
