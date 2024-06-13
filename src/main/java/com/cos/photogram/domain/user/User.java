package com.cos.photogram.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
