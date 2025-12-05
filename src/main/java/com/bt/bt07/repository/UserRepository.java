package com.bt.bt07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bt.bt07.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // JpaRepository đã có sẵn các hàm findById, findAll, save, delete
    // Bạn có thể thêm hàm tìm theo email nếu cần:
    // Optional<User> findByEmail(String email);
}