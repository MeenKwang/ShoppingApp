package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.RoleDto;
import com.shopping.common.entity.Role;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    RoleDto roleToRoleDto(Role role);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    Role roleDtoToRole(RoleDto dto);
}
