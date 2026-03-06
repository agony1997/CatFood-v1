package com.example.catfood.domain.common.repository;

import com.example.catfood.domain.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
