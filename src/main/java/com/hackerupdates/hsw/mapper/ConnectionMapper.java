package com.hackerupdates.hsw.mapper;

import com.hackerupdates.hsw.dto.ConnectionDTO;
import com.hackerupdates.hsw.persistence.entity.Connection;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConnectionMapper {

    ConnectionDTO toDTO(Connection entity);

    List<ConnectionDTO> toDTOs(List<Connection> entities);

    Connection toEntity(ConnectionDTO dto);

}