package com.bankapp.front.service;

import com.bankapp.front.feign.accounts.AccountsFeignClient;
import com.bankapp.front.feign.accounts.UserDto;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    AccountsFeignClient accountsFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDto userInfo = accountsFeignClient.fetchUserByLogin(username);
            return User.withUsername(username)
                    .password(userInfo.getPassword())
                    .authorities(new GrantedAuthority[0])
                    .accountExpired(false)
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .disabled(false)
                    .build();
        } catch (FeignException.FeignClientException ex) {
            throw new UsernameNotFoundException("User service unavailable", ex);
        }
    }

}
