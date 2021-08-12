import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final Node start;
    private final Node end;
    private int length;

    private class Node {
        Item value;
        Node prev;
        Node next;

        public Node(Item value) {
            this.value = value;
        }

        public Node() {
        }
    }

    // construct an empty deque
    public Deque() {
        this.start = new Node();
        this.end = new Node();

        this.start.next = this.end;
        this.end.prev = this.start;
        this.length = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node s = new Node(item);
        s.prev = start;
        s.next = start.next;
        start.next.prev = s;
        start.next = s;
        length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node s = new Node(item);
        s.next = end;
        s.prev = end.prev;
        end.prev.next = s;
        end.prev = s;
        length++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node s = start.next;
        s.next.prev = start;
        start.next = s.next;
        length--;

        return s.value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node s = end.prev;
        s.prev.next = end;
        end.prev = s.prev;
        length--;

        return s.value;
    }

    private class DQIter implements Iterator<Item> {
        Node curr = start.next;

        @Override
        public boolean hasNext() {
            return curr != end;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item ret = curr.value;
            curr = curr.next;
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DQIter();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        int[] addLast = {1, 1, 4, 5, 1, 4};
        int[] removeLast = new int[addLast.length];
        for (int n : addLast) {
            deque.addLast(n);
        }
        for (int i = 0; i < addLast.length; i++) {
            removeLast[i] = deque.removeLast();
            assert removeLast[i] == addLast[i];
        }
        assert deque.isEmpty();
        System.out.println("remove/add Last works fine!");

        int[] addFirst = {1, 1, 4, 5, 1, 4};
        int[] removeFirst = new int[addFirst.length];
        for (int n : addFirst) {
            deque.addFirst(n);
        }
        for (int i = 0; i < addFirst.length; i++) {
            removeFirst[i] = deque.removeFirst();
            assert removeFirst[i] == addFirst[i];
        }
        assert deque.isEmpty();
        System.out.println("remove/add First works fine!");

        int[] elems = {1, 1, 4, 5, 1, 4};
        for (int elem : elems) {
            deque.addLast(elem);
        }
        assert deque.size() == elems.length;
        System.out.println("size works fine!");

        int i = 0;
        for (int elem : deque) {
            assert elem == elems[i];
            i++;
        }
        System.out.println("iterator works fine!");
        System.out.println("Done!");
    }
}
