package com.shopping.admin.service;

import com.shopping.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;


public interface UserService {
    public List<User> listAll();
    public Page<User> listByPage(int pageNum, String sortField, String sortDir,
                                    String emailSearch, String firstNameSearch, String lastNameSearch,
                                    int pageSize);

    public User save(User user);

    public boolean isEmailUnique(Integer id, String email);
    public void delete(Integer id);

    User getUserById(Integer id);

    public Workbook exportUserListToExcel() throws IOException;
}
