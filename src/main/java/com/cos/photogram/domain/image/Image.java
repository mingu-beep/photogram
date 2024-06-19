package com.cos.photogram.domain.image;

import com.cos.photogram.domain.likes.Likes;
import com.cos.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // PK 지정
    @Column(unique = true)
    private Integer id;

    private String caption;
    private String postImageUrl;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"images"})
    private User user;

    // 이미지 좋아요
    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY)
    private List<Likes> likes;

    @Transient // DB에 해당 컬럼을 생성하지 않게 만드는 애노테이션
    private boolean likeState;

    // 이미지 좋아요 카운팅
    @Transient
    private Integer likeCount;


    // 댓글 정보

    private LocalDateTime createDate;
    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
