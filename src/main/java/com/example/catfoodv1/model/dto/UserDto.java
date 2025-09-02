package com.example.catfoodv1.model.dto;

import com.example.catfoodv1.model.entity.auth.Account;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String username; // 顯示名稱

    public static UserDto fromEntity(Account account) {
        if (account == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(account.getId());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getDisplayName());
        return dto;
    }
}
