package com.shopping.admin.dto.mapper.decorator;

import com.shopping.admin.dto.UserFormDTO;
import com.shopping.admin.dto.mapper.UserFormMapper;
import com.shopping.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserFormMapperDecorator implements UserFormMapper {
    @Autowired
    @Qualifier("delegate")
    private UserFormMapper delegate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User userFormDTOToUser(UserFormDTO userFormDTO) {
        User user = delegate.userFormDTOToUser(userFormDTO);
        user.setPassword(passwordEncoder.encode(userFormDTO.getPassword()));
        return user;
    }

}
