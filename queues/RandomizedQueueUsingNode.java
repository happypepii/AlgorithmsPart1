import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueueUsingNode<Item> implements Iterable<Item> {
    private Node headPointer;
    private int size;

    private class Node {
        private Item data;
        private Node next;
        private Node previous;

        public Node(Item data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        public Item getData() {
            return this.data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getPrevious() {
            return this.previous;
        }
    }

    // Construct an empty randomized queue
    public RandomizedQueueUsingNode() {
        this.headPointer = null;
        this.size = 0;
    }

    // Is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // Add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Data added cannot be null");
        
        Node newNode = new Node(item);
        if (isEmpty()) {
            this.headPointer = newNode;
        } else {
            newNode.setNext(this.headPointer);
            this.headPointer.setPrevious(newNode);
            this.headPointer = newNode;
        }
        size++;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("No item to be removed");

        int randomIndex = StdRandom.uniformInt(0, size);
        Node currentPointer = this.headPointer;

        // Traverse to the node at randomIndex
        for (int i = 0; i < randomIndex; i++) {
            currentPointer = currentPointer.getNext();
        }

        if (currentPointer.getPrevious() != null) {
            currentPointer.getPrevious().setNext(currentPointer.getNext());
        } else {
            this.headPointer = currentPointer.getNext();
        }

        if (currentPointer.getNext() != null) {
            currentPointer.getNext().setPrevious(currentPointer.getPrevious());
        }

        size--;
        return currentPointer.getData();
    }

    // Return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("No item to be sampled");

        int randomIndex = StdRandom.uniformInt(0, size);
        Node currentPointer = this.headPointer;

        // Traverse to the node at randomIndex
        for (int i = 0; i < randomIndex; i++) {
            currentPointer = currentPointer.getNext();
        }

        return currentPointer.getData();
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] randomizedItems;
        private int currentIndex;

        public RandomizedQueueIterator() {
            randomizedItems = (Item[]) new Object[size];
            Node current = headPointer;

            // Copy elements to the array
            int index = 0;
            while (current != null) {
                randomizedItems[index++] = current.getData();
                current = current.getNext();
            }

            // Shuffle the array
            StdRandom.shuffle(randomizedItems);
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < randomizedItems.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items");
            return randomizedItems[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove function is not supported");
        }
    }

    // Unit testing (required)
    public static void main(String[] args) {
        RandomizedQueueUsingNode<Integer> queue = new RandomizedQueueUsingNode<>();

        // Test enqueue
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Queue size after enqueues: " + queue.size()); // Should print 3

        // Test dequeue
        System.out.println("Dequeued item: " + queue.dequeue());
        System.out.println("Queue size after dequeue: " + queue.size()); // Should print 2

        // Test sample
        System.out.println("Sampled item: " + queue.sample());
        System.out.println("Queue size after sample: " + queue.size()); // Should still print 2

        // Test iterator
        System.out.println("Items in random order:");
        for (int item : queue) {
            System.out.println(item);
        }
    }
}
