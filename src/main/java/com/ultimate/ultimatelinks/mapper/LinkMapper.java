package com.ultimate.ultimatelinks.mapper;

import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LinkMapper {
    LinkDtoToUser toDto(LinkEntity linkEntity);

    List<LinkDtoToUser> toDtoList(List<LinkEntity> linkEntityList);
}
