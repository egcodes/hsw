package com.hackerswork.hsw.mapper;

import com.hackerswork.hsw.dto.ShareDTO;
import com.hackerswork.hsw.persistence.entity.Share;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShareMapper {

    ShareDTO toDTO(Share entity);

    List<ShareDTO> toDTOs(List<Share> entities);

    Share toEntity(ShareDTO dto);

}