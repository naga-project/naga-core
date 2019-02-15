package webfx.tool.buildtool.util.spliterable.operable.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Predicate;

/**
 * @author Bruno Salmon
 */
final class DistinctSpliterator<T> extends FilteredSpliterator<T> {

    DistinctSpliterator(Spliterator<T> spliterator) {
        super(spliterator, new Predicate<T>() {
            private final Set<T> values = new HashSet<>();
            @Override
            public boolean test(T t) {
                return values.add(t);
            }
        });
    }
}
