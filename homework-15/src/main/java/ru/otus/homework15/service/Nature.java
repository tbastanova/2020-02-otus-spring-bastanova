package ru.otus.homework15.service;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework15.domain.Butterfly;
import ru.otus.homework15.domain.Larva;

import java.util.Collection;

@MessagingGateway
public interface Nature {

    @Gateway(requestChannel = "larvaChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Larva> larvas);
}
