package com.projet.citronix.service.impl;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.repository.ChampRepository;
import com.projet.citronix.repository.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChampServiceTest {

    @Mock
    private ChampRepository champRepository;

    @Mock
    private FermeRepository fermeRepository;



    @InjectMocks
    private ChampService champService;

    private ChampDto champDto;
    private Champ champ;
    private Ferme ferme;

    @BeforeEach
    void setUp() {
        ferme = Ferme.builder()
                .id(1L)
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(10.0)
                .dateCreation(LocalDate.now())
                .champs(new ArrayList<>())
                .build();

        champDto = ChampDto.builder()
                .nom("Champ Test")
                .superficie(2.0)
                .fermeid(1L)
                .build();

        champ = Champ.builder()
                .id(1L)
                .nom("Champ Test")
                .superficie(2.0)
                .ferme(ferme)
                .build();
    
    }

    @Test
    void createChamp_Success() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.of(ferme));
        when(champRepository.save(any(Champ.class))).thenReturn(champ);

        ChampDto result = champService.creerChamp(champDto);

        assertNotNull(result);
        assertEquals("Champ Test", result.getNom());
        assertEquals(2.0, result.getSuperficie());
        assertEquals(1L, result.getFermeid());
        verify(champRepository).save(any(Champ.class));
    }




    @Test
    void getChampById_NotFound() {

        when(champRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundExceptionHndler.class, () ->
            champService.getChampById(1L)
        );
    }

    @Test
    void updateChamp_Success() {

        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        when(fermeRepository.findById(1L)).thenReturn(Optional.of(ferme));
        when(champRepository.save(any(Champ.class))).thenReturn(champ);

        ChampDto result = champService.modifierChamp(1L, champDto);

        assertNotNull(result);
        assertEquals("Champ Test", result.getNom());
        verify(champRepository).save(any(Champ.class));
    }

    @Test
    void deleteChamp_Success() {
        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        doNothing().when(champRepository).delete(any(Champ.class));

        champService.supprimerChamp(1L);

        verify(champRepository).delete(any(Champ.class));
    }


}