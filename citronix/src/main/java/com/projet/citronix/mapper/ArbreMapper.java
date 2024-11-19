package com.projet.citronix.mapper;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.entity.Arbre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ArbreMapper {


    ArbreMapper INSTANCE = Mappers.getMapper(ArbreMapper.class);
    @Mapping(target = "champid", source = "champ.id")

    ArbreDto ArbreToDTO(Arbre arbre);
    @Mapping(source = "champid", target = "champ.id")

    Arbre arbreDTOToEntity(ArbreDto arbreDto);


}
