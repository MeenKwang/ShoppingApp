package com.shopping.admin.helper;

import com.shopping.admin.dto.ProductExtraImageDto;
import com.shopping.admin.dto.ProductFormDto;
import com.shopping.admin.repository.ProductImageRepository;
import com.shopping.admin.util.FileUploadUtil;
import com.shopping.common.entity.Product;
import com.shopping.common.entity.ProductImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProductSaveHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSaveHelper.class);

    public static void setMainImageName(MultipartFile mainImageMultipart, Product product) {
        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            product.setMainImage(fileName);
        }
    }

    public static void saveMainImageFile(MultipartFile mainImage, Product savedProduct) throws IOException {
        if (mainImage != null) {
            String fileName = StringUtils.cleanPath(mainImage.getOriginalFilename());
            String uploadDir = "../product-images/" + savedProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImage);
        }
    }
    public static void saveExtraImageFiles(MultipartFile[] extraImageFiles, Product savedProduct) throws IOException {
        if (extraImageFiles.length > 0) {
            String uploadDir = "../product-images/" + savedProduct.getId() + "/extras";

            for (MultipartFile multipartFile : extraImageFiles) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }
    }
    public static void deleteExtraImagesWereRemovedOnForm(Product product) throws IOException {
        String extraImageDir = "../product-images/" + product.getId() + "/extras";
        Path dirPath = Paths.get(extraImageDir);

        try {
            Files.list(dirPath).forEach(file -> {
                String filename = file.toFile().getName();

                if (!product.containsImageName(filename)) {
                    try {
                        Files.delete(file);
                        LOGGER.info("Deleted extra image: " + filename);

                    } catch (IOException e) {
                        LOGGER.info("Could not delete extra image: " + filename);
                    }
                }

            });
        } catch (IOException ex) {
            LOGGER.info("Could not list directory: " + dirPath);
        }
    }

    public static void saveExtraImages(MultipartFile[] extraImages, Integer savedProductId) throws IOException {
        for(MultipartFile image : extraImages) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            String uploadDir = "../product-images/" + savedProductId + "/extras";
            FileUploadUtil.saveFile(uploadDir, fileName, image);
        }
    }

}
