import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;


public class Solver {
	private int moves;
	private List<Board> solution;
	private final boolean isSolvable;

	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) {
    		throw new java.lang.IllegalArgumentException();
    	}
    	moves = 0;
    	solution = new ArrayList<Board>();

    	Data data = new Data(initial, moves, null);
    	Data twinData = new Data(initial.twin(), moves, null);
    	
    	MinPQ<Data> pq = new MinPQ<Data>(new DataPriority());
    	pq.insert(data);

    	MinPQ<Data> pqTwin = new MinPQ<Data>(new DataPriority());
    	pqTwin.insert(twinData);

    	while (true) {
    		data = populatePriorityQueue(data, pq, data.moves);
    		twinData = populatePriorityQueue(twinData, pqTwin, twinData.moves);
    		
    		if (data.board.isGoal() || twinData.board.isGoal()) {
    			break;
    		}
    	}    	
    	
    	isSolvable = (data.board.isGoal()) ? true : false;
    	if (isSolvable) {
    		moves = data.moves;
    		fillSol(data);
    	} else {
    		moves = -1;
    		solution = null;
    	}
    }

    private void fillSol(Data data) {
    	int count = moves + 1;
    	while (count-- > 0) {
    		solution.add(0, data.board);
    		data = data.pred;
    	}
    }

    private Data populatePriorityQueue(Data data, MinPQ<Data> pq, int move) {    	
		for (Board b : data.board.neighbors()) {
			if (data.pred == null || 
				(data.pred != null && !b.equals(data.pred.board))) {
				pq.insert(new Data(b, move + 1, data));
			}
		}
    	return pq.delMin();
    }

    // is the initial board solvable?          
    public boolean isSolvable() {
    	return isSolvable;
    }           
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	return moves;
    }  

    // sequence of boards in a shortest solution; null if unsolvable                   
    public Iterable<Board> solution() {
    	return solution;
    }

    private class DataPriority implements Comparator<Data> {
        @Override
        public int compare(Data o1, Data o2) {	
        	if (o1.priority == o2.priority) {
        		return Integer.compare(-o1.moves, -o2.moves);
        	} else {
        		return Integer.compare(o1.priority, o2.priority);
        	}
        }
    }

    private class Data{
    	private Board board;
    	private int moves;
    	private int priority;
    	private Data pred;

    	Data (Board b, int moves, Data pred) {
    		this.pred = pred;
    		this.board = b;
    		this.moves = moves;
    		this.priority = b.manhattan() + moves;
    	}
    }         


    public static void main(String[] args) {// solve a slider puzzle (given below)
    	// create initial board from file
    	In in = new In(args[0]);
    	int n = in.readInt();
    	int[][] blocks = new int[n][n];
    	for (int i = 0; i < n; i++)
    	    for (int j = 0; j < n; j++)
    	        blocks[i][j] = in.readInt();
    	Board initial = new Board(blocks);
	
    	// solve the puzzle
    	Solver solver = new Solver(initial);
	
    	// print solution to standard output
    	if (!solver.isSolvable()) {
    	    StdOut.println("No solution possible");
    	} else {
    	    StdOut.println("Minimum number of moves = " + solver.moves());
    	    for (Board board : solver.solution())
    	        StdOut.println(board);
    	}
    }
    

}