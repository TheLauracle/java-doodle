package notebookdoodle;

public class Animal
{
	private String name = "You shouldn't be seeing this - Animal";
	private int age = 0;
	
	public Animal() {
		name = "Danny Default";
		age = 10;
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
	
	public void setAge(int newAge) {
		age = newAge;
	}
}