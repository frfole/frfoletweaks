package code.frfole.frfoletweaks.util;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadOnlyMap<K, V> extends ConcurrentHashMap<K, V> {
    public ReadOnlyMap() {
        super();
    }

    public ReadOnlyMap(@NotNull Map<? extends K, ? extends V> m) {
        super(m);
    }

    @Override
    public V put(K key, V value) {
        return value;
    }
}
