package com.nexossoftware.pruebanexos.mapper;

import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.jpa.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

  @Mapping(source = "product.person.firstName", target = "personFirstName")
  @Mapping(source = "product.person.lastName", target = "personLastName")
  CardDtoOut toDto(CardEntity cardEntity);
}
