package com.cos.photogram.domain.likes;

import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
)
public class Likes { // 1 N

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(unique = true)
    private Integer id;

    @JoinColumn(name = "userId")
    @ManyToOne // 주체 -> 객체 순 (주체 : Likes , 객체 : user)
    /*
        Likes : User
        하나의 유저는 여러 개의 좋아요를 가질 수 있고
        하나의 좋아요는 하나의 유저만 가질 수 있다.
        -> 따라서 N : 1 -> ManyToOne
     */
    private User user; // 1

    @JoinColumn(name = "imageId")
    @ManyToOne
    /*
        Likes : Image
        하나의 이미지는 여러개의 좋아요를 가질 수 있고
        하나의 좋아요는 하나의 이미지만 갖는다.
        -> 따라서 N : 1 -> ManyToOne
     */
    private Image image;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
