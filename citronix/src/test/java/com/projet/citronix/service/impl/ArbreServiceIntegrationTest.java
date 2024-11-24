package com.projet.citronix.service.impl;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.repository.ArbreRepository;
import com.projet.citronix.repository.ChampRepository;
import com.projet.citronix.repository.FermeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ArbreServiceIntegrationTest {

    @Autowired
    private ArbreService arbreService;

    @Autowired
    private ArbreRepository arbreRepository;

    @Autowired
    private ChampRepository champRepository;

    @Autowired
    private FermeRepository fermeRepository;

    private Ferme ferme;
    private Champ champ;
    private ArbreDto arbreDto;

    @Before
    public void setUp() {
        ferme = Ferme.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(10.0)
                .dateCreation(LocalDate.now())
                .champs(new ArrayList<>())
                .build();
        ferme = fermeRepository.save(ferme);


        champ = Champ.builder()
                .nom("Champ Test")
                .superficie(1.0)
                .ferme(ferme)
                .arbres(new ArrayList<>())
                .build();
        champ = champRepository.save(champ);

        
        arbreDto = ArbreDto.builder()
                .datePlantation(LocalDate.parse("2025-05-20"))
                .champid(champ.getId())
                .build();
    }

    @Test
    public void creerArbre_Success() {
        ArbreDto result = arbreService.creerArbre(arbreDto);

        assertNotNull(result);
        assertEquals(arbreDto.getDatePlantation(), result.getDatePlantation());
        assertEquals(arbreDto.getChampid(), result.getChampid());
    }

    @Test
    public void modifierArbre_Success() {
        ArbreDto savedArbre = arbreService.creerArbre(arbreDto);
        Long arbreId = arbreRepository.findAll().get(0).getId();
        
        ArbreDto updateDto = ArbreDto.builder()
                .datePlantation(LocalDate.parse("2025-05-20"))
                .champid(champ.getId())
                .build();

        ArbreDto result = arbreService.modifierArbre(arbreId, updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getDatePlantation(), result.getDatePlantation());
        assertEquals(updateDto.getChampid(), result.getChampid());
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void supprimerArbre_Success() {
        ArbreDto savedArbre = arbreService.creerArbre(arbreDto);
        Long arbreId = arbreRepository.findAll().get(0).getId();

        arbreService.supprimerArbre(arbreId);
        arbreService.getArbreById(arbreId);
    }
}
