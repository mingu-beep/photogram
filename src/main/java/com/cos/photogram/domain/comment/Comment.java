package com.cos.photogram.domain.comment;

import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(unique = true)
    private int id;

    private String content;

    /*
        하나의 댓글은 하나의 유저만 작성할 수 있다.
        하나의 유저는 여러개의 댓글을 작성할 수 있따.
        -> N : 1
     */
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"images"})
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /*
        하나의 댓글은 하나의 이미지에 달린다.
        하나의 이미지는 여러개의 댓글을 가질 수 있다.
     */
    @JoinColumn(name = "imageId")
    @ManyToOne(fetch = FetchType.EAGER) // 하나의 댓글은 하나의 이미지에 종속되기 때문에 EAGER로 설정해도 OK
    private Image image;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
