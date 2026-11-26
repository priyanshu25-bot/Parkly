package parkly.storage;

import parkly.exception.ParklyException;

import java.util.Collection;
import java.util.Map;

public interface Repository<K, V> {
    void saveAll(Collection<V> items) throws ParklyException;

    Map<K, V> loadAll() throws ParklyException;
}
