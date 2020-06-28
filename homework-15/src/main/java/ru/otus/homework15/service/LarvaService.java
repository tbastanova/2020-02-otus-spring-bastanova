package ru.otus.homework15.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.homework15.domain.Butterfly;
import ru.otus.homework15.domain.Larva;

@Service
public class LarvaService {

    private static final String[] BUTTERFLIES = {"Капустница", "Павлиний глаз", "Голубянка", "Монарх", "Адмирал"};

    public Butterfly pupate(Larva larva) throws Exception {
        System.out.println(larva.getName() + " окуклилась");
        Thread.sleep(3000);
        System.out.println("Окукливание " + larva.getName() + " завершено");
        return new Butterfly(generateButterfly().getName());
    }

    private static Butterfly generateButterfly() {
        return new Butterfly(BUTTERFLIES[RandomUtils.nextInt(0, BUTTERFLIES.length)]);
    }
}
