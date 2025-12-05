package com.bt.bt07.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Videos")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(500) not null")
    private String title;

    @Column(columnDefinition = "nvarchar(500)")
    private String poster;

    private int views;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String description;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "categoryId") // Khóa ngoại liên kết với bảng Category
    private Category category;
}