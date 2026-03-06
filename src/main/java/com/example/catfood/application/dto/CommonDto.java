package com.example.catfood.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommonDto {
    private UUID id;
    private String code;
    private String name;
}
