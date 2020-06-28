package ru.otus.homework15.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.homework15.domain.Butterfly;
import ru.otus.homework15.domain.Larva;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LifeCycleService {

    private final Nature nature;

    private static final String[] LARVAS = {"Зеленая гусеница", "Красная гусеница", "Черная гусеница", "Синяя гусеница"};


    public LifeCycleService(Nature nature) {
        this.nature = nature;
    }

    public void run() {
        try {
            while (true) {

                Thread.sleep(1000);

                Collection<Larva> items = generateLarvas();
                System.out.println("___________");
                System.out.println("Гусеницы: " +
                        items.stream().map(Larva::getName)
                                .collect(Collectors.joining(",")));
                Collection<Butterfly> butterflies = nature.process(items);
                System.out.println("Бабочки: " + butterflies.stream()
                        .map(Butterfly::getName)
                        .collect(Collectors.joining(",")));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Larva generateLarva() {
        return new Larva(LARVAS[RandomUtils.nextInt(0, LARVAS.length)]);
    }

    private static Collection<Larva> generateLarvas() {
        List<Larva> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, LARVAS.length); ++i) {
            items.add(generateLarva());
        }
        return items;
    }
}
