package com.shopping.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.admin.dto.UserDTO;
import com.shopping.admin.dto.UserFormDTO;
import com.shopping.admin.dto.mapper.UserFormMapper;
import com.shopping.admin.dto.mapper.UserMapper;
import com.shopping.admin.service.RoleService;
import com.shopping.admin.service.UserService;
import com.shopping.admin.util.FileUploadUtil;
import com.shopping.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserFormMapper userFormMapper;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper, UserFormMapper userFormMapper,
                              RoleService roleService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userFormMapper = userFormMapper;
        this.roleService = roleService;
    }

    @GetMapping("users")
    public ResponseEntity<Page<UserDTO>> listFirstPage() {
        return listByPage(1, "firstName",
                "asc", "", "", "", 15);
    }

    @GetMapping("users/page/{pageNum}")
    public ResponseEntity<Page<UserDTO>> listByPage(
            @PathVariable("pageNum") int pageNum,
            @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
            @RequestParam("emailSearch") String emailSearch, @RequestParam("firstNameSearch") String firstNameSearch,
            @RequestParam("lastNameSearch") String lastNameSearch, @RequestParam("pageSize") int pageSize
    ) {
        Page<User> userPage = userService.listByPage(pageNum, sortField, sortDir, emailSearch, firstNameSearch, lastNameSearch, pageSize);
        Page<UserDTO> userDTOPage = userPage.map(new Function<User, UserDTO>() {
            @Override
            public UserDTO apply(User user) {
                return userMapper.userToUserDTO(user) ;
            }
        });

        return ResponseEntity.ok(userDTOPage);
    }

    @PostMapping("users/save")
    public ResponseEntity<UserDTO> save(@RequestParam(name = "photo", required = false) MultipartFile photo,
                                              @RequestParam(name = "user") String userForm) throws IOException {
        UserFormDTO userFormDTO = new ObjectMapper().readValue(userForm, UserFormDTO.class);
        User user = userFormMapper.userFormDTOToUser(userFormDTO);
        UserDTO savedUser = new UserDTO();
        //save information of user first, save photo later
        if(photo != null) {
            String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
            user.setPhotos(fileName);
            savedUser = userMapper.userToUserDTO(userService.save(user));
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, photo);
        } else {
            if(userFormDTO.getPhotos().isEmpty()) {
                user.setPhotos(null);
            }
            if(userFormDTO.getId() != null) {
                String userDir = "user-photos/" + userFormDTO.getId();
                FileUploadUtil.removeDir(userDir);
            }
            savedUser = userMapper.userToUserDTO(userService.save(user));
        }

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("users/check_email_unique")
    public ResponseEntity<Boolean> checkDuplicateEmail(@RequestParam(name = "id", required = false) Integer id,
                                                       @RequestParam(name = "email", required = true) String email) {
        return userService.isEmailUnique(id, email) ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }

    @GetMapping("users/edit/{id}")
    public ResponseEntity<UserFormDTO> editUser(@PathVariable(name = "id") Integer id) {
        try {
            UserFormDTO userFormDTO = new UserFormDTO();
            User user =  userService.getUserById(id);
            userFormDTO = userFormMapper.userToUserFormDTO(user);
            return ResponseEntity.ok(userFormDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping("users/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }
}
