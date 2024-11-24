package com.projet.citronix.service.impl.testIntegration;

import com.projet.citronix.dto.DetailsRecolteDto;
import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.entity.Arbre;
import com.projet.citronix.entity.Champ;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.enums.Saison;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.repository.*;
import com.projet.citronix.service.impl.DetailRecolteService;
import com.projet.citronix.service.impl.RecolteService;
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
public class DetailRecolteServiceIntegrationTest {

    @Autowired
    private DetailRecolteService detailRecolteService;

    @Autowired
    private RecolteService recolteService;

    @Autowired
    private DetailRecolteRepository detailRecolteRepository;

    @Autowired
    private RecolteRepository recolteRepository;

    @Autowired
    private ArbreRepository arbreRepository;

    @Autowired
    private ChampRepository champRepository;

    @Autowired
    private FermeRepository fermeRepository;

    private Ferme ferme;
    private Champ champ;
    private Arbre arbre;
    private RecolteDto recolteDto;
    private DetailsRecolteDto detailsRecolteDto;

    @Before
    public void setUp() {
        // Créer une ferme
        ferme = Ferme.builder()
                .nom("Ferme Test")
                .localisation("Location Test")
                .superficie(10.0)
                .dateCreation(LocalDate.now())
                .champs(new ArrayList<>())
                .build();
        ferme = fermeRepository.save(ferme);

        // Créer un champ
        champ = Champ.builder()
                .nom("Champ Test")
                .superficie(1.0)
                .ferme(ferme)
                .arbres(new ArrayList<>())
                .build();
        champ = champRepository.save(champ);

        // Créer un arbre
        arbre = Arbre.builder()
                .datePlantation(LocalDate.now().minusYears(5))
                .champ(champ)
                .build();
        arbre = arbreRepository.save(arbre);

        // Créer une récolte
        recolteDto = RecolteDto.builder()
                .dateRecolte(LocalDate.of(2024, 4, 15))
                .saison(Saison.PRINTEMPS)
                .quantiteTotale(0.0)
                .build();
        RecolteDto savedRecolte = recolteService.creerRecolet(recolteDto);

        // Préparer le DTO des détails de récolte
        detailsRecolteDto = DetailsRecolteDto.builder()

                .recolteid(recolteRepository.findAll().get(0).getId())
                .arbreid(arbre.getId())
                .quantiteParArbre(10.0)
                .build();
    }

    @Test
    public void creerDetailsRecolte_Success() {
        DetailsRecolteDto result = detailRecolteService.creerDetailsRecolte(detailsRecolteDto);

        assertNotNull(result);
        assertEquals(detailsRecolteDto.getQuantiteParArbre(), result.getQuantiteParArbre());
        assertEquals(detailsRecolteDto.getArbreid(), result.getArbreid());
    }

    @Test(expected = ValidationException.class)
    public void creerDetailsRecolte_QuantiteExcessive() {
        detailsRecolteDto.setQuantiteParArbre(15.0);
        detailRecolteService.creerDetailsRecolte(detailsRecolteDto);
    }

    @Test
    public void modifierDetailsRecolte_Success() {
        // Créer d'abord le détail
        DetailsRecolteDto savedDetail = detailRecolteService.creerDetailsRecolte(detailsRecolteDto);
        // Récupérer l'ID depuis la base de données
        Long detailId = detailRecolteRepository.findAll().get(0).getId();

        DetailsRecolteDto updateDto = DetailsRecolteDto.builder()
                .recolteid(detailsRecolteDto.getRecolteid())
                .arbreid(detailsRecolteDto.getArbreid())
                .quantiteParArbre(8.0)
                .build();

        DetailsRecolteDto updatedDetail = detailRecolteService.modifierDetailsRecolte(detailId, updateDto);

        assertNotNull(updatedDetail);
        assertEquals(updateDto.getQuantiteParArbre(), updatedDetail.getQuantiteParArbre());
        assertEquals(updateDto.getArbreid(), updatedDetail.getArbreid());
    }
}
