package com.springproject.sns.model.entity;

import com.springproject.sns.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "\"user\"") // postgresql 에는 user 테이블이 이미 존재하므로
@SQLDelete(sql = "UPDATE \"user\" SET removed_at = NOW() WHERE id=?") // 삭제 쿼리 전 update 쿼리로 변환
@Where(clause = "removed_at is NULL")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "user_name", unique = true)
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER; // user 권한정보 default USER

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "removed_at")
    private Timestamp removedAt; // soft delete


    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    } // 저장 전 오늘 날짜로 지정

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    } // 수정전 오늘 날짜로 지정

    public static UserEntity of(String userName, String encodedPwd) {
        UserEntity entity = new UserEntity();
        entity.setUserName(userName);
        entity.setPassword(encodedPwd);
        return entity;
    }
}
