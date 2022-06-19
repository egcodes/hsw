package com.hackerswork.hsw.mapper;

import com.hackerswork.hsw.dto.ConnectionDTO;
import com.hackerswork.hsw.persistence.entity.Connection;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConnectionMapper {

    ConnectionDTO toDTO(Connection entity);

    List<ConnectionDTO> toDTOs(List<Connection> entities);

    Connection toEntity(ConnectionDTO dto);

}