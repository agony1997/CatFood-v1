package com.example.catfoodv1.model.dto.bussines;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonDto {
    private UUID id;
    private String code;
    private String name;
    private UUID parentId;

    public CommonDto(UUID id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
