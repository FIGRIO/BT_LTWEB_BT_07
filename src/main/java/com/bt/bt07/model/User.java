package com.bt.bt07.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    private String phone;

    @Column(columnDefinition = "nvarchar(100)")
    private String fullname;

    private String email;

    private boolean admin;

    private boolean active;

    private String images;

    @JsonIgnore // Ngăn chặn vòng lặp vô tận khi xuất JSON
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories;
}