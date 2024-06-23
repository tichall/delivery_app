package com.sparta.delivery_app.domain.openApi.repository;

import com.sparta.delivery_app.domain.openApi.entity.OpenApi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenApiRepository extends JpaRepository<OpenApi, Long> {
}