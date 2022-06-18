package com.example.delegates.userProcess;

import com.example.dto.RegistrationDTO;
import com.example.service.implementation.registration.RegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUser implements JavaDelegate {

    private final RegistrationServiceImpl registrationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        String email = (String) delegateExecution.getVariable("email");
        String password = (String) delegateExecution.getVariable("password");
        RegistrationDTO newUser = RegistrationDTO.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        registrationService.signUp(newUser);
    }
}
