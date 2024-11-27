package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author 张竹和
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return null;
        }
        return buckets[index].get(key);
        // throw new UnsupportedOperationException();
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF) {
            resize(buckets.length * 2);
        }

        int index = hash(key);
        if (buckets[index] == null) {
            buckets[index] = new ArrayMap<>();
        }

        buckets[index].put(key, value);
        size++;
        // throw new UnsupportedOperationException();
    }

    private void resize(int newCapacity) {
        ArrayMap<K, V>[] newBuckets = new ArrayMap[newCapacity];
        for (ArrayMap<K, V> bucket : buckets) {
            if (bucket != null) {
                for (K key : bucket.keySet()) {
                    V value = bucket.get(key);
                    int newIndex = Math.floorMod(key.hashCode(), newCapacity);
                    if (newBuckets[newIndex] == null) {
                        newBuckets[newIndex] = new ArrayMap<>();
                    }
                    newBuckets[newIndex].put(key, value);  // 重新插入到新桶
                }
            }
        }
        buckets = newBuckets;  // 更新桶数组
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
        // throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            if (bucket != null) {
                keys.addAll(bucket.keySet());
            }
        }
        return keys;
        // throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return null;
        }
        V value = buckets[index].remove(key);
        if (value != null) {
            size--;
        }
        return value;
        // throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        if (buckets[index] == null) {
            return null;  // 如果桶为空，返回 null
        }
        V currentValue = buckets[index].get(key);
        if (currentValue != null && currentValue.equals(value)) {
            return remove(key);  // 如果值匹配，调用 remove 方法
        }
        return null;  // 否则返回 null
        // throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int bucketIndex = 0;  // 当前桶索引
            private Iterator<K> currentIterator = getNextIterator();  // 当前桶的迭代器

            private Iterator<K> getNextIterator() {
                while (bucketIndex < buckets.length) {
                    if (buckets[bucketIndex] != null) {
                        return buckets[bucketIndex].keySet().iterator();  // 返回当前桶的键迭代器
                    }
                    bucketIndex++;
                }
                return null;  // 如果没有更多桶，返回 null
            }

            @Override
            public boolean hasNext() {
                while (currentIterator == null || !currentIterator.hasNext()) {
                    bucketIndex++;
                    currentIterator = getNextIterator();  // 获取下一个桶的迭代器
                }
                return currentIterator != null && currentIterator.hasNext();
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();  // 如果没有下一个元素，抛出异常
                }
                return currentIterator.next();  // 返回下一个元素
            }
        };
        // throw new UnsupportedOperationException();
    }
}
