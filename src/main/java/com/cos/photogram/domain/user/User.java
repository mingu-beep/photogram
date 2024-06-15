package com.cos.photogram.domain.user;

import com.cos.photogram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder // builder 패턴을 사용할 수 있게 함
@AllArgsConstructor // 모든 생성자를 자동으로 생성
@NoArgsConstructor // 빈 생성자를 자동으로 생성
@Data // 자동으로 Getter, Setter, toString 생성
@Entity // DB에 테이블을 생성 -> PK 를 설정해주지 않으면 오류 발생!
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // PK 지정
    @Column(unique = true)
    private Integer id;

    @Column(unique = true, length = 20, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String website;
    private String bio;
    private String tel;
    private String gender;

    private String role;

    // Image 객체와의 양방향 매핑
    // mappedBy : 연관관계의 주인이 user가 아니다.
    // 따라서, 유저 테이블을 생성할 때, imags 테이블을 만들지 않겠다.
    // User오브젝트를 select할 때, 해당 userId로 Upload된 모든 image들을 같이 가져와야한다.
    // LAZY : User 오브젝트를 SELECT 할떄, 해당 userId로 등록된 모든 Image들을 가져오지 않겠다.
    // EAGER : User 오브젝트를 SELECT 할때, 해당 userId로 등록된 모든 image들을 조인을 통해 가져온다.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // 이 오브젝트 내부에 있는 user를 JSON으로 파싱하지 않겠다.
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
