import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.List;
import java.util.ArrayList;


public class Board {
    
    private final int[][] blocks;
    private Board twin;
    private final int n;
    private int manhattan = 0;
    private int hamming = 0;
    private int emptyPos;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) throw new java.lang.IllegalArgumentException();
        n = blocks.length;
        this.blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                
                if (this.blocks[i][j] == 0) {
                    emptyPos = (i * n) + j;
                }
                if (this.blocks[i][j] != 0 && this.blocks[i][j] != ((i * n) + j + 1)) {
                    int goalR = (this.blocks[i][j] - 1) / n;
                    int goalC = (this.blocks[i][j] - 1) % n;
                    manhattan = manhattan + Math.abs(goalR - i) + Math.abs(goalC - j);
                    hamming++;
                }
            }
        }
    }           
    

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place                
    public int hamming() {
        return hamming;
    }                   
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }                 
    
    // is this board the goal board?
    public boolean isGoal() {
        return (hamming == 0);
    }               
    
    // // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (twin == null) twin = createTwin();
        return twin;

    }                   
    
    private Board createTwin() {
        int r1 = StdRandom.uniform(n);
        int c1 = StdRandom.uniform(n);
        int r2 = StdRandom.uniform(n);
        int c2 = StdRandom.uniform(n);
        int row = emptyPos / n;
        int col = emptyPos % n;

        while ((row == r1 && col == c1)|| (row == r2 && col == c2) || (r1 == r2 && c1 == c2)) {
            r1 = StdRandom.uniform(n);
            c1 = StdRandom.uniform(n);
            r2 = StdRandom.uniform(n);
            c2 = StdRandom.uniform(n);
        }

        int[][] twinBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        }
        twinBlocks[r1][c1] = blocks[r2][c2]; 
        twinBlocks[r2][c2] = blocks[r1][c1];

        return new Board(twinBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board b = (Board) y;
        return this.blocks.equals(b.blocks);
    }       
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row = emptyPos / n;
        int col = emptyPos % n;
        List<Board> list = new ArrayList<Board>();

        if (row > 0) list.add(neighbor(row, col, row - 1, col));
        if (row < n - 1) list.add(neighbor(row, col, row + 1, col));
        if (col < n - 1) list.add(neighbor(row, col, row, col + 1));
        if (col > 0) list.add(neighbor(row, col, row, col - 1));

        return list;
        
    }     
    
    private Board neighbor(int row, int col, int nRow, int nCol) {
        if (nRow < 0 || nRow >= n || nCol < 0 || nCol >= n) return null;
        
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = blocks[i][j];
            }
        }
        array[nRow][nCol] = blocks[row][col]; 
        array[row][col] = blocks[nRow][nCol];

        return new Board(array);
    }


    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder out = new StringBuilder(n*n);
        out.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.append(blocks[i][j] + " ");
            }
            out.append("\n");
        }
        return out.toString();
    } 

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.twin());
        
        StdOut.println(initial.manhattan());
        
        for (Board b: initial.neighbors()) {
            StdOut.println(b);
            // StdOut.println(b.manhattan());
        }
    } 
}