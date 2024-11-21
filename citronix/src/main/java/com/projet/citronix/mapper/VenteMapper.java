package com.projet.citronix.mapper;

import com.projet.citronix.dto.VenteDto;
import com.projet.citronix.dto.response.VenteData;
import com.projet.citronix.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface VenteMapper {

    VenteMapper INSTANCE= Mappers.getMapper(VenteMapper.class);

    @Mapping(target = "recolteid", source = "recolte.id")

    VenteDto venteDto(Vente vente);

    @Mapping(source = "recolteid", target = "recolte.id")
    Vente venteDtoToEntity(VenteDto venteDto);

    @Mapping(target = "recoltedate",source = "recolte.dateRecolte")
    @Mapping(target = "saison",source = "recolte.saison")
    VenteData ventData(Vente vente);


}
