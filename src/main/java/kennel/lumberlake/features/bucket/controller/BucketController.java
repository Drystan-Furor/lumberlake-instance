package kennel.lumberlake.features.bucket.controller;

import kennel.lumberlake.features.bucket.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/page")
    public String page(
            @RequestParam(name = "cursor", required = false) Integer cursor,
            Model model
    ) {
        model.addAttribute("bucketPage", bucketService.getPage(cursor));
        return "fragments/images-bucket/bucket :: bucket-page";
    }
}
