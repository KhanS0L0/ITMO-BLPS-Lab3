package com.example.delegates.userProcess;

import com.example.service.implementation.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatingNotification implements JavaDelegate {

    private final NotificationServiceImpl notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
            //TODO
    }
}
