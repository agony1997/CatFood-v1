package com.example.catfoodv1.service.auth;

import com.example.catfoodv1.model.entity.auth.Account;
import com.example.catfoodv1.repo.auth.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountCode) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountCode(accountCode)
                .orElseThrow(() -> new UsernameNotFoundException("無此使用者: " + accountCode));
        // User 實體轉換成 Spring Security 需要的 UserDetails 物件
        // 注意：第一個參數是 principal，也就是使用者的唯一識別，我們傳入 accountCode
        return new User(account.getAccountCode(),account.getPassword(),
                Collections.emptyList() // 這裡可以加入使用者的權限 (Roles)
        );
    }
}