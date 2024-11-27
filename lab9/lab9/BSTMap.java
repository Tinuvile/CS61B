package lab9;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author 张竹和
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
        // throw new UnsupportedOperationException();
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
        // throw new UnsupportedOperationException();
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }

        return p;
        // throw new UnsupportedOperationException();
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size++;
        // throw new UnsupportedOperationException();
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
        fillKeySet(root, keys);
        return keys;
        // throw new UnsupportedOperationException();
    }

    private void fillKeySet(Node p, Set<K> keys) {
        if (p != null) {
            fillKeySet(p.left, keys);
            keys.add(p.key);
            fillKeySet(p.right, keys);
        }
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        V[] removedValue = (V[]) new Object[1];  // 使用数组来保存被移除的值
        root = removeHelper(key, root, removedValue);  // 调用辅助方法
        return removedValue[0];  // 返回被移除的值
        // throw new UnsupportedOperationException();
    }

    /**
     * 辅助方法，递归地从以 p 为根的子树中移除 KEY。
     * 同时返回被移除的值。
     */
    private Node removeHelper(K key, Node p, V[] removedValue) {
        if (p == null) {
            return null;  // 找不到该键
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            // 在左子树中查找并删除
            p.left = removeHelper(key, p.left, removedValue);
        } else if (cmp > 0) {
            // 在右子树中查找并删除
            p.right = removeHelper(key, p.right, removedValue);
        } else {
            // 找到要删除的节点
            removedValue[0] = p.value;  // 保存被移除的值
            // 处理删除操作
            if (p.left == null) {
                return p.right;  // 只有右子树
            } else if (p.right == null) {
                return p.left;  // 只有左子树
            } else {
                // 有两个子树的情况
                Node successor = findMin(p.right);  // 找到右子树的最小节点
                p.key = successor.key;  // 用最小节点的键替换当前节点的键
                p.value = successor.value;  // 用最小节点的值替换当前节点的值
                p.right = removeHelper(successor.key, p.right, removedValue);  // 删除最小节点
            }
        }
        return p;  // 返回当前节点
    }

    /**
     * 辅助方法，找到以 p 为根的子树中的最小节点。
     */
    private Node findMin(Node p) {
        while (p.left != null) {
            p = p.left;  // 一直向左查找
        }
        return p;  // 返回最小节点
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (containsKey(key) && get(key).equals(value)) {
            return remove(key);  // 如果键存在且值匹配，调用单一的 remove 方法
        }
        return null;  // 否则返回 null
        // throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private Stack<Node> stack = new Stack<>();  // 使用栈来模拟递归
            private Node current = root;

            { // 初始化时将左子树的所有节点推入栈
                pushLeft(current);
            }

            private void pushLeft(Node node) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;  // 一直向左推
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();  // 栈不为空时还有下一个元素
            }

            @Override
            public K next() {
                Node node = stack.pop();  // 弹出栈顶元素
                pushLeft(node.right);  // 将右子树的所有左节点推入栈
                return node.key;  // 返回当前节点的键
            }
        };
        // throw new UnsupportedOperationException();
    }
}
