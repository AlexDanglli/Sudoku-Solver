package code;

import java.io.FileNotFoundException;

public class Sudokusolver {

	public static void main(String[] args) throws FileNotFoundException {

    	 Cell potentialCheck = new Cell();
        boolean[] potentials = {false, true, false, true, false, false, false, false, false, false};
        potentialCheck.setPotential(potentials);

        int firstPotential = potentialCheck.getFirstPotential();
        int secondPotential = potentialCheck.getSecondPotential();

        if(firstPotential != 1 || secondPotential != 3)
        {
            throw new AssertionError("Unexpected potential positions: first=" + firstPotential + ", second=" + secondPotential);
        }

        System.out.println("Cell potential check passed: first=" + firstPotential + ", second=" + secondPotential);

        Board puzzle = new Board();
        puzzle.loadPuzzle("easy.txt");
		puzzle.display();
		puzzle.logicCycles();
		puzzle.display();
		System.out.println(puzzle.errorFound());
		System.out.println(puzzle.isSolved());

	}

}
