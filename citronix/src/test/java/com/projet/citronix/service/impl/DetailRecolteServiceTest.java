package com.projet.citronix.service.impl;

import com.projet.citronix.dto.DetailsRecolteDto;
import com.projet.citronix.entity.Arbre;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.DetailRecolte;
import com.projet.citronix.entity.Recolte;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.repository.ArbreRepository;
import com.projet.citronix.repository.DetailRecolteRepository;
import com.projet.citronix.repository.RecolteRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailRecolteServiceTest {

    @Mock
    private DetailRecolteRepository detailRecolteRepository;

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private ArbreRepository arbreRepository;

    @InjectMocks
    private DetailRecolteService detailRecolteService;

    private DetailsRecolteDto detailsRecolteDto;
    private Recolte recolte;
    private Arbre arbre;
    private DetailRecolte detailsRecolte;

    @Before
    public void setUp() {
        // Préparer les données de test
        recolte = Recolte.builder()
                .id(1L)
                .dateRecolte(LocalDate.of(2024, 4, 15))
                .quantiteTotale(0.0)
                .build();

        Champ champ = Champ.builder()
                .id(1L)
                .nom("Champ Test")
                .superficie(1.0)
                .build();

        arbre = Arbre.builder()
                .id(1L)
                .datePlantation(LocalDate.of(2020, 4, 15))
                .champ(champ)
                .build();

        detailsRecolte = DetailRecolte.builder()
                .id(1L)
                .recolte(recolte)
                .arbre(arbre)
                .quantiteParArbre(2.0)
                .build();

        detailsRecolteDto = DetailsRecolteDto.builder()
                .recolteid(1L)
                .arbreid(1L)
                .quantiteParArbre(2.0)
                .build();

        // Configuration des mocks
        when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        when(detailRecolteRepository.save(any(DetailRecolte.class))).thenReturn(detailsRecolte);
        when(detailRecolteRepository.existsByArbreAndSaison(anyLong(), any(), anyInt())).thenReturn(false);
    }

    @Test
    public void creerDetailsRecolte_Success() {
        DetailsRecolteDto result = detailRecolteService.creerDetailsRecolte(detailsRecolteDto);

        assertNotNull(result);
        assertEquals(detailsRecolteDto.getQuantiteParArbre(), result.getQuantiteParArbre());
        assertEquals(detailsRecolteDto.getArbreid(), result.getArbreid());
    }



}
