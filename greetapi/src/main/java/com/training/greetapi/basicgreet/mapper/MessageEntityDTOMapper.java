package com.training.greetapi.basicgreet.mapper;

import com.training.greetapi.basicgreet.dto.MessageRequestDTO;
import com.training.greetapi.basicgreet.dto.MessageResponseDTO;
import com.training.greetapi.basicgreet.entity.UserEntity;
import com.training.greetapi.basicgreet.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageEntityDTOMapper {


    MessageEntityDTOMapper INSTANCE = Mappers.getMapper(MessageEntityDTOMapper.class);


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "user", qualifiedByName = "userEntityToCreatorName", target = "creatorName"),
            @Mapping(source = "message", target = "message"),
            @Mapping(source = "description", target = "description")
    })
    MessageResponseDTO entityToDTO(MessageEntity messageEntity);

    @Named("userEntityToCreatorName")
    default String userEntityToCreatorName(UserEntity user) {
        return user != null ? user.getUserName() : null;
    }

    @Mappings({
            @Mapping(source = "greetingMessage", target = "message"),
            @Mapping(source = "description", target = "description")
    })
    MessageEntity DTOtoEntity(MessageRequestDTO messageRequestDTO);


}
