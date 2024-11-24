package com.projet.citronix.service.impl;

import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.dto.response.RecolteData;
import com.projet.citronix.entity.Recolte;
import com.projet.citronix.enums.Saison;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.RecolteMapper;
import com.projet.citronix.repository.RecolteRepository;
import com.projet.citronix.repository.DetailRecolteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecolteServiceTest {

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private DetailRecolteRepository detailRecolteRepository;

    @InjectMocks
    private RecolteService recolteService;

    private RecolteDto recolteDto;
    private Recolte recolte;

    @Before
    public void setUp() {
        recolteDto = RecolteDto.builder()
                .dateRecolte(LocalDate.of(2025, 4, 15))
                .saison(Saison.PRINTEMPS)
                .quantiteTotale(0.0)
                .build();

        recolte = RecolteMapper.INSTANCE.RecolteDTOToEntity(recolteDto);
        recolte.setId(1L);
    }

    @Test
    public void creerRecolte_Success() {
        when(recolteRepository.save(any(Recolte.class))).thenReturn(recolte);

        RecolteDto result = recolteService.creerRecolet(recolteDto);

        assertNotNull(result);
        assertEquals(recolteDto.getDateRecolte(), result.getDateRecolte());
        assertEquals(recolteDto.getSaison(), result.getSaison());
        verify(recolteRepository).save(any(Recolte.class));
    }

    @Test(expected = ValidationException.class)
    public void creerRecolte_InvalidSeason() {
        recolteDto.setDateRecolte(LocalDate.of(2024, 7, 15));
        recolteDto.setSaison(Saison.PRINTEMPS);
        
        recolteService.creerRecolet(recolteDto);
    }

    @Test
    public void modifierRecolte_Success() {
        when(recolteRepository.existsById(1L)).thenReturn(true);
        when(recolteRepository.save(any(Recolte.class))).thenReturn(recolte);

        RecolteDto result = recolteService.modifierRecolet(1L, recolteDto);

        assertNotNull(result);
        assertEquals(recolteDto.getDateRecolte(), result.getDateRecolte());
        assertEquals(recolteDto.getSaison(), result.getSaison());
        verify(recolteRepository).save(any(Recolte.class));
    }

    @Test(expected = ValidationException.class)
    public void modifierRecolte_NonExistant() {
        when(recolteRepository.existsById(999L)).thenReturn(false);
        recolteService.modifierRecolet(999L, recolteDto);
    }



    @Test
    public void supprimerRecolte_Success() {
        when(recolteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(recolteRepository).deleteById(1L);

        recolteService.supprimerRecolet(1L);

        verify(recolteRepository).deleteById(1L);
    }

    @Test(expected = ValidationException.class)
    public void supprimerRecolte_NonExistant() {
        when(recolteRepository.existsById(999L)).thenReturn(false);
        recolteService.supprimerRecolet(999L);
    }
}
