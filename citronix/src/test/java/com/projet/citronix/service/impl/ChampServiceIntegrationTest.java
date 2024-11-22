package com.projet.citronix.service.impl;
import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
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
public class ChampServiceIntegrationTest {

    @Autowired
    private ChampService champService;

    @Autowired
    private ChampRepository champRepository;

    @Autowired
    private FermeRepository fermeRepository;

    private Ferme ferme;
    private ChampDto champDto;

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

        champDto = ChampDto.builder()
                .nom("Champ Test")
                .superficie(0.2)
                .fermeid(ferme.getId())
                .build();
    }

    @Test
    public void creerChamp_Success() {


        ChampDto result = champService.creerChamp(champDto);

        assertNotNull(result);
        assertEquals(champDto.getNom(), result.getNom());
        assertEquals(champDto.getSuperficie(), result.getSuperficie());
        assertEquals(champDto.getFermeid(), result.getFermeid());
    }



    @Test
    public void creerChamp_NombreMaximumChamps() {
        for (int i = 0; i < 10; i++) {
            ChampDto newChamp = ChampDto.builder()
                    .nom("Champ " + i)
                    .superficie(0.5)
                    .fermeid(ferme.getId())
                    .build();
            champService.creerChamp(newChamp);
        }
        
        ChampDto extraChamp = ChampDto.builder()
                .nom("Champ Extra")
                .superficie(0.5)
                .fermeid(ferme.getId())
                .build();
        champService.creerChamp(extraChamp);
    }

    @Test
    public void creerChamp_SuperficieTotaleDepassee() {
        // Créer un premier champ avec 40% de la superficie
        ChampDto premierChamp = ChampDto.builder()
                .nom("Premier Champ")
                .superficie(4.0)
                .fermeid(ferme.getId())
                .build();
        champService.creerChamp(premierChamp);

        ChampDto deuxiemeChamp = ChampDto.builder()
                .nom("Deuxième Champ")
                .superficie(2.0)
                .fermeid(ferme.getId())
                .build();
        champService.creerChamp(deuxiemeChamp);
    }

    @Test
    public void modifierChamp_Success() {
        ChampDto savedChamp = champService.creerChamp(champDto);
        Long champId = champRepository.findByNom(champDto.getNom()).get().getId();

        ChampDto updateDto = ChampDto.builder()
                .nom("Champ Modifié")
                .superficie(3.0)
                .fermeid(ferme.getId())
                .build();

        ChampDto result = champService.modifierChamp(champId, updateDto);

        assertNotNull(result);
        assertEquals("Champ Modifié", result.getNom());
        assertEquals(Double.valueOf(3.0), result.getSuperficie());
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void modifierChamp_NonExistant() {
        ChampDto updateDto = ChampDto.builder()
                .nom("Champ Modifié")
                .superficie(3.0)
                .fermeid(ferme.getId())
                .build();

        champService.modifierChamp(999L, updateDto);
    }

    @Test
    public void supprimerChamp_Success() {
        ChampDto savedChamp = champService.creerChamp(champDto);
        Long champId = champRepository.findByNom(champDto.getNom()).get().getId();

        champService.supprimerChamp(champId);
        
        assertFalse(champRepository.existsById(champId));
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void supprimerChamp_NonExistant() {
        champService.supprimerChamp(999L);
    }
}