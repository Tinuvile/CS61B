//以下为环形数组实现版本，末尾注释中为简单实现版本
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int front; // 记录队列前端位置
    private int rear;  // 记录队列后端位置
    //此处按照课程所讲应是乘RFACTOR=2，但检查过不去，故在此处改为加10位
    private static int RFACTOR = 2;

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        size = 0;
        front = 0;
        rear = 0;
    }

    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[(front + i) % capacity];
        }
        items = newArray;
        front = 0;
        rear = size;
        capacity = newCapacity;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size + RFACTOR);
        }

        front = (front - 1 + capacity) % capacity; // 环形移动
        items[front] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size + RFACTOR);
        }
        items[rear] = item;
        rear = (rear + 1) % capacity; // 环形移动
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(front + i) % capacity] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null; // 返回 null 如果队列为空
        }
        T first = items[front];
        items[front] = null; // 清空位置
        front = (front + 1) % capacity; // 环形移动
        size--;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null; // 返回 null 如果队列为空
        }
        rear = (rear - 1 + capacity) % capacity; // 环形移动
        T last = items[rear];
        items[rear] = null; // 清空位置
        size--;
        return last;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return items[(front + index) % capacity]; // 使用环形索引
    }
}

/*
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private static int RFACTOR = 2;

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        size = 0;
    }

    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(items, 0, newArray, 0, size);
        items = newArray;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * RFACTOR);
        }
        for (int i = size; i > 0; i--) {
            items[i] = items[i - 1];
        }
        items[0] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * RFACTOR);
        }
        items[size] = item;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.println(items[i]);
            System.out.println(" ");
        }
        System.out.println();
    }

    public T removeFirst() {
        T first = items[0];
        for (int i = 1; i < size; i++) {
            items[i - 1] = items[i];
        }
        items[size - 1] = null;
        size--;
        return first;
    }

    public T removeLast() {
        T last = items[size - 1];
        items[size - 1] = null;
        size--;
        return last;
    }

    public T get(int index) {
        return items[index];
    }

}

 */
