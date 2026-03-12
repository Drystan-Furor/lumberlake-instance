package kennel.lumberlake.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class StaticResourceCacheWebTest {

    private static final Pattern STYLESHEET_PATTERN = Pattern.compile("/build/style-[^\"]+\\.css");
    private static final Pattern SCRIPT_PATTERN = Pattern.compile("/build/dist/main\\.bundle-[^\"]+\\.js");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ResourceUrlEncodingFilter resourceUrlEncodingFilter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilters(resourceUrlEncodingFilter)
                .build();
    }

    @Test
    void generatedImagesAreServedWithLongLivedCacheHeaders() throws Exception {
        mockMvc.perform(get("/img/lumberlake-banner-640.avif"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", containsString("max-age=31536000")))
                .andExpect(header().string("Cache-Control", containsString("public")));
    }

    @Test
    void homePageUsesVersionedBuildAssetUrls() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        org.assertj.core.api.Assertions.assertThat(findFirst(content, STYLESHEET_PATTERN))
                .startsWith("/build/style-");
        org.assertj.core.api.Assertions.assertThat(findFirst(content, SCRIPT_PATTERN))
                .startsWith("/build/dist/main.bundle-");
    }

    @Test
    void versionedBuildAssetsAreServedWithLongLivedCacheHeaders() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String stylesheetPath = findFirst(result.getResponse().getContentAsString(), STYLESHEET_PATTERN);

        mockMvc.perform(get(stylesheetPath))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", containsString("max-age=31536000")))
                .andExpect(header().string("Cache-Control", containsString("public")))
                .andExpect(header().string("Cache-Control", containsString("immutable")));
    }

    private String findFirst(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        org.assertj.core.api.Assertions.assertThat(matcher.find())
                .as("Expected pattern %s in rendered content", pattern)
                .isTrue();
        return matcher.group();
    }
}
