package com.projet.citronix.service.impl.testIntegration;

import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.dto.response.FermeData;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.repository.FermeRepository;
import com.projet.citronix.service.impl.FermeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FermeServiceIntegrationTest {

    @Autowired
    private FermeService fermeService;

    @Autowired
    private FermeRepository fermeRepository;

    @Test
    public void creerFerme_Success() {
        FermeDto fermeDto = FermeDto.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();

        FermeDto result = fermeService.creerFerme(fermeDto);

        assertNotNull("Le résultat ne devrait pas être null", result);
        assertEquals("Le nom devrait correspondre", fermeDto.getNom(), result.getNom());
        assertEquals("La localisation devrait correspondre", fermeDto.getLocalisation(), result.getLocalisation());
        assertEquals("La superficie devrait correspondre", fermeDto.getSuperficie(), result.getSuperficie());
    }

    @Test(expected = ValidationException.class)
    public void creerFerme_InvalidSuperficie() {
        FermeDto fermeDto = FermeDto.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(0.1)
                .dateCreation(LocalDate.now())
                .build();

        fermeService.creerFerme(fermeDto);
    }

    @Test
    public void modifierFerme_Success() {
        Ferme ferme = Ferme.builder()
                .nom("Ferme Originale")
                .localisation("Location Originale")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();
        ferme = fermeRepository.save(ferme);

        FermeDto updateDto = FermeDto.builder()
                .nom("Ferme Modifiée")
                .localisation("Location Modifiée")
                .superficie(2.0)
                .dateCreation(LocalDate.now())
                .build();

        FermeDto result = fermeService.modifierFerme(ferme.getId(), updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getNom(), result.getNom());
        assertEquals(updateDto.getLocalisation(), result.getLocalisation());
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void modifierFerme_NonExistant() {
        FermeDto updateDto = FermeDto.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();

        fermeService.modifierFerme(999L, updateDto);
    }

    @Test
    public void getFermeById_Success() {
        Ferme ferme = Ferme.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();
        ferme = fermeRepository.save(ferme);

        Optional<FermeData> result = fermeService.getFermeById(ferme.getId());

        assertTrue(result.isPresent());
        assertEquals(ferme.getNom(), result.get().getNom());
        assertEquals(ferme.getLocalisation(), result.get().getLocalisation());
    }

    @Test
    public void supprimerFerme_Success() {
        Ferme ferme = Ferme.builder()
                .nom("Ferme à Supprimer")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();
        ferme = fermeRepository.save(ferme);
        Long fermeId = ferme.getId();

        fermeService.supprimerFerme(fermeId);

        assertFalse(fermeRepository.existsById(fermeId));
    }

    @Test
    public void rechercherFermes_Success() {
        Ferme ferme = Ferme.builder()
                .nom("Ferme Recherche")
                .localisation("Location Recherche")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();
        fermeRepository.save(ferme);

        List<FermeData> results = fermeService.rechercherFermes(
                "Ferme Recherche",
                LocalDate.now(),
                "Location Recherche",
                1.0
        );

        assertFalse(results.isEmpty());
        assertEquals(ferme.getNom(), results.get(0).getNom());
    }
}