/****************************************************

Author: Thota Tejasri
Date: Dec 4, 2018
Possible Improvements: 
1. Try creating Deque or RandomizedQueue with just 'k' random chosen elements

****************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			rq.enqueue(StdIn.readString());
		}
		Iterator<String> it = rq.iterator();
		while (k-- > 0) {
			StdOut.println(it.next());
		}
	}
}
