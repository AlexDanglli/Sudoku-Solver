package code;

import java.io.FileNotFoundException;

public class Sudokusolver {

	public static void main(String[] args) throws FileNotFoundException {
		
		Board puzzle = new Board();
		puzzle.loadPuzzle("easy.txt");
		puzzle.display();
		puzzle.logicCycles();
		puzzle.display();
		System.out.println(puzzle.errorFound());
		System.out.println(puzzle.isSolved());

	}

}
