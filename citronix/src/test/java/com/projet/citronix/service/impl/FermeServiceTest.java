package com.projet.citronix.service.impl;

import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.dto.response.FermeData;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.FermeMapper;
import com.projet.citronix.repository.FermeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FermeServiceTest {

    @Mock
    private FermeRepository fermeRepository;



    @InjectMocks
    private FermeService fermeService;

    private FermeDto fermeDto;
    private Ferme ferme;
    private FermeData fermeData;

    @Before
    public void setUp() {
        fermeDto = FermeDto.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();

        ferme = Ferme.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();

        fermeData = FermeData.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(1.0)
                .dateCreation(LocalDate.now())
                .build();
    }

    @Test
    public void creerFerme_Success() {

        FermeDto result = fermeService.creerFerme(fermeDto);

        assertNotNull(result);
        assertEquals(fermeDto.getNom(), result.getNom());
        verify(fermeRepository).save(any(Ferme.class));
    }

    @Test(expected = ValidationException.class)
    public void creerFerme_InvalidSuperficie() {
        fermeDto.setSuperficie(0.1);
        fermeService.creerFerme(fermeDto);
    }

    @Test
    public void modifierFerme_Success() {
        when(fermeRepository.findById(anyLong())).thenReturn(Optional.of(ferme));
        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);

        FermeDto result = fermeService.modifierFerme(1L, fermeDto);

        assertNotNull(result);
        assertEquals(fermeDto.getNom(), result.getNom());
        verify(fermeRepository).save(any(Ferme.class));
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void modifierFerme_NonExistant() {
        when(fermeRepository.findById(anyLong())).thenReturn(Optional.empty());
        fermeService.modifierFerme(999L, fermeDto);
    }

    @Test
    public void getFermeById_Success() {
        Long fermeId = 1L;
        ferme.setId(fermeId);
        when(fermeRepository.findById(fermeId)).thenReturn(Optional.of(ferme));

        Optional<FermeData> result = fermeService.getFermeById(fermeId);

        assertTrue(result.isPresent());
        assertEquals(fermeData.getNom(), result.get().getNom());
        verify(fermeRepository).findById(fermeId);
    }

    @Test
    public void rechercherFermes_Success() {
        when(fermeRepository.rechercherFermes(anyString(), any(), anyString(), anyDouble()))
                .thenReturn(Arrays.asList(ferme));

        List<FermeData> results = fermeService.rechercherFermes(
                "Ferme Test",
                LocalDate.now(),
                "Location Test",
                1.0
        );

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(ferme.getNom(), results.get(0).getNom());
    }
}