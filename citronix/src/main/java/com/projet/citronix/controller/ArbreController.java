package com.projet.citronix.controller;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.service.impl.ArbreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/arbres")
@RequiredArgsConstructor
public class ArbreController {

    private final ArbreService arbreService;


    @PostMapping
    public ResponseEntity<ArbreDto> creer(@Valid @RequestBody ArbreDto arbreDto) {
        log.info("Création d'un nouveau Arbre");
        if (arbreDto == null) {
            throw new ValidationException("Les données du champ ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(arbreService.creerArbre(arbreDto));
    }

}
