package com.login.form.registration;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.login.form.appuser.AppUser;
import com.login.form.appuser.AppUserRole;
import com.login.form.appuser.AppUserService;
import com.login.form.registration.token.ConfirmationToken;
import com.login.form.registration.token.ConfirmationTokenService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email is not valid");
        }

        return appUserService.signUpUser(
            new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
            )
        );
    } 

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
            .getToken(token)
            .orElseThrow(() -> new IllegalStateException("Token is not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email was already taken");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        appUserService.enableAppUser(
            confirmationToken.getAppUser().getEmail()
        );

        return "Confirmed\n";
    }
    
}
