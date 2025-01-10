package code;

public class Cell {

	private int number;
	private boolean[] potential = {false,true,true,true,true,true,true,true,true,true};
	private int boxID;
	
	
	public boolean canBe(int number) {
		
		if(potential[number])
		{
			return true;
		}
		else 
		{
			return false;
		}
		
	}
	
	public void cantBe(int number) {
		
		potential[number] = false;
		
	}
	
	public int numberOfPotentials() {
		
		int numberOfPotentials = 0;
		
		for(int x = 0; x < potential.length; x++)
		{
			if(potential[x])
			{
				numberOfPotentials++;
			}
		}
		
		return numberOfPotentials;
		
	}
	
	public int getFirstPotential() {
		
		int firstPotential = -1;
		
		for(int x = 0; x < potential.length; x++)
		{
			
			if(potential[x])
			{
				firstPotential = x;
				break;
			}
		}
		return firstPotential;
		
	}
	
	public int getSecondPotential()
	{
		int secondPotential = -1;
		boolean foundFirst = false;
		
		for(int x = 0; x < potential.length; x++)
		{
			if(potential[x])
			{
				foundFirst = true;
			}
			if(potential[x] && foundFirst)
			{
				secondPotential = x;
				break;
			}
		}
		
		return secondPotential;
	}
	
	public int getNumber() {
		
		return number;
	}
	
	public void setNumber(int number) {
		
		this.number = number;
		
		for(int x = 0; x < potential.length; x++)
		{
			potential[x] = false;
		}
		
		potential[number] = true;
		
	}
	
	public boolean[] getPotential() {
		return potential;
	}
	
	public void setPotential(boolean[] potential) {
		this.potential = potential;
	}
	
	public int getBoxID() {
		return boxID;
	}
	
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	
	
}
