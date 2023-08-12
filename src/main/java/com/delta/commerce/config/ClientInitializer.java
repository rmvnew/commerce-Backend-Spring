package com.delta.commerce.config;

import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.entity.Client;
import com.delta.commerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientInitializer implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private ClientRepository clientRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (this.clientRepository.count() == 0) {

            var clientRequest = new Client();
            clientRequest.setClientCpf(null);
            clientRequest.setClientCnpj(null);
            clientRequest.setClientEmail("email@email.com");
            clientRequest.setClientName("Default");
            clientRequest.setClientResponsible("System");
            clientRequest.setCompany(false);
            clientRequest.setTelephone(null);
            clientRequest.setAddress(null);
            clientRequest.setActive(true);
            clientRequest.setCreateAt(LocalDateTime.now());
            clientRequest.setUpdateAt(LocalDateTime.now());

            this.clientRepository.save(clientRequest);
        }

    }
}
