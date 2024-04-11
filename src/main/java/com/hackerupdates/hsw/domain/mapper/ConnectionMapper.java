package com.hackerupdates.hsw.domain.mapper;

import com.hackerupdates.hsw.domain.dto.ConnectionDTO;
import com.hackerupdates.hsw.domain.entity.Connection;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConnectionMapper {

    ConnectionDTO toDTO(Connection entity);

    List<ConnectionDTO> toDTOs(List<Connection> entities);

    Connection toEntity(ConnectionDTO dto);

}