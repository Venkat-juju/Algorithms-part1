import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {

	private final int[] board;
	private final int dimension;
	private final int len;
	private final int missingTile;
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	dimension = tiles.length;
    	board = new int[dimension * dimension];
    	len = dimension * dimension;
    	
    	int count = 0;
    	for(int i = 0; i < dimension; i++) {
    		for(int j = 0; j < dimension; j++) {
    			board[count] = tiles[i][j];
    			count++;
    		}
    	}
    	
    	missingTile = getMissingTile();
    }
    
    private int getMissingTile() {
    	int[] copy = Arrays.copyOf(board, board.length);
    	Arrays.sort(copy);
    	
    	for(int i = 0; i < len; i++) {
    		if(copy[i] != i) {
    			return i;
    		}
    	}
    	
    	return len;
    }
                                           
    // string representation of this board
    public String toString() {
    	StringBuilder res1 = new StringBuilder();
    	res1.append(dimension + "\n");
    	
    	for(int i = 0 ; i < len; i++) {
    		if((i + 1)  % dimension != 0) {
    			res1.append(board[i] + " ");
    		} else {
    			res1.append(board[i] + "\n");
    		}
    	}
    	
    	return res1.toString();
    }

    // board dimension n
    public int dimension() {
    	
    	return dimension;
    }

    // number of tiles out of place
    public int hamming() {
    	int hamming = 0;
    	
    	for(int i = 0; i < len; i++) {
    		if(board[i] != (i+1) && board[i] != 0) {
    			hamming++;
    		}
    	}
    	
    	return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int manhattan = 0;
    	
    	for(int i = 0; i < len; i++) {
    		if(board[i] != (i+1) && (i+1) != missingTile) {
    			int[] liesOn = twoDimensionalIndexOf(indexOf(i+1));
    			int[] currentPos = twoDimensionalIndexOf(i);
    			manhattan += Math.abs(liesOn[0] - currentPos[0]) + Math.abs(liesOn[1] - currentPos[1]);
    		}
    	}
    	
    	return manhattan;
    }
    
    private int indexOf(int key) {
    	for(int i = 0; i < len; i++) {
    		if(board[i] == key) {
    			return i;
    		}
    	}
    	
    	return 0;
    }
    
    private int[] twoDimensionalIndexOf(int key) {
    	int index[] = new int[2];
    	
    	index[0] = key / dimension;
    	index[1] = key % dimension;
    	
    	return index;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	for(int i = 0; i < len; i++) {
    		if(board[i] != (i+1) && board[i] != 0) {
    			return false;
    		}
    	}
    	return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	if(this == y) return true;
    	if(y == null) return false;
    	if(this.getClass() != y.getClass()) return false;
    	Board that = (Board) y;
    	if(this.dimension() != that.dimension()) return false;
    	
    	return Arrays.equals(this.board, that.board);
    }

    // returns the index of the array whose value is zero
    private int findblankcell() {
    	for(int i = 0; i < len; i++) {
    		if(board[i] == 0) {
    			return i;
    		}
    	}
    	return 0;
    }
    
    // returns a board by swapping the values of the two given arguments
    private Board neighbor(int blankcell, int swapcell) {
    	int[] neighbor = Arrays.copyOf(board, board.length);
    	neighbor[blankcell] = neighbor[swapcell];
    	neighbor[swapcell] = 0;
    	
    	int[][] neighborBoard = new int[dimension][dimension];
    	int count = 0;
    	for(int k = 0; k < dimension; k++) {
    		for(int l = 0; l < dimension; l++) {
    			neighborBoard[k][l] = neighbor[count++];
    		}
    	}
    	
    	return new Board(neighborBoard);
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
    	Stack<Board> stack = new Stack<>();
    	int blankcell = findblankcell();
    	
    	if (blankcell >= dimension) {
    		// StdOut.println("upperneighbor");
    		stack.push(neighbor(blankcell, blankcell - dimension));
    	}
    	if (blankcell % dimension != (dimension - 1)) {
    		// StdOut.println("rightneighbor");
    		stack.push(neighbor(blankcell, blankcell+1));
    	}
    	if (blankcell % dimension != 0) {
    		// StdOut.println("leftneighbor");
    		stack.push(neighbor(blankcell, blankcell-1));
    	}
    	if (blankcell < len - dimension) {
    		// StdOut.println("lowerneighbor");
    		stack.push(neighbor(blankcell, blankcell + dimension));
    	}
    	
    	return stack;
    	
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	
    	int[][] twin = new int[dimension][dimension];
    	int count = 0;
    	for(int k = 0; k < dimension; k++) {
    		for(int l = 0; l < dimension; l++) {
    			twin[k][l] = board[count++];
    		}
    	}
    	
    	for (int i = 0; i < dimension; i++) {
    		for(int j = 0; j < dimension - 1; j++) {
    			if(twin[i][j] != 0 && twin[i][j+1] != 0) {
    				int swap = twin[i][j];
    				twin[i][j] = twin[i][j+1];
    				twin[i][j+1] = swap;
    				
    				return new Board(twin);
    			}
    		}
    	}
    	
    	return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	int[][] tiles = {{3,0,4},{2,5,7},{1,6,8}};
    	Board board = new Board(tiles);
    	StdOut.println("The board:\n " +  board.toString());
    	StdOut.println("The hamming distance of the board: " +board.hamming());
    	StdOut.println("The missing tile: " + board.getMissingTile());
    	StdOut.println("The manhattan distance : " + board.manhattan());
    	StdOut.println("Twin: ");
    	StdOut.println(board.twin().toString());
    	StdOut.println("The neighbors of the board: ");
    	for(Board b : board.neighbors()) {
    		StdOut.println(b.toString());
    	}
    	
    	
    }
}
