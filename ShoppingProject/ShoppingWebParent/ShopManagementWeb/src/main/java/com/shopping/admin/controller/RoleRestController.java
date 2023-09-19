package com.shopping.admin.controller;

import com.shopping.admin.dto.RoleDto;
import com.shopping.admin.dto.mapper.RoleMapper;
import com.shopping.admin.repository.RoleRepository;
import com.shopping.admin.service.RoleService;
import com.shopping.common.entity.Role;
import com.shopping.common.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RoleRestController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleRestController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping("roles/getAllRolesForUserForm")
    public ResponseEntity<List<RoleDto>> getAllRolesForUserForm () {
        List<Role> roleList =  roleService.getAllRoles();
        List<RoleDto> roleDtos = roleList.stream().map(roleMapper::roleToRoleDto).collect(Collectors.toList());
        System.out.println(roleDtos);
        return ResponseEntity.ok(roleDtos);
    }

//    @PostMapping()
//    public ResponseEntity<Role> create(@RequestBody @Valid Role role) {
//        Role savedRole = roleRepository.save(role);
//        URI roleURI = URI.create("/users/" + savedRole.getId());
//
//        return ResponseEntity.created(roleURI).body(savedRole);
//    }
}
