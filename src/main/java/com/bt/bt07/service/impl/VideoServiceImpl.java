package com.bt.bt07.service.impl;

import com.bt.bt07.model.Video;
import com.bt.bt07.repository.VideoRepository;
import com.bt.bt07.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Đánh dấu đây là một Bean Service để Spring quản lý
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoRepository videoRepository; // Tiêm Repository vào để sử dụng

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Page<Video> findAll(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public <S extends Video> S save(S entity) {
        // Có thể thêm logic kiểm tra dữ liệu trước khi lưu tại đây nếu cần
        return videoRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    @Override
    public Page<Video> findByTitleContaining(String title, Pageable pageable) {
        return videoRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public long count() {
        return videoRepository.count();
    }
}