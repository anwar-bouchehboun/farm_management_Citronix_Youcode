package com.projet.citronix.mapper;

import com.projet.citronix.dto.FermeDto;
import com.projet.citronix.entity.Ferme;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FermeMapper {
    
    FermeMapper INSTANCE = Mappers.getMapper(FermeMapper.class);

  
    FermeDto toDto(Ferme ferme);


    Ferme toEntity(FermeDto fermeDto);
}
