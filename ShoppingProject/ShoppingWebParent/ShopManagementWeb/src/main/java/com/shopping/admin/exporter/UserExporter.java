package com.shopping.admin.exporter;

import com.shopping.admin.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api")
public class UserExporter {
    private final UserService userService;

    public UserExporter(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/export/excel")
    public void exportListToExcel(HttpServletResponse response) throws IOException {
        Workbook workbook = userService.exportUserListToExcel();

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");

        // Write Excel file to response output stream
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();
    }
}
