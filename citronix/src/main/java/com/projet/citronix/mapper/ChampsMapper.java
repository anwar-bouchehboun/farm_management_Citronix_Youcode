package com.projet.citronix.mapper;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.dto.response.ChampData;
import com.projet.citronix.entity.Champ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ChampsMapper {

    ChampsMapper INSTANCE = Mappers.getMapper(ChampsMapper.class);
    
    @Mapping(target = "fermeid", source = "ferme.id")
    ChampDto champsToDTO(Champ champs);

    @Mapping(source = "fermeid", target = "ferme.id")
    Champ champsDTOToEntity(ChampDto champsDTO);

    @Mapping(target = "fermename", source = "ferme.nom")
    @Mapping(target = "localisation", source = "ferme.localisation")
    @Mapping(target = "superficieFerme", source = "ferme.superficie")
    @Mapping(target = "dateCreation", source = "ferme.dateCreation")
    @Mapping(target = "arberDtos", source = "arbres") 
        ChampData champDataDto(Champ champ);
}
