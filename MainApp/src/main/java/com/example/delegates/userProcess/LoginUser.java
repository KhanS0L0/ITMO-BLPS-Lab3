package com.example.delegates.userProcess;

import com.example.entity.user.User;
import com.example.security.CustomUserDetailsService;
import com.example.service.implementation.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUser implements JavaDelegate {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        String email = (String) delegateExecution.getVariable("email");
        String password = (String) delegateExecution.getVariable("password");

        User user = userService.findUserByUsername(username);
        if (passwordEncoder.matches(password, user.getPassword()) && user.getEmail().equals(email))
            delegateExecution.setVariable("userId", user.getId());
        else
            delegateExecution.setVariable("userId", 0);

    }

}
