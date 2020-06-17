import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Node first = null;
	private Node last = null;
	private int size = 0;
	
	
	private class Node {
		Item item;
		Node next = null;
		Node prev = null;
		
		Node (Item item) {
			this.item = item;
		}
	}
	
	private class iterator implements Iterator<Item> {
		Node current = first;

		@Override
		public boolean hasNext() {
			return current  != null;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Item next() {
			if(current == null) {
				throw new NoSuchElementException();
			}
			Item res = current.item;
			current = current.next;
			
			return res;
		}
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new iterator();
	}
	
	// construct an empty randomized queue
	public RandomizedQueue() {
		
	}
	
	// is the randomized queue empty?
	public boolean isEmpty() {
		
		return (first == null && last == null);
	}
	
	// return the number of items on the randomised queue
	public int size() {
		
		return size;
	}
	
	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		
		Node newNode = new Node(item);
		
		if (isEmpty()) {
			first  = newNode;
			last = newNode;
		} else {
			newNode.prev = last;
			last.next = newNode;
			last = last.next;
		}
		size++;
	}
	
	// remove and return a random item
	public Item dequeue() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		
		Item res;
		
		// to generate a random number in the length of the node
		int rand = StdRandom.uniform(size) + 1;
		
		if (rand == 1) {
			// remove the first node and return the result
			res = first.item;
			if (size == 1) {
				first = null;
				last = null;
			} else {
				first = first.next;
				first.prev = null;
			}
		} else if (rand == size) {
			// remove the last node and return the result
			res = last.item;
			if (size == 1) {
				last = null;
			} else {
				last = last.prev;
				last.next = null;
			}
		} else {
			// find the respective node and return the result
			int count = 1;
			Node current = first;
			
			while (count != rand) {
				current = current.next;
				count++;
			}
			
			res = current.item;
			current.prev.next  = current.next;
			current.next.prev = current.prev;
		}
		size--;
		return res;
	}
	
	// return a random item (but do not remove it)
	public Item sample() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		
		int rand = StdRandom.uniform(size) + 1;
		Node current = first;
		
		int count = 1;
		while (count != rand) {
			current = current.next;
			count++;
		}
		
		return current.item;
	}
	
	// unit testing(required)
	public static void main(String args[]) {
		
		StdOut.println("Creating an empty randomized queue..");
		RandomizedQueue<Integer> queue = new RandomizedQueue<>();
		
		StdOut.println("Size of the queue: " + queue.size());
		StdOut.println("Is the queue is empty: " + queue.isEmpty());
		StdOut.println("Adding 1 to the queue: ");
		queue.enqueue(1);
		printQueue(queue);
		StdOut.println("Adding 2 to the queue: ");
		queue.enqueue(2);
		printQueue(queue);
		StdOut.println("Adding 3 to the queue: ");
		queue.enqueue(3);
		printQueue(queue);
		StdOut.println("Adding 4 to the queue: ");
		queue.enqueue(4);
		printQueue(queue);
		StdOut.println("Adding 5 to the queue: ");
		queue.enqueue(5);
		printQueue(queue);
		StdOut.println("Sample element from the queue: " + queue.sample());
		printQueue(queue);
		StdOut.println("Removing an element from the queue: " + queue.dequeue());
		printQueue(queue);
		StdOut.println("Removing an element from the queue: " + queue.dequeue());
		printQueue(queue);
		StdOut.println("Removing an element from the queue: " + queue.dequeue());
		printQueue(queue);
		StdOut.println("Removing an element from the queue: " + queue.dequeue());
		printQueue(queue);
		StdOut.println("Removing an element from the queue: " + queue.dequeue());
		printQueue(queue);
		
		// gives us no such element exception
		StdOut.println(queue.dequeue());
	}
	
	private static void printQueue(RandomizedQueue<Integer> queue) {
		StdOut.print("The qeueue elements are: ");
		for (Integer d : queue) {
			StdOut.print(d + " ");
		}
		StdOut.println();
		StdOut.println("Size of the queue: " + queue.size());
		StdOut.println("Is the queue is empty: " + queue.isEmpty());
		StdOut.println();
	}
}