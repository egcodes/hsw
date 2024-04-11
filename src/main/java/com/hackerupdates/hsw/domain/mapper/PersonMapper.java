package com.hackerupdates.hsw.domain.mapper;

import com.hackerupdates.hsw.domain.dto.PersonDTO;
import com.hackerupdates.hsw.domain.entity.Person;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO toDTO(Person entity);

    List<PersonDTO> toDTOs(List<Person> entities);

    Person toEntity(PersonDTO dto);

}