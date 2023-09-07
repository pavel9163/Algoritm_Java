import java.util.*;

public class HashMapCustom<K, V> implements Iterable<Map.Entry<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private List<Entry<K, V>>[] buckets;
    private int size;

    public HashMapCustom() {
        this(DEFAULT_CAPACITY);
    }

    public HashMapCustom(int capacity) {
        buckets = new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new ArrayList<>();
        }
        size = 0;
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        List<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        List<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = getBucketIndex(key);
        List<Entry<K, V>> bucket = buckets[index];
        Iterator<Entry<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                size--;
                return;
            }
        }
    }

    public int size() {
        return size;
    }

    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % buckets.length;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private int bucketIndex = 0;
            private Iterator<Entry<K, V>> entryIterator = Collections.emptyIterator();

            @Override
            public boolean hasNext() {
                while (!entryIterator.hasNext() && bucketIndex < buckets.length) {
                    entryIterator = buckets[bucketIndex].iterator();
                    bucketIndex++;
                }
                return entryIterator.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return entryIterator.next();
            }
        };
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    public static void main(String[] args) {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        // Перебор элементов с использованием цикла foreach
        for (Map.Entry<String, Integer> entry : map) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
