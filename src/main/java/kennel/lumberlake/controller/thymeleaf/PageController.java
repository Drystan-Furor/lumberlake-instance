package kennel.lumberlake.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }


}
