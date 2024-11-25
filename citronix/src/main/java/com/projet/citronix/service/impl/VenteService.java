package com.projet.citronix.service.impl;

import com.projet.citronix.dto.VenteDto;
import com.projet.citronix.dto.response.VenteData;
import com.projet.citronix.entity.Recolte;
import com.projet.citronix.entity.Vente;
import com.projet.citronix.exception.NotFoundExceptionHndler;
import com.projet.citronix.exception.ValidationException;
import com.projet.citronix.mapper.VenteMapper;
import com.projet.citronix.repository.RecolteRepository;
import com.projet.citronix.repository.VenteRepository;
import com.projet.citronix.service.interfaces.VenteInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class VenteService  implements VenteInterface {
    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper=VenteMapper.INSTANCE;
    private final RecolteRepository recolteRepository;

    @Override
    public VenteDto creerVente(VenteDto venteDto) {
        Recolte recolte = recolteRepository.findById(venteDto.getRecolteid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Récolte non trouvée avec l'ID: " + venteDto.getRecolteid()));
           //vlaidation Quantite
        validateQuantiteVente(recolte, venteDto.getQuantite());
        
        Vente vente = venteMapper.venteDtoToEntity(venteDto);
        vente.setRecolte(recolte);
        vente.setRevenu(vente.calculerRevenu());
        Vente savedVente = venteRepository.save(vente);
        //update QuantiteTotalRecolet
        updateQuantiteTotalRecolteVente(recolte, savedVente);
        return venteMapper.venteDto(savedVente);
    }

    @Override
    public VenteDto modifierVente(Long id, VenteDto venteDto) {
        Vente existingVente = venteRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Vente non trouvée avec l'ID: " + id));

        Recolte recolte = recolteRepository.findById(venteDto.getRecolteid())
                .orElseThrow(() -> new NotFoundExceptionHndler("Récolte non trouvée avec l'ID: " + venteDto.getRecolteid()));

        validateQuantiteVente(recolte, venteDto.getQuantite());

        existingVente.setDateVente(venteDto.getDateVente());
        existingVente.setPrixUnitaire(venteDto.getPrixUnitaire());
        existingVente.setQuantite(venteDto.getQuantite());
        existingVente.setClient(venteDto.getClient());
        existingVente.setRecolte(recolte);
        existingVente.setRevenu(existingVente.calculerRevenu());
        Vente updatedVente = venteRepository.save(existingVente);
        updateQuantiteTotalRecolteVente(recolte, updatedVente);
        return venteMapper.venteDto(updatedVente);
    }

    @Override
    public Optional<VenteData> getVentetById(Long id) {
        return Optional.of(venteRepository.findById(id)
                .map(venteMapper::ventData)
                .orElseThrow(() -> new NotFoundExceptionHndler("Vente non trouvée avec l'ID: " + id)));    }

    @Override
    public List<VenteData> getAllVentes(Pageable pageable) {
        return venteRepository.findAll(pageable)
                .map(venteMapper::ventData)
                .getContent();
    }

    @Override
    public Page<VenteData> getAllVent(Pageable pageable) {
        return venteRepository.findAll(pageable)
                .map(venteMapper::ventData);
    }

    @Override
    public void supprimerVente(Long id) {
     if(!venteRepository.existsById(id)){
         throw new NotFoundExceptionHndler("Vente non trouvée avec l'ID:" + id);
     }
        venteRepository.deleteById(id);
    }





    public void updateQuantiteTotalRecolteVente(Recolte recolte, Vente vente) {
        Double quantiteRestante = recolte.getQuantiteTotale() - vente.getQuantite();
        recolte.setQuantiteTotale(quantiteRestante);
        recolteRepository.save(recolte);
    }









//validation
    private void validateQuantiteVente(Recolte recolte, Double quantiteVente) {
        if (recolte.getQuantiteTotale() == null || recolte.getQuantiteTotale() <= 0) {
            throw new ValidationException("La récolte n'a pas de quantité totale valide");
        }

        Double quantiteTotaleVendue = venteRepository.sumQuantiteByRecolteId(recolte.getId());
        if (quantiteTotaleVendue == null) {
            quantiteTotaleVendue = 0.0;
        }
        
     //   Double stockDisponible =  - quantiteTotaleVendue;
        
        if (quantiteVente > recolte.getQuantiteTotale()) {
            throw new ValidationException(String.format(
                "Quantité invalide. Stock disponible: %.2f kg, Quantité demandée: %.2f kg, Quantité totale récolte: %.2f kg",
                    recolte.getQuantiteTotale(),
                quantiteVente,
                recolte.getQuantiteTotale()
            ));
        }
    }


}
