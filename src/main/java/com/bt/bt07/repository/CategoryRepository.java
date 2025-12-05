package com.bt.bt07.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bt.bt07.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Tìm kiếm Category theo tên (theo hướng dẫn PDF)
    List<Category> findByCategorynameContaining(String name);

    // Tìm kiếm và phân trang (Bổ sung thêm cho đầy đủ)
    Page<Category> findByCategorynameContaining(String name, Pageable pageable);
}