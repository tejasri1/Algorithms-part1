/****************************************************

Author: Thota Tejasri
Date: Dec 4, 2018
Possible Improvements: 
1. removeLast() method is not linear.
2. Try with arrayResizing implementation.

****************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

   private Node first;
   private Node last;
   private int size;

   private class Node {
       Item data;
       Node next;

        Node(Item item) {
            this.data = item;
        }
   }

   // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

   // is the deque empty?
   public boolean isEmpty() {
        return (size == 0);
   }

   // return the number of items on the deque
   public int size() {
        return size;
   }

   // add the item to the front
   public void addFirst(Item item) {
        if(item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node newFirst = new Node(item);
        newFirst.next = first;
        first = newFirst;
        if(isEmpty()) last = first;
        size++;
   }

   // add the item to the end
   public void addLast(Item item) {
        if (item == null) {
           throw new java.lang.IllegalArgumentException();
        }
        Node newLast = new Node(item);
        if (isEmpty()) {
          first = newLast;
        } else {
          last.next = newLast;
        }
        last = newLast;
        size++;
   }

   // remove and return the item from the front
   public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.data;
        first = first.next;
        size--;
        if(isEmpty()) last = null;
        return item;
   }

   // remove and return the item from the end
   public Item removeLast() {
        if (isEmpty()) {
           throw new java.util.NoSuchElementException();
        }
        Item item  = last.data;

        Node newLast = first;
        if (newLast.next != null) {
            while (newLast.next.next != null) {
                newLast = newLast.next;
            }
            last = newLast;
            last.next = null;

        } else {
          first = null;
          last = null;
        }        

        size--;
        return item;
   }

   // return an iterator over items in order from front to end
   public Iterator<Item> iterator() {
        return new ListIterator();
   }

   private class ListIterator implements Iterator<Item> {
        private Node curr = first;
        public boolean hasNext() {
            return (curr != null);
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = curr.data;
            curr = curr.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
   }

   // unit testing (optional)
   public static void main(String[] args) {
         Deque<String> deque = new Deque<String>();
         deque.addFirst("NWTLKWMJZZ");
         
         System.out.println(deque.removeLast());
         
   }


}
