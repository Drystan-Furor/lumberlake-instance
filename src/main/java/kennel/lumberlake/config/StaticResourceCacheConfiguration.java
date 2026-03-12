package kennel.lumberlake.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.time.Duration;

@Configuration
public class StaticResourceCacheConfiguration implements WebMvcConfigurer {

    private static final CacheControl IMAGE_CACHE_CONTROL = CacheControl.maxAge(Duration.ofDays(365))
            .cachePublic();
    private static final CacheControl BUILD_CACHE_CONTROL = CacheControl.maxAge(Duration.ofDays(365))
            .cachePublic()
            .immutable();

    @Bean
    ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/")
                .setCacheControl(IMAGE_CACHE_CONTROL);

        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCacheControl(IMAGE_CACHE_CONTROL);

        ResourceChainRegistration buildResources = registry.addResourceHandler("/build/**")
                .addResourceLocations("classpath:/static/build/")
                .setCacheControl(BUILD_CACHE_CONTROL)
                .resourceChain(true);

        buildResources.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
        buildResources.addResolver(new PathResourceResolver());
    }
}
