package com.hackerswork.hsw.mapper;

import com.hackerswork.hsw.dto.PersonDTO;
import com.hackerswork.hsw.persistence.entity.Person;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO toDTO(Person entity);

    List<PersonDTO> toDTOs(List<Person> entities);

    Person toEntity(PersonDTO dto);

}