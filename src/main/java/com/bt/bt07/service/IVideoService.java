package com.bt.bt07.service;

import com.bt.bt07.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IVideoService {
    // Lấy tất cả video
    List<Video> findAll();

    // Lấy tất cả video có phân trang
    Page<Video> findAll(Pageable pageable);

    // Lưu hoặc Cập nhật video
    <S extends Video> S save(S entity);

    // Xóa video theo ID
    void deleteById(Long id);

    // Tìm video theo ID (trả về Optional để tránh NullPointerException)
    Optional<Video> findById(Long id);

    // Tìm kiếm video theo tiêu đề + Phân trang
    Page<Video> findByTitleContaining(String title, Pageable pageable);

    // Đếm tổng số video
    long count();
}