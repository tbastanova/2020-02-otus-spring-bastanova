package ru.otus.homework18.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import ru.otus.homework18.service.MainService;

import java.util.Random;

@Service
public class MainServiceImpl implements MainService {

    @Override
    @HystrixCommand(fallbackMethod = "buildFallback")
    public String helloWord() {
        sleepRandomly();
        return "Hello World!";
    }

    private void sleepRandomly() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) {
            System.out.println("It is a chance for demonstrating Hystrix action");
            try {
                System.out.println("Start sleeping...." + System.currentTimeMillis());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Hystrix thread interupted...." + System.currentTimeMillis());
                e.printStackTrace();
            }
        }
    }

    public String buildFallback() {
        return "Сервис в данный момент не отвечает. Попробуйте позже";
    }
}
