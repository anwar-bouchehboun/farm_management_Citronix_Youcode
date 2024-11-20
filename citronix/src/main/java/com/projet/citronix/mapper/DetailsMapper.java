package com.projet.citronix.mapper;

import com.projet.citronix.dto.DetailsRecolteDto;
import com.projet.citronix.dto.response.DetaailRecolteData;
import com.projet.citronix.entity.DetailRecolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface DetailsMapper {

    DetailsMapper INSTANCE = Mappers.getMapper(DetailsMapper.class);

    @Mapping(target = "recolteid", source = "recolte.id")
    @Mapping(target = "arbreid", source = "arbre.id")
    DetailsRecolteDto toDto(DetailRecolte detailRecolte);

    @Mapping(source = "recolteid", target = "recolte.id")
    @Mapping(source = "arbreid", target = "arbre.id")
    DetailRecolte toEntity(DetailsRecolteDto detailsRecolteDto);

    DetaailRecolteData toDtoData(DetailRecolte detailRecolte);
}
