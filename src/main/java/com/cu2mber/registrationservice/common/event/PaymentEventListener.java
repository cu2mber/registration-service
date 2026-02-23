package com.cu2mber.registrationservice.common.event;


import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final RegistrationService registrationService;

    @Transactional
    @RabbitListener(queues = "${message.queue.payment}")
    public void handlePaymentApproved(RegistrationCreateCommand command) {
        registrationService.createRegistration(command);
    }
}
