package com.projet.citronix.mapper;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.entity.Champ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChampsMapper {

    ChampsMapper INSTANCE = Mappers.getMapper(ChampsMapper.class);
    
    @Mapping(target = "fermeid", source = "ferme.id")
    ChampDto champsToDTO(Champ champs);

    @Mapping(source = "fermeid", target = "ferme.id")
    Champ champsDTOToEntity(ChampDto champsDTO);
}
