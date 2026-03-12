package kennel.lumberlake.features.bucket.service;

import kennel.lumberlake.features.bucket.model.BucketImage;
import kennel.lumberlake.features.bucket.model.BucketModel;
import kennel.lumberlake.features.bucket.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final int batchSize;

    public BucketService(
            BucketRepository bucketRepository,
            @Value("${bucket.batch-size:12}") int batchSize
    ) {
        this.bucketRepository = bucketRepository;
        this.batchSize = Math.max(1, batchSize);
    }

    public BucketModel getInitialBucket(Integer requestedVisibleCount) {
        List<BucketImage> images = bucketRepository.findAll();
        int visibleCount = requestedVisibleCount == null ? batchSize : requestedVisibleCount;
        int clampedVisibleCount = clamp(visibleCount, batchSize, images.size());

        return createBucketModel(images.subList(0, clampedVisibleCount), clampedVisibleCount, images.size());
    }

    public BucketModel getPage(Integer requestedCursor) {
        List<BucketImage> images = bucketRepository.findAll();
        int cursor = clamp(requestedCursor == null ? 0 : requestedCursor, 0, images.size());
        int endExclusive = Math.min(cursor + batchSize, images.size());

        return createBucketModel(images.subList(cursor, endExclusive), endExclusive, images.size());
    }

    private BucketModel createBucketModel(List<BucketImage> items, int loadedCount, int totalCount) {
        boolean hasMore = loadedCount < totalCount;
        Integer nextCursor = hasMore ? loadedCount : null;
        int nextVisibleCount = hasMore ? Math.min(loadedCount + batchSize, totalCount) : totalCount;

        return new BucketModel(items, nextCursor, nextVisibleCount, hasMore);
    }

    private int clamp(int value, int minimum, int maximum) {
        if (maximum <= 0) {
            return 0;
        }

        return Math.min(Math.max(value, minimum), maximum);
    }
}
