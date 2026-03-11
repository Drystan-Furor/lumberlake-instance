package kennel.lumberlake.features.awards.service;

import kennel.lumberlake.features.awards.model.DogProfile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DogProfileService {

    private final Map<String, DogProfile> profiles;

    public DogProfileService(ResourceLoader resourceLoader) {
        this.profiles = loadProfiles(resourceLoader.getResource("classpath:awards.yaml"));
    }

    public DogProfile getProfile(String slug) {
        return profiles.get(slug);
    }

    @SuppressWarnings("unchecked")
    private Map<String, DogProfile> loadProfiles(Resource resource) {
        Yaml yaml = new Yaml();

        try (InputStream inputStream = resource.getInputStream()) {
            Map<String, Object> rawProfiles = yaml.load(inputStream);
            if (rawProfiles == null) {
                return Map.of();
            }

            Map<String, DogProfile> loadedProfiles = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : rawProfiles.entrySet()) {
                Map<String, Object> profile = (Map<String, Object>) entry.getValue();
                List<String> achievements = (List<String>) profile.get("achievements");
                loadedProfiles.put(
                        entry.getKey(),
                        new DogProfile(entry.getKey(), Objects.requireNonNullElse(achievements, List.of()))
                );
            }

            return Map.copyOf(loadedProfiles);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load awards.yaml", e);
        }
    }
}
