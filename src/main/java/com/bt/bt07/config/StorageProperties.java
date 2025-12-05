package com.bt.bt07.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage") //
public class StorageProperties {
    private String location = "uploads/images"; // Thư mục mặc định để lưu ảnh
}