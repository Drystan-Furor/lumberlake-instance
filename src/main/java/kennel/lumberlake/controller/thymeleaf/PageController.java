package kennel.lumberlake.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/placeholder")
    public String placeholder(Model model) {
        model.addAttribute("contentTemplate", "fragments/placeholder/content");
        return "skeleton";
    }

    @GetMapping("/information")
    public String information(Model model) {
        model.addAttribute("contentTemplate", "pages/information/information");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }


    @GetMapping("/vaccineren")
    public String vaccineren(Model model) {
        model.addAttribute("contentTemplate", "pages/information/content/vaccineren");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }

    @GetMapping("/carnivoor")
    public String carnivoor(Model model) {
        model.addAttribute("contentTemplate", "pages/information/content/carnivoor");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }

    @GetMapping("/titeren")
    public String titeren(Model model) {
        model.addAttribute("contentTemplate", "pages/information/content/titeren");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }

    @GetMapping("/steriliseren")
    public String steriliseren(Model model) {
        model.addAttribute("contentTemplate", "pages/information/content/steriliseren");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }

    @GetMapping("/jrt")
    public String jrt(Model model) {
        model.addAttribute("contentTemplate", "pages/information/content/jrt");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }

    @GetMapping("/shootingtimesarticle")
    public String shootingTimesArticle(Model model) {
        model.addAttribute("contentTemplate", "pages/information/content/shootingtimesarticle");
        model.addAttribute("contentFragment", "content");
        return "skeleton";
    }


}
