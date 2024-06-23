package com.sparta.delivery_app.domain.openApi.entity;


import com.sparta.delivery_app.domain.commen.BaseTimeCreateEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "api")
public class OpenApi extends BaseTimeCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "remoteAddr_id")
    private Long id;

    @Column(nullable = false)
    private String remoteAddr;

    public OpenApi(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
}
