public class LinkedListDeque<T> {
    //    创建链表的节点单元
    private class Node {
        T item;       //链表中的元素
        Node prev;    //指向前一个节点的引用
        Node next;    //指向后一个节点的引用

        //构造函数，初始化节点的item属性
        Node(T item) {
            this.item = item;
        }
    }

    private Node sentinel;  //哨兵节点，用作链表的头尾
    private int size;       //更新链表的大小

    //    构造函数，创建一个空的双向链表
    public LinkedListDeque() {
        sentinel = new Node(null);  //创建一个哨兵节点，初始item为空
        sentinel.prev = sentinel;        //将前后均指向自身，表示队列为空
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node addFirst = new Node(item);
        addFirst.prev = sentinel;
        addFirst.next = sentinel.next;
        sentinel.next = addFirst;
        sentinel.next.prev = addFirst;
        size++;
    }

    public void addLast(T item) {
        Node addLast = new Node(item);
        addLast.prev = sentinel.prev;
        addLast.next = sentinel;
        sentinel.prev = addLast;
        sentinel.prev.next = addLast;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item);
            current = current.next;
            if (current != sentinel) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node firstNode = sentinel.next;
            sentinel.next = firstNode.next;
            firstNode.next.prev = sentinel;
            size--;
            return firstNode.item;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            Node lastNode = sentinel.prev;
            sentinel.prev = lastNode.prev;
            lastNode.prev.next = sentinel;
            size--;
            return lastNode.item;
        }
    }

    //      使用迭代
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    //    使用递归
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

}
