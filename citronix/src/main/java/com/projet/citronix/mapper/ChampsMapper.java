package com.projet.citronix.mapper;

import com.projet.citronix.dto.ChampDto;
import com.projet.citronix.entity.Champ;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChampsMapper {

    ChampsMapper INSTANCE = Mappers.getMapper(ChampsMapper.class);

    ChampDto champsToDTO(Champ champs);

    Champ champsDTOToEntity(ChampDto champsDTO);
}
