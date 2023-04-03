package notebookdoodle;
import java.lang.Math.*;

public class Animal
{
	private String name = "You shouldn't be seeing this - Animal";
	private int age = 0;
	private boolean canGetPregnant = false;
	
	public Animal() {
		name = "Danny Default";
		age = 10;
		canGetPregnant = false;
	}
	
	public Animal(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String toString() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public boolean canGetPregnant() {
		return canGetPregnant;
	}
	
	// randomisers
	// age
	public void randomiseAge(int min, int max) {
		if (min > max) {
			Test.debugPrint("Wrong input in Animal randomiseAge");
			return;
		}
		age = (int)(Math.random() * (max - min) + min);
	}
	// name
	// could add parameter for name length? meh..
	public void randomiseName( ) {
		int nameLength = (int)(Math.random()*6+1); // (of lowercase letters only)
		name = name + (char)(Math.random()*26 + 65);
		for (int i = 0; i < nameLength; i++) {
			name = name + (char)(Math.random()*26 + 97);
		}
	}
	// gender
	public void randomiseGender() {
		canGetPregnant = (Math.random()*2 >= 1.0);
	}
	
	public void randomise() {
		randomiseAge(0, 10);
		randomiseName();
		randomiseGender();
	}
	
	public void setAge(int newAge) {
		age = newAge;
	}
}