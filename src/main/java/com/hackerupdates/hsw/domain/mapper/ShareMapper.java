package com.hackerupdates.hsw.domain.mapper;

import com.hackerupdates.hsw.domain.dto.ShareDTO;
import com.hackerupdates.hsw.domain.entity.Share;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShareMapper {

    ShareDTO toDTO(Share entity);

    List<ShareDTO> toDTOs(List<Share> entities);

    Share toEntity(ShareDTO dto);

}