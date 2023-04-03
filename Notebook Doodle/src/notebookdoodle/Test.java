package notebookdoodle;
import java.util.Arrays; // general use; pet shelter game
import java.lang.Math.*; // general use; generate random animal
import java.util.Scanner; // keyboard input

public class Test
{
	public static int shelterSize = 5; // used in petShelter(), how many animals can fit in the shelter
	public static boolean DEBUG = true; // used in debugPrint(), false = mute debug statements
	
	public static void main (String[] args) {
		debugPrint("Main init");
		Scanner keyboard = new Scanner(System.in);		
		
		
		customPrint("Welcome!", true);
		printMainMenu();
		
		while (menuContinue(keyboard));
		
		customPrint("Exiting...", true);
		debugPrint("Implicit exit");
	}
	
	// customPrint - For now, just a convention. In case I do
	//   decide to use a GUI, I'll have everything ready to
	//   print to a text box instead :)
	// in: string to print, add newline yes/no
	// out: ***subject to change*** prints to console
	public static void customPrint(String toPrint, boolean addNl) {
		System.out.print(toPrint);
		if (addNl) System.out.print("\n");
	}
	public static void customPrint(String toPrint) {
		customPrint(toPrint, true);
	}
	
	// debugPrint - If global variable DEBUG, then print debug message
	//   always to console
	// in: string too print
	// out: prints to console; auto add newline
	public static void debugPrint(String toPrint) {
		if (DEBUG) System.out.println(toPrint);
	}
	
	// menuContinue - Main menu loop, this is the "true main"
	// in: keyboard object to read from
	// out: F = exit, T = continue
	public static boolean menuContinue(Scanner kb) {
		//debugPrint("Beginning menuContinue");
		String keyboardString = "";
		
		customPrint("Please enter an option: ", true);
		keyboardString = kb.nextLine();
		
		switch (keyboardString.charAt(0)) {
			case 'Q':
				return false;
			case 'M':
				printMainMenu();
				return true;
			case '1':
				petShelter(kb);
				return true;
			default:
				debugPrint("Character not recognized. Continuing loop");
				return true;
		}
		
		//return false;
	}
	
	// printMainMenu - Prints the main menu options. In a GUI
	//    this should probably have its own text area.
	// out: prints the menu to customPrint
	public static void printMainMenu() {
		customPrint("=== Menu: Enter the letter corresponding to your choice ===", true);
		customPrint("M: Print this menu", true);
		customPrint("1: Play pet shelter game", true);
		customPrint("Q: Exit", true);
	}
	
	// Function 1 - Pet shelter
	/* For now, I want to generate random shelter cats / dogs / (others?)
	 *  and let the user interact with them, maybe get them adopted / take in
	 *  new animals.
	 * If Java can interact with SQL in an easy way, maybe I could make
	 *  a more useful version for actual animal intake / display / scheduling?
	 */
	// in: keyboard object
	// outros before returning to menuContinue loop
	public static void petShelter(Scanner kb) {
		// debugPrint("petShelter init");
		
		// note: shelterSize global variable needs to be treated as a const here.
		// can't remember atm how to note that in the code itself.
		String petChoice = " "; // needs to be at least 1 long for the first while loop check
		Animal[] shelterAnimals = new Animal[shelterSize];
		for (int i = 0; i < shelterSize; i++) {
			shelterAnimals[i] = newRandomAnimal(); // fill shelterAnimals with random animals
		}
		
		customPrint("\n=== Welcome to the pet shelter! ===", true);
		customPrint("Current shelter population: ".concat(shelterSize+""));
		customPrint(Arrays.toString(shelterAnimals));
		
		while (petChoice.length() > 0 && petChoice.charAt(0) != 'Q') {
			customPrint("What would you like to do? (Q to quit, P to panic quit if something is wrong)", true);
			petChoice = kb.next();
			kb.nextLine();
			
			if (petChoice.charAt(0) == 'P') return; // in case the loop goes rogue
		}	
	}
	
	// newRandomAnimal - Generate a random Animal object with name, age, !! sub-class !!
	// out: an Animal object that may actually be a Cat, Dog, etc object
	public static Animal newRandomAnimal() {
		debugPrint("Generating random animal");
		Animal newRandom = new Animal(); // investigate later: does this leak memory? Just want a failsafe default value
		
		
		int randomSpecies = (int)(Math.random() * 2);
		switch (randomSpecies) {
			case 0: // Cat
				newRandom = new Cat();
				break;
			case 1: // Animal
				newRandom = new Animal();
				break;
			default: // Uh oh
				debugPrint("Default case in randomSpecies switch - how?!");
		}
		
		newRandom.randomise();
		
		debugPrint("New animal generated: " + newRandom.toString());
		return newRandom;
	}
}
