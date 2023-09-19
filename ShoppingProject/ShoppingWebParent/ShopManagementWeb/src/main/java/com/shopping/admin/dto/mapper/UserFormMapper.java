package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.UserDTO;
import com.shopping.admin.dto.UserFormDTO;
import com.shopping.admin.dto.mapper.decorator.UserFormMapperDecorator;
import com.shopping.common.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
@DecoratedWith(UserFormMapperDecorator.class)
public interface UserFormMapper {

    UserFormMapper INSTANCE = Mappers.getMapper(UserFormMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "photos", source = "photos")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "password", ignore = true)
    UserFormDTO userToUserFormDTO(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "photos", source = "photos")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "roles", source = "roles")
    User userFormDTOToUser(UserFormDTO userDTO);
}
