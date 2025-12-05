package com.bt.bt07.service.impl;

import com.bt.bt07.config.StorageProperties;
import com.bt.bt07.exception.StorageException;
import com.bt.bt07.exception.StorageFileNotFoundException;
import com.bt.bt07.service.IStorageService;

import org.apache.commons.io.FilenameUtils; // Cần thư viện commons-io trong pom.xml
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class FileSystemStorageServiceImpl implements IStorageService { // [cite: 253]

    private final Path rootLocation;

    // Constructor Injection để lấy cấu hình thư mục lưu trữ
    public FileSystemStorageServiceImpl(StorageProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation); // Tạo thư mục nếu chưa có [cite: 302]
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String getStorageFilename(MultipartFile file, String id) {
        // Lấy đuôi file gốc (ví dụ .png, .jpg)
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        // Tạo tên file mới: p + id + đuôi file
        return "p" + id + "." + ext; // [cite: 258]
    }

    @Override
    public void store(MultipartFile file, String storeFilename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file."); // [cite: 265]
            }
            // Tính toán đường dẫn lưu file tuyệt đối
            Path destinationFile = this.rootLocation.resolve(Paths.get(storeFilename))
                    .normalize().toAbsolutePath();

            // Kiểm tra bảo mật: Không cho phép lưu ra ngoài thư mục quy định
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory."); // [cite: 268]
            }

            // Copy file vào thư mục đích (ghi đè nếu đã tồn tại)
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING); // [cite: 270]
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri()); // [cite: 280]
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename); // [cite: 291]
    }

    @Override
    public void delete(String storeFilename) throws Exception {
        Path destinationFile = rootLocation.resolve(Paths.get(storeFilename)).normalize().toAbsolutePath();
        Files.deleteIfExists(destinationFile); // [cite: 297]
    }
}