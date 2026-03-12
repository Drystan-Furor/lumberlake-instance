package kennel.lumberlake.controller.thymeleaf;

import kennel.lumberlake.features.awards.service.DogProfileService;
import kennel.lumberlake.features.bucket.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final DogProfileService dogProfileService;
    private final BucketService bucketService;

    public PageController(
            DogProfileService dogProfileService,
            BucketService bucketService
    ) {
        this.dogProfileService = dogProfileService;
        this.bucketService = bucketService;
    }

    @GetMapping("/")
    public String showHomePage(
            Model model,
            @RequestParam(name = "bucketVisibleCount", required = false) Integer bucketVisibleCount
    ) {
        model.addAttribute("emmaProfile", dogProfileService.getProfile("emma"));
        model.addAttribute("kingProfile", dogProfileService.getProfile("king"));
        model.addAttribute("sofietjeProfile", dogProfileService.getProfile("sofietje"));
        model.addAttribute("bucketPage", bucketService.getInitialBucket(bucketVisibleCount));
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
