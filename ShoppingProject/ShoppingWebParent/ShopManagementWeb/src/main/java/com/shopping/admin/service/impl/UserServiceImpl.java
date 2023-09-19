package com.shopping.admin.service.impl;

import com.shopping.admin.dto.RoleDto;
import com.shopping.admin.dto.UserDTO;
import com.shopping.admin.dto.mapper.UserMapper;
import com.shopping.admin.repository.UserRepository;
import com.shopping.admin.service.UserService;
import com.shopping.common.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll(Sort.by("firstName").ascending());
    }

    @Override
    public Page<User> listByPage(int pageNum, String sortField, String sortDir,
                                    String emailSearch, String firstNameSearch, String lastNameSearch,
                                    int pageSize) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        return userRepository.findAll(pageable, emailSearch, firstNameSearch, lastNameSearch);
    }

    @Override
    public User save(User userForm) {
        boolean isUpdatingUser = (userForm.getId() != null);
        if(isUpdatingUser) {
            User existingUser = userRepository.findById(userForm.getId()).get();
            if(userForm.getPassword().isEmpty()) {
                userForm.setPassword(existingUser.getPassword());
            }
        }
        return userRepository.save(userForm);
    }
    @Override
    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if(userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if(isCreatingNew) { //create
            if(userByEmail != null) return false;
        } else { //edit
            if(!userByEmail.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Workbook exportUserListToExcel() throws IOException {
        //Create Workbook
        Workbook workbook = new XSSFWorkbook();
        //Create Sheet
        Sheet sheet = workbook.createSheet("Sheet1");
        int rowIndex = 0;
        //Write header
        writeHeader(sheet, rowIndex);
        rowIndex++;
        //Write body
        List<UserDTO> userDTOS = userRepository.findAll().stream()
                .map(user -> userMapper.userToUserDTO(user)).collect(Collectors.toList());
        for(UserDTO userDTO : userDTOS) {
            writeBody(sheet, rowIndex, userDTO, workbook);
            rowIndex++;
        }
        //autosize column
        autosizeColumn(sheet, 6);

        return workbook;
    }

    private static void writeHeader(Sheet sheet, int rowIndex) {
        CellStyle cellStyle = createStyleForHeader(sheet);
        //Create row
        Row row = sheet.createRow(rowIndex);
        //Create cells
        Cell idCell = row.createCell(0);
        idCell.setCellStyle(cellStyle);
        idCell.setCellValue("ID");

        Cell emailCell = row.createCell(1);
        emailCell.setCellStyle(cellStyle);
        emailCell.setCellValue("Email");

        Cell photoCell = row.createCell(2);
        photoCell.setCellStyle(cellStyle);
        photoCell.setCellValue("Photo");

        Cell firstNameCell = row.createCell(3);
        firstNameCell.setCellStyle(cellStyle);
        firstNameCell.setCellValue("First Name");

        Cell lastNameCell = row.createCell(4);
        lastNameCell.setCellStyle(cellStyle);
        lastNameCell.setCellValue("Last Name");

        Cell enabledCell = row.createCell(5);
        enabledCell.setCellStyle(cellStyle);
        enabledCell.setCellValue("Enabled");

        Cell rolesCell = row.createCell(6);
        rolesCell.setCellStyle(cellStyle);
        rolesCell.setCellValue("Roles");
    }

    private static void writeBody(Sheet sheet, int rowIndex, UserDTO userDTO, Workbook workbook) throws IOException {
        Row row = sheet.createRow(rowIndex);

        Cell id = row.createCell(0);
        id.setCellValue(userDTO.getId());

        Cell email = row.createCell(1);
        email.setCellValue(userDTO.getEmail());

        Cell photo = row.createCell(2);
        loadPhotoFile(userDTO.getId(), userDTO.getPhotos(), 2, rowIndex, workbook);

        Cell firstName = row.createCell(3);
        firstName.setCellValue(userDTO.getFirstName());

        Cell lastName = row.createCell(4);
        lastName.setCellValue(userDTO.getLastName());

        Cell enabled = row.createCell(5);
        enabled.setCellValue(userDTO.isEnabled());

        Cell roles = row.createCell(6);
        roles.setCellValue(userDTO.getRoles().toString());
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    private static void loadPhotoFile(String id, String photos, int col, int row, Workbook workbook) throws IOException {
        // Load image file
        File photo = new File("user-photos/" + id + "/" + photos);
        BufferedImage image = ImageIO.read(photo);
        // Add image to Excel sheet
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        Sheet sheet = workbook.getSheet("Sheet1");
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(col);
        anchor.setRow1(row);
        anchor.setCol2(col + 1);
        anchor.setRow2(row + 1);
        Picture picture = drawing.createPicture(anchor, pictureIdx);
    }

    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex <= lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}
