package kennel.lumberlake.features.bucket.service;

import kennel.lumberlake.features.bucket.model.BucketModel;
import kennel.lumberlake.features.bucket.repository.BucketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import static org.assertj.core.api.Assertions.assertThat;

class BucketServiceTest {

    private BucketService bucketService;

    @BeforeEach
    void setUp() {
        BucketRepository bucketRepository = new BucketRepository(
                new PathMatchingResourcePatternResolver(),
                "classpath*:static/img/photobook/*",
                "/img/photobook/",
                "Jack Russell Terrier van Lumberlake kennel"
        );
        bucketService = new BucketService(bucketRepository, 12);
    }

    @Test
    void returnsInitialServerRenderedBatch() {
        BucketModel bucketPage = bucketService.getInitialBucket(null);

        assertThat(bucketPage.items()).hasSize(12);
        assertThat(bucketPage.nextCursor()).isEqualTo(12);
        assertThat(bucketPage.nextVisibleCount()).isEqualTo(24);
        assertThat(bucketPage.hasMore()).isTrue();
        assertThat(bucketPage.items().getFirst().filename()).isEqualTo("1.avif");
        assertThat(bucketPage.items().getLast().filename()).isEqualTo("12.avif");
    }

    @Test
    void returnsNextBatchFromCursor() {
        BucketModel bucketPage = bucketService.getPage(12);

        assertThat(bucketPage.items()).hasSize(12);
        assertThat(bucketPage.items().getFirst().filename()).isEqualTo("13.avif");
        assertThat(bucketPage.items().getLast().filename()).isEqualTo("24.avif");
        assertThat(bucketPage.nextCursor()).isEqualTo(24);
        assertThat(bucketPage.nextVisibleCount()).isEqualTo(32);
        assertThat(bucketPage.hasMore()).isTrue();
    }

    @Test
    void returnsLastBatchWithoutExtraCursor() {
        BucketModel bucketPage = bucketService.getPage(24);

        assertThat(bucketPage.items()).hasSize(8);
        assertThat(bucketPage.items().getFirst().filename()).isEqualTo("25.avif");
        assertThat(bucketPage.items().getLast().filename()).isEqualTo("32.avif");
        assertThat(bucketPage.nextCursor()).isNull();
        assertThat(bucketPage.nextVisibleCount()).isEqualTo(32);
        assertThat(bucketPage.hasMore()).isFalse();
    }

    @Test
    void expandsVisibleCountForNoJavascriptFallback() {
        BucketModel bucketPage = bucketService.getInitialBucket(24);

        assertThat(bucketPage.items()).hasSize(24);
        assertThat(bucketPage.nextCursor()).isEqualTo(24);
        assertThat(bucketPage.nextVisibleCount()).isEqualTo(32);
        assertThat(bucketPage.hasMore()).isTrue();
    }
}
