package kennel.lumberlake.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class StaticResourceCacheWebTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void generatedImagesAreServedWithLongLivedCacheHeaders() throws Exception {
        mockMvc.perform(get("/img/logo/logo-512.avif"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", containsString("max-age=31536000")))
                .andExpect(header().string("Cache-Control", containsString("public")));
    }

    @Test
    void buildAssetsAreServedWithCacheHeaders() throws Exception {
        mockMvc.perform(get("/build/style.css"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", containsString("max-age=604800")))
                .andExpect(header().string("Cache-Control", containsString("public")));
    }
}
