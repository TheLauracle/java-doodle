package notebookdoodle;


public class Cat extends Animal
{
	public Cat() {
		super(); // implicit but meh
	}
	
	public Cat(String name, int age) {
		super(name, age);
	}
	
	public String toString() {
		String toReturn = super.toString() + " (Cat, " + super.getAge();
		if (super.canGetPregnant())
			toReturn = toReturn + "F)";
		else
			toReturn = toReturn + "M)";
		return toReturn;
	}
	
	public void randomise() {
		super.randomiseAge(0, 14);
		super.randomiseName();
		super.randomiseGender();
	}
}