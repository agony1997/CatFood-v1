package com.example.catfood.application.auth;

import com.example.catfood.domain.account.entity.Account;
import com.example.catfood.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountCode) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountCode(accountCode)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found: " + accountCode));

        return new User(
                account.getAccountCode(),
                account.getPassword(),
                account.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleCode()))
                        .toList()
        );
    }
}
