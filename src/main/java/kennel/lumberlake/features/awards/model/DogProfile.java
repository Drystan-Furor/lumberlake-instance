package kennel.lumberlake.features.awards.model;

import java.util.List;

public record DogProfile(
        String slug,
        List<String> achievements
) {
}
