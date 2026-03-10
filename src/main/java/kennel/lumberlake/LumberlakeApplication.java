package kennel.lumberlake;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class LumberlakeApplication {

    static void main(String[] args) {
        SpringApplication.run(LumberlakeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logStartupArt() {
        log.info("""
Application is running! Access it at: http://localhost:8080/
				
                .____                 ___.                .__          __          \s
                |    |    __ __  _____\\_ |__   ___________|  | _____  |  | __ ____ \s
                |    |   |  |  \\/     \\| __ \\_/ __ \\_  __ \\  | \\__  \\ |  |/ // __ \\\s
                |    |___|  |  /  Y Y  \\ \\_\\ \\  ___/|  | \\/  |__/ __ \\|    <\\  ___/\s
                |_______ \\____/|__|_|  /___  /\\___  >__|  |____(____  /__|_ \\\\___  >
                        \\/           \\/    \\/     \\/                \\/     \\/    \\/\s
				""");
    }
}
