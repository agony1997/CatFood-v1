package com.example.catfoodv1.dto;

import com.example.catfoodv1.model.entity.auth.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String username; // 顯示名稱

    public static UserDto fromEntity(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
