package com.lineup.mapper;

import com.lineup.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toUserEntity(SignUpRequestDTO dto);

    UserResponseDTO toUserResponse(UserEntity user);

}
