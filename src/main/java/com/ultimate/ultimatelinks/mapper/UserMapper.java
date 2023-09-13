package com.ultimate.ultimatelinks.mapper;

import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    ReturnedUserDto toDto(UserEntity userEntity);
}
