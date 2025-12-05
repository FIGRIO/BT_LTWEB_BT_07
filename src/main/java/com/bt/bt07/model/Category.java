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
@Table(name = "Categories")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(200)")
    private String categoryname;

    @Column(columnDefinition = "nvarchar(100)")
    private String categorycode;

    private String images;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "username") // Liên kết với bảng User
    private User user;

    @JsonIgnore // Ngăn chặn vòng lặp vô tận khi xuất JSON
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Video> videos;
}