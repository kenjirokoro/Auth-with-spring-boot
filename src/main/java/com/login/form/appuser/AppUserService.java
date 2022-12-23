package com.login.form.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    
    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return appUserRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format(USER_NOT_FOUND_MSG, email) 
            )); 
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
            .findByEmail(appUser.getEmail())
            .isPresent();

        if (userExists) {
            throw new IllegalStateException("Email is already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
            .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        // TODO: send confirmation token

        return "it works\n";
    }
    
}
