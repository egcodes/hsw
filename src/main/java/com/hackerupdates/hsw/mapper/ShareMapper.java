package com.hackerupdates.hsw.mapper;

import com.hackerupdates.hsw.dto.ShareDTO;
import com.hackerupdates.hsw.persistence.entity.Share;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShareMapper {

    ShareDTO toDTO(Share entity);

    List<ShareDTO> toDTOs(List<Share> entities);

    Share toEntity(ShareDTO dto);

}