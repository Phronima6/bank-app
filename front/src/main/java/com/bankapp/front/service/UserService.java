package com.bankapp.front.service;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import com.bankapp.front.dto.CreateUserDto;
import com.bankapp.front.feign.accounts.AccountSettingsDto;
import com.bankapp.front.feign.accounts.AccountsFeignClient;
import com.bankapp.front.feign.accounts.ResponseDto;
import com.bankapp.front.feign.accounts.UpdatePasswordDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class UserService {

    AccountsFeignClient accountsFeignClient;
    UserDetailsServiceImpl userDetailsService;

    public String createUser(CreateUserDto createUserDto, RedirectAttributes model, HttpServletRequest request) {
        ResponseDto response = accountsFeignClient.createUser(createUserDto);
        if (response.isHasErrors()) {
            handleUserCreationErrors(response, createUserDto, model);
            return "signup";
        } else {
            authenticateCreatedUser(createUserDto, request);
            return "redirect:/";
        }
    }

    private void handleUserCreationErrors(ResponseDto response, CreateUserDto createUserDto, RedirectAttributes model) {
        model.addFlashAttribute("errors", response.getErrors());
        model.addFlashAttribute("login", createUserDto.getLogin());
        model.addFlashAttribute("name", createUserDto.getName());
        model.addFlashAttribute("birthdate", createUserDto.getBirthdate());
    }

    private void authenticateCreatedUser(CreateUserDto createUserDto, HttpServletRequest request) {
        UserDetails principal = userDetailsService.loadUserByUsername(createUserDto.getLogin());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
    }

    public String updatePassword(String login,
                                 UpdatePasswordDto updatePasswordDto,
                                 RedirectAttributes model) {
        ResponseDto response = accountsFeignClient.updatePassword(login, updatePasswordDto);
        if (response.isHasErrors()) {
            model.addFlashAttribute("passwordErrors", response.getErrors());
        }
        return "redirect:/";
    }

    public String editUserAccounts(String login,
                                   AccountSettingsDto accountSettingsDto,
                                   RedirectAttributes model) {
        ResponseDto response = accountsFeignClient.editUserAccounts(login, accountSettingsDto);
        if (response.isHasErrors()) {
            model.addFlashAttribute("userAccountsErrors", response.getErrors());
        }
        return "redirect:/";
    }

}
