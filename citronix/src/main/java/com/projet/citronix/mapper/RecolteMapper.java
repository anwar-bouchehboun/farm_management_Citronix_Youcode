package com.projet.citronix.mapper;


import com.projet.citronix.dto.RecolteDto;
import com.projet.citronix.dto.response.FermeData;
import com.projet.citronix.dto.response.RecolteData;
import com.projet.citronix.entity.Ferme;
import com.projet.citronix.entity.Recolte;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RecolteMapper {

    RecolteMapper INSTANCE= Mappers.getMapper(RecolteMapper.class);


    RecolteDto recolteDto(Recolte recolte);

    Recolte RecolteDTOToEntity(RecolteDto recolteDto);
    RecolteData toDtoData(Recolte recolte);
}
