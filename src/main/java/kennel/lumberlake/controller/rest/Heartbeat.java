package kennel.lumberlake.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Heartbeat {

    @GetMapping(value="/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

}
