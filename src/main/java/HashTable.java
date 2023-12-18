import java.util.Iterator;
import java.util.LinkedList;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75d;
    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

//Create hash table
    public HashTable() {
        this.capacity = INITIAL_CAPACITY;
        this.slots = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            slots[i] = new LinkedList<>();
        }
    }

//Create hash table with capacity
    public HashTable(int capacity) {
        this.capacity = capacity;
        this.slots = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            slots[i] = new LinkedList<>();
        }
    }

//Получить коэффициент заполнения
    public double getLoadFactor() {
        return (double) (this.size() + 1) / capacity;
    }

//Подсчитать количество коллизий
    public int countCollisions() {
        int collisionsCount = 0;
        for (LinkedList<KeyValue<K, V>> bucket : slots) {
            if (bucket.size() > 1) {
                collisionsCount += bucket.size() - 1;
            }
        }
        return collisionsCount;
    }

//Добавить элемент. При коллизии используется метод цепочек
    public void add(K key, V value) {
        int slot = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> bucket = slots[slot];
        for (KeyValue<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        bucket.add(new KeyValue<>(key, value));
        count++;
        growIfNeeded();
    }

//Определяет индекс слота(бакета) для заданного ключа
    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.slots.length;
    }

//Check if there is need to grow
    private void growIfNeeded() {
        if ((double) (this.size() + 1) / this.capacity() > LOAD_FACTOR) {
            this.grow();
        }
    }

//Grow
    private void grow() {
        int newCapacity = this.capacity * 2;
        LinkedList<KeyValue<K, V>>[] newSlots = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newSlots[i] = new LinkedList<>();
        }
        for (LinkedList<KeyValue<K, V>> bucket : slots) {
            for (KeyValue<K, V> entry : bucket) {
                int slot = Math.abs(entry.getKey().hashCode()) % newCapacity;
                newSlots[slot].add(entry);
            }
        }
        this.slots = newSlots;
        this.capacity = newCapacity;
    }

//Return amount of elements in hash table
    public int size() {
        return count;
    }

//Return capacity of hash table
    public int capacity() {
        return capacity;
    }

//Add element if there is none, replace value if key alreade exists
    public boolean addOrReplace(K key, V value) {
        int slot = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> bucket = slots[slot];
        for (KeyValue<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return false;
            }
        }
        bucket.add(new KeyValue<>(key, value));
        count++;
        growIfNeeded();
        return true;
    }

//Return value for this key
    public V get(K key) {
        int slot = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> bucket = slots[slot];
        for (KeyValue<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

//Return key-value object for this key
    public KeyValue<K, V> find(K key) {
        int slot = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> bucket = slots[slot];
        for (KeyValue<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

//Check if there is this key in hash table
    public boolean containsKey(K key) {
        int slot = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> bucket = slots[slot];
        for (KeyValue<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

//Delete element with this key
    public boolean remove(K key) {
        int slot = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> bucket = slots[slot];
        Iterator<KeyValue<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            KeyValue<K, V> entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                count--;
                return true;
            }
        }
        return false;
    }

//Clear hash table from all elements
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            slots[i].clear();
        }
        count = 0;
    }

//Returns iterator for all keys
    public Iterable<K> keys() {
        LinkedList<K> keys = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> bucket : slots) {
            for (KeyValue<K, V> entry : bucket) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

//Returns iterator for all values
    public Iterable<V> values() {
        LinkedList<V> values = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> bucket : slots) {
            for (KeyValue<K, V> entry : bucket) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

//Iterator
    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        LinkedList<KeyValue<K, V>> allEntries = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> bucket : slots) {
            allEntries.addAll(bucket);
        }
        return allEntries.iterator();
    }
}