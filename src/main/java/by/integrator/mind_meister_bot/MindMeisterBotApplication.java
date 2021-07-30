package by.integrator.mind_meister_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication

public class MindMeisterBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(MindMeisterBotApplication.class, args);
    }
}
