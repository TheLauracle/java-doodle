package notebookdoodle;


public class Dog extends Animal
{
	public Dog() {
		super();
	}
	
	public Dog(String name, int age) {
		super(name, age);
	}
	
	public String toString() {
		String toReturn = super.toString() + " (Dog, " + super.getAge();
		if (super.canGetPregnant())
			toReturn = toReturn + "F)";
		else
			toReturn = toReturn + "M)";
		return toReturn;
	}
	
	public void randomise() {
		super.randomiseAge(0, 20); // dog lifespan?
		super.randomiseName();
		super.randomiseGender();
	}
}