package com.bt.bt07.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bt.bt07.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    // Tìm kiếm theo tiêu đề chứa từ khóa
    List<Video> findByTitleContaining(String title);

    // Tìm kiếm theo tiêu đề và phân trang
    Page<Video> findByTitleContaining(String title, Pageable pageable);
}