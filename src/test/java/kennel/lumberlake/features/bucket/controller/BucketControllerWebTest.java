package kennel.lumberlake.features.bucket.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class BucketControllerWebTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void homePageRendersInitialBucketBatch() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("id=\"bucket-items\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/bucket/page?cursor=12")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/img/photobook/1.avif")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/img/photobook/12.avif")));
    }

    @Test
    void bucketPageEndpointRendersNextFragmentBatch() throws Exception {
        mockMvc.perform(get("/bucket/page").param("cursor", "12"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/img/photobook/13.avif")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/img/photobook/24.avif")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("hx-swap-oob=\"outerHTML\"")));
    }
}
