import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

	private Node firstNode = null;
	private Node lastNode = null;
	private int size = 0;
	
	private class Node {
		Item item;
		Node next = null;
		Node prev = null;
		
		Node (Item item) {
			this.item = item;
		}
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new iterator();
	}
	
	private class iterator implements Iterator<Item>{
		Node current = firstNode;

		@Override
		public boolean hasNext() {
			return current != null;
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

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove operation is not supported");
		}
	}
	
	public Deque() {
		
	}
	
	public boolean isEmpty() {
		
		return firstNode == null && lastNode == null;
	}
	
	public int size() {
		
		return size;
	}
	
	// to add an element to the front of the dequeue
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Null values are not allowed to input");
		}
		
		Node newNode = new Node(item);
		
		if (isEmpty()) {
			firstNode = newNode;
			lastNode = newNode;
		} else {
			newNode.next = firstNode;
			firstNode.prev = newNode;
			firstNode = newNode;
		}
		size++;
	}
	
	// to add an element to the end of the dequeue
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Null values are not allowed to input");
		}

		Node newNode = new Node(item);
		
		if (isEmpty()) {
			firstNode = newNode;
			lastNode = newNode;
		} else {
			newNode.prev = lastNode;
			lastNode.next = newNode;
			lastNode = newNode;
		}
		size++;
	}
	
	// to remove the first element of the dequeue
	public Item removeFirst() {
		Item res;
		if (isEmpty()) {
			throw new NoSuchElementException("The queue is empty");
		} else if (firstNode == lastNode) {
			res = firstNode.item;
			firstNode = null;
			lastNode = null;
		} else {
			res = firstNode.item;
			firstNode = firstNode.next;
			firstNode.prev = null;
		}
		size--;
		return res;
	}
	
	// to remove the last element of the dequeue
	public Item removeLast() {
		Item res;
		if (isEmpty()) {
			throw new NoSuchElementException("The queue is empty");
		} else if (firstNode == lastNode) {
			res = firstNode.item;
			firstNode = null;
			lastNode = null;
		} else {
			res = lastNode.item;
			lastNode = lastNode.prev;
			lastNode.next = null;
		}
		size--;
		return res;
	}
	
	// unit testing	
	public static void  main(String args[]) {
		
		StdOut.println("Dequeue created");
		Deque<Integer> dequeue = new Deque<>();
		
		StdOut.println("Is the dequeue is empty: " + dequeue.isEmpty());
		StdOut.println("Size of the dequeue: " + dequeue.size());
		StdOut.println("Adding 2 to the front of the dequeue..");
		dequeue.addFirst(2);
		StdOut.println("Is the dequeue empty: " + dequeue.isEmpty());
		StdOut.println("Size of the dequeue: " + dequeue.size());
		StdOut.print("The dequeue is : ");
		for (int d : dequeue) {
			StdOut.print(d + " ");
		}
		StdOut.println();
		StdOut.println("Removing the last element of the deqeue: ");
		dequeue.removeLast();
		StdOut.println("Is the dequeue empty: " + dequeue.isEmpty());
		StdOut.println("Size of the dequeue: " + dequeue.size());
		StdOut.println("Adding 3 to the front of the dequeue..");
		dequeue.addFirst(3);
		StdOut.println("Adding 4 to the end of the dequeue..");
		dequeue.addLast(4);
		StdOut.println("Adding 2 to the front of the dequeue..");
		dequeue.addFirst(2);
		StdOut.println("Adding 1 to the front of the dequeue..");
		dequeue.addFirst(1);
		StdOut.println("Adding 5 to the end of the dequeue..");
		dequeue.addLast(5);
		StdOut.print("The dequeue is : ");
		for (int d : dequeue) {
			StdOut.print(d + " ");
		}
		StdOut.println();
		StdOut.println("Is the dequeue is empty: " + dequeue.isEmpty());
		StdOut.println("Size of the dequeue: " + dequeue.size());
		StdOut.println("Removing the last element of the dequeue " + dequeue.removeLast());
		StdOut.println("size of the dequeue: " + dequeue.size());
		StdOut.println("Removing the first element of the dequeue " + dequeue.removeFirst());
		StdOut.println("size of the dequeue: " + dequeue.size());
		StdOut.print("The dequeue is : ");
		for (int d : dequeue) {
			StdOut.print(d + " ");
		}
		StdOut.println();
		StdOut.println("Removing the last element of the dequeue " + dequeue.removeLast());
		StdOut.println("size of the dequeue: " + dequeue.size());
		StdOut.println("Removing the first element of the dequeue " + dequeue.removeFirst());
		StdOut.println("size of the dequeue: " + dequeue.size());
		StdOut.println("Removing the last element of the dequeue " + dequeue.removeLast());
		StdOut.println("size of the dequeue: " + dequeue.size());
	}
}