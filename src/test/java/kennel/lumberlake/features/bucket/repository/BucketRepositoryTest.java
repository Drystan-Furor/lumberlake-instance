package kennel.lumberlake.features.bucket.repository;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import static org.assertj.core.api.Assertions.assertThat;

class BucketRepositoryTest {

    @Test
    void scansAndNaturallySortsClasspathImages() {
        BucketRepository bucketRepository = new BucketRepository(
                new PathMatchingResourcePatternResolver(),
                "classpath*:static/img/photobook/*",
                "/img/photobook/",
                "Jack Russell Terrier van Lumberlake kennel"
        );

        assertThat(bucketRepository.findAll()).hasSize(32);
        assertThat(bucketRepository.findAll().getFirst().filename()).isEqualTo("1.avif");
        assertThat(bucketRepository.findAll().get(11).filename()).isEqualTo("12.avif");
        assertThat(bucketRepository.findAll().getLast().filename()).isEqualTo("32.avif");
        assertThat(bucketRepository.findAll().getFirst().url()).isEqualTo("/img/photobook/1.avif");
    }
}
