package code.frfole.frfoletweaks.util;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadOnlyMap<K, V> extends ConcurrentHashMap<K, V> {
    public ReadOnlyMap() {
        super();
    }

    @Override
    public V put(@NotNull K key, @NotNull V value) {
        return value;
    }
}
