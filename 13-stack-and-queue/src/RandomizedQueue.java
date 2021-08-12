import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int length;
    private int capacity = 1;

    public RandomizedQueue() {
        this.items = (Item[]) new Object[capacity];
        this.length = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    private void resize() {
        capacity *= 2;
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (length == capacity) {
            resize();
        }
        items[length++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(length);
        Item ret = items[index];
        items[index] = items[--length];
        items[length] = null;
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(length)];
    }

    private class RQueueIter implements Iterator<Item> {
        private int curr = 0;
        private final Item[] iterItems;

        RQueueIter() {
            iterItems = (Item[]) new Object[length];
            for (int i = 0; i < length; i++) {
                iterItems[i] = items[i];
            }
            StdRandom.shuffle(iterItems);
        }

        @Override
        public boolean hasNext() {
            return curr < iterItems.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iterItems[curr++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQueueIter();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> que = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            que.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(que.dequeue());
        }

        assert que.isEmpty();

        System.out.println("size: " + que.size());

        for (int i = 0; i < 10; i++) {
            que.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(que.sample());
        }

        for (int n : que) {
            System.out.println(n);
        }
    }
}
