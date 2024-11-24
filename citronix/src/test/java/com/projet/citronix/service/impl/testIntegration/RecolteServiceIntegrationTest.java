package com.projet.citronix.service.impl.testIntegration;

import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.enums.Saison;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.repository.RecolteRepository;
import com.projet.citronix.service.impl.RecolteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RecolteServiceIntegrationTest {

    @Autowired
    private RecolteService recolteService;

    @Autowired
    private RecolteRepository recolteRepository;



    private RecolteDto recolteDto;

    @Before
    public void setUp() {
        recolteDto = RecolteDto.builder()
                .dateRecolte(LocalDate.of(2025, 4, 15))
                .saison(Saison.PRINTEMPS)
                .quantiteTotale(0.0)
                .build();
    }

    @Test
    public void creerRecolte_Success() {
        RecolteDto result = recolteService.creerRecolet(recolteDto);

        assertNotNull(result);
        assertEquals(recolteDto.getDateRecolte(), result.getDateRecolte());
        assertEquals(recolteDto.getSaison(), result.getSaison());
    }

    @Test(expected = ValidationException.class)
    public void creerRecolte_InvalidSeason() {
        recolteDto.setDateRecolte(LocalDate.of(2024, 7, 15));
        recolteDto.setSaison(Saison.PRINTEMPS);
        
        recolteService.creerRecolet(recolteDto);
    }

    @Test
    public void modifierRecolte_Success() {
        RecolteDto savedRecolte = recolteService.creerRecolet(recolteDto);
        Long recolteId = recolteRepository.findAll().get(0).getId();

        RecolteDto updateDto = RecolteDto.builder()
                .dateRecolte(LocalDate.of(2024, 5, 15))
                .saison(Saison.PRINTEMPS)
                .quantiteTotale(0.0)
                .build();

        RecolteDto result = recolteService.modifierRecolet(recolteId, updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getDateRecolte(), result.getDateRecolte());
        assertEquals(updateDto.getSaison(), result.getSaison());
    }

    @Test(expected = ValidationException.class)
    public void modifierRecolte_NonExistant() {
        RecolteDto updateDto = RecolteDto.builder()
                .dateRecolte(LocalDate.of(2024, 5, 15))
                .saison(Saison.PRINTEMPS)
                .quantiteTotale(0.0)
                .build();

        recolteService.modifierRecolet(999L, updateDto);
    }




    @Test
    public void supprimerRecolte_Success() {
        RecolteDto savedRecolte = recolteService.creerRecolet(recolteDto);
        Long recolteId = recolteRepository.findAll().get(0).getId();

        recolteService.supprimerRecolet(recolteId);

        assertFalse(recolteRepository.existsById(recolteId));
    }

    @Test(expected = ValidationException.class)
    public void supprimerRecolte_NonExistant() {
        recolteService.supprimerRecolet(999L);
    }
}
