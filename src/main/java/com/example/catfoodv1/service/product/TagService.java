package com.example.catfoodv1.service.product;

import com.example.catfoodv1.repo.product.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
}
