package kennel.lumberlake.features.bucket.repository;

import kennel.lumberlake.features.bucket.model.BucketImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Repository
public class BucketRepository {

    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of("avif", "jpg", "jpeg", "png", "webp");

    private final List<BucketImage> images;

    public BucketRepository(
            ResourcePatternResolver resourcePatternResolver,
            @Value("${bucket.resource-pattern:classpath*:static/img/photobook/*}") String resourcePattern,
            @Value("${bucket.public-path:/img/photobook/}") String publicPath,
            @Value("${bucket.alt-text:Jack Russell Terrier van Lumberlake kennel}") String altText
    ) {
        this.images = loadImages(resourcePatternResolver, resourcePattern, publicPath, altText);
    }

    public List<BucketImage> findAll() {
        return images;
    }

    private List<BucketImage> loadImages(
            ResourcePatternResolver resourcePatternResolver,
            String resourcePattern,
            String publicPath,
            String altText
    ) {
        try {
            Resource[] resources = resourcePatternResolver.getResources(resourcePattern);

            return Arrays.stream(resources)
                    .map(Resource::getFilename)
                    .filter(Objects::nonNull)
                    .filter(this::isSupportedImage)
                    .distinct()
                    .sorted(BucketRepository::compareNaturally)
                    .map(filename -> new BucketImage(filename, buildPublicUrl(publicPath, filename), altText))
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load bucket images from " + resourcePattern, e);
        }
    }

    private boolean isSupportedImage(String filename) {
        int extensionIndex = filename.lastIndexOf('.');
        if (extensionIndex < 0 || extensionIndex == filename.length() - 1) {
            return false;
        }

        String extension = filename.substring(extensionIndex + 1).toLowerCase(Locale.ROOT);
        return SUPPORTED_EXTENSIONS.contains(extension);
    }

    private String buildPublicUrl(String publicPath, String filename) {
        String normalizedPublicPath = publicPath.endsWith("/") ? publicPath : publicPath + "/";
        return normalizedPublicPath + filename;
    }

    private static int compareNaturally(String left, String right) {
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.length() && rightIndex < right.length()) {
            char leftChar = left.charAt(leftIndex);
            char rightChar = right.charAt(rightIndex);

            if (Character.isDigit(leftChar) && Character.isDigit(rightChar)) {
                int leftStart = leftIndex;
                int rightStart = rightIndex;

                while (leftIndex < left.length() && Character.isDigit(left.charAt(leftIndex))) {
                    leftIndex++;
                }
                while (rightIndex < right.length() && Character.isDigit(right.charAt(rightIndex))) {
                    rightIndex++;
                }

                BigInteger leftNumber = new BigInteger(left.substring(leftStart, leftIndex));
                BigInteger rightNumber = new BigInteger(right.substring(rightStart, rightIndex));
                int numberComparison = leftNumber.compareTo(rightNumber);
                if (numberComparison != 0) {
                    return numberComparison;
                }

                int digitCountComparison = Integer.compare(leftIndex - leftStart, rightIndex - rightStart);
                if (digitCountComparison != 0) {
                    return digitCountComparison;
                }
                continue;
            }

            int charComparison = Character.compare(
                    Character.toLowerCase(leftChar),
                    Character.toLowerCase(rightChar)
            );
            if (charComparison != 0) {
                return charComparison;
            }

            leftIndex++;
            rightIndex++;
        }

        return Comparator.comparingInt(String::length).compare(left, right);
    }
}
