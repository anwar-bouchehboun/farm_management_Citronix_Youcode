package com.projet.citronix.mapper;

import com.projet.citronix.dto.ArbreDto;
import com.projet.citronix.dto.response.ArbreData;
import com.projet.citronix.entity.Arbre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ArbreMapper {


    ArbreMapper INSTANCE = Mappers.getMapper(ArbreMapper.class);
    @Mapping(target = "champid", source = "champ.id")

    ArbreDto ArbreToDTO(Arbre arbre);
    @Mapping(source = "champid", target = "champ.id")
    Arbre arbreDTOToEntity(ArbreDto arbreDto);

   ArbreData arbreTOdata(Arbre arbre);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "datePlantation", source = "datePlantation")
    @Mapping(target = "nomChamp", source = "champ.nom")
    @Mapping(target = "superficie", source = "champ.superficie")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "productiviteParSaison", expression = "java(arbre.calculerProductiviteAnnuelle())")
    @Mapping(target = "categorieAge", expression = "java(determinerCategorieAge(arbre.getAge()))")
    @Mapping(target = "quantiteParArbre", expression = "java(arbre.getDetails().isEmpty() ? 0 : arbre.getDetails().get(0).getQuantiteParArbre())")
    ArbreData arbreToData(Arbre arbre);

    default String determinerCategorieAge(int age) {
        if (age < 3) return "Arbre jeune";
        else if (age >= 3 && age <= 10) return "Arbre mature";
        else return "Arbre vieux";
    }
}
