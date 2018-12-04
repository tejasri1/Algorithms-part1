/****************************************************

Author: Thota Tejasri
Date: Dec 4, 2018
Possible Improvements: 
1. Iteration logic can be improved. 
   Try without maintaing a visited array

****************************************************/


import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
   
   private Node first;
   private int size;

   private class Node {
       Item data;
       Node next;

        Node(Item item) {
            this.data = item;
        }
   }

   // construct an empty randomized queue
   public RandomizedQueue() {
   		first = null;
   }                
   // is the randomized queue empty?
   public boolean isEmpty() {
   		return (size == 0);
   }                
   
   // return the number of items on the randomized queue
   public int size() {
   		return size;
   }

   // add the item                        
   public void enqueue(Item item) {
   		if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node newFirst = new Node(item);
        newFirst.next = first;
        first = newFirst;
        size++;

   }

   // remove and return a random item           
   public Item dequeue() {
   		if (isEmpty()) {
   			throw new java.util.NoSuchElementException();
   		}
   		int index = StdRandom.uniform(size);
   		Item item  = null;
   		Node temp = first;
   		while (index > 1) {
   			temp = temp.next;
   			index--;
   		}
   		if (temp.next != null) {
   			item = temp.next.data;
   			temp.next = temp.next.next;
   		} else {
   			item = temp.data;
   			first = null;
   		}
   		
   		size--;
   		return item;
   }

   // return a random item (but do not remove it)                    
   public Item sample() {
   		if (isEmpty()) {
   			throw new java.util.NoSuchElementException();
   		}
   		int index = StdRandom.uniform(size);
   		Node temp = first;
   		while (index > 0) {
   			temp = temp.next;
   			index--;
   		}
   		return temp.data;
   }

   // return an independent iterator over items in random order                     
   public Iterator<Item> iterator() {
   		return new ListIterator();
   }

   private class ListIterator implements Iterator<Item> {
   		private boolean[] visited = new boolean[size];
   		private int visitedCount = 0;

        public boolean hasNext() {
            return (visitedCount != size);
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            
            visitedCount++;
   			
   			int index = StdRandom.uniform(size);
   			while (visited[index]) {
   				index = StdRandom.uniform(size);
   			}
   			
   			visited[index] = true;
   			Node temp = first;
   			while (index > 0) {
   				temp = temp.next;
   				index--;
   			}
   			return temp.data;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
   }

   // unit testing (optional)         
   public static void main(String[] args) {
   		RandomizedQueue<Integer> rq = new RandomizedQueue<>();
   		RandomizedQueue<String> rq2 = new RandomizedQueue<>();
   		rq.isEmpty();
         rq.enqueue(465);
         rq.enqueue(134);
         rq.dequeue();
         rq.dequeue();
         rq2.isEmpty();
         rq2.enqueue("465");
         rq2.enqueue("134");
         rq2.dequeue();
         rq2.dequeue();

   }  
}