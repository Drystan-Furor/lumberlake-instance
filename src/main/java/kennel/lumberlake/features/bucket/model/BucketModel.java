package kennel.lumberlake.features.bucket.model;

import java.util.List;

public record BucketModel(
        List<BucketImage> items,
        Integer nextCursor,
        int nextVisibleCount,
        boolean hasMore
) {
    public BucketModel {
        items = List.copyOf(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
