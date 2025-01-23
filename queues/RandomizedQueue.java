import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue(){
        this.items = (Item[]) new Object[2];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }
    
    private boolean isFull(){
        return size == this.items.length;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }    
    
    private void resize(int newCapacity){
        Item[] newItems = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null) throw new IllegalArgumentException(" null argument when enqueue");
        if(isFull()){
            resize(items.length * 2);
        }
        this.items[this.size++] = item;
    }
    


    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        int randomIndex = StdRandom.uniformInt(0, size);
        Item item = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = null; // avoid loitering
        size--;

        // Shrink the array if necessary
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException("queue is empty");

        int randomIndex = StdRandom.uniformInt(0, size);
        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randomizedItems;
        private int current;

        public RandomizedQueueIterator() {
            randomizedItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                randomizedItems[i] = items[i];
            }
            StdRandom.shuffle(randomizedItems); // Shuffle to ensure random order
            current = 0;
        }


        @Override
        public boolean hasNext(){
            return current < randomizedItems.length;
        }

        @Override
        public Item next(){
            if(!hasNext()) throw new NoSuchElementException("No more items");
            return randomizedItems[current++];
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException("Remove function is not supported");
        }
    }

    // Unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        System.out.println("Sample: " + rq.sample());
        System.out.println("Dequeue: " + rq.dequeue());
        System.out.println("Size: " + rq.size());

        System.out.println("Iterating over elements:");
        for (int item : rq) {
            System.out.println(item);
        }
    }

}