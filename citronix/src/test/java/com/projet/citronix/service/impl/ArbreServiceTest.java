package com.projet.citronix.service.impl;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.entity.Arbre;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.repository.ArbreRepository;
import com.projet.citronix.repository.ChampRepository;
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
public class ArbreServiceTest {

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampRepository champRepository;

    @InjectMocks
    private ArbreService arbreService;

    private ArbreDto arbreDto;
    private Champ champ;
    private Arbre arbre;

    @Before
    public void setUp() {
        champ = Champ.builder()
                .id(1L)
                .nom("Champ Test")
                .superficie(1.0)
                .build();

        arbreDto = ArbreDto.builder()
                .datePlantation(LocalDate.parse("2025-05-20"))
                .champid(1L)
                .build();

        arbre = Arbre.builder()
                .id(1L)
                .datePlantation(LocalDate.parse("2025-05-20"))
                .champ(champ)
                .build();
    }

    @Test
    public void creerArbre_Success() {
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(champ));
        when(arbreRepository.save(any(Arbre.class))).thenReturn(arbre);

        ArbreDto result = arbreService.creerArbre(arbreDto);

        assertNotNull(result);
        assertEquals(arbreDto.getDatePlantation(), result.getDatePlantation());
        assertEquals(arbreDto.getChampid(), result.getChampid());
        verify(arbreRepository, times(1)).save(any(Arbre.class));
    }

    @Test
    public void modifierArbre_Success() {
        when(arbreRepository.findById(anyLong())).thenReturn(Optional.of(arbre));
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(champ));
        when(arbreRepository.save(any(Arbre.class))).thenReturn(arbre);

        ArbreDto result = arbreService.modifierArbre(1L, arbreDto);

        assertNotNull(result);
        assertEquals(arbreDto.getDatePlantation(), result.getDatePlantation());
        assertEquals(arbreDto.getChampid(), result.getChampid());
        verify(arbreRepository, times(1)).save(any(Arbre.class));
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void supprimerArbre_Success() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        
        arbreService.supprimerArbre(1L);
        
        verify(arbreRepository).delete(arbre);
        
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());
        
        arbreService.getArbreById(1L);
    }

    @Test(expected = NotFoundExceptionHndler.class)
    public void getArbreById_NotFound() {
        when(arbreRepository.findById(anyLong())).thenReturn(Optional.empty());
        arbreService.getArbreById(1L);
    }
}
