package notebookdoodle;
import java.util.Arrays; // general use; pet shelter game
import java.util.ArrayList; // general use; pet shelter game
//import java.lang.Math.*; // general use; generate random animal
import java.util.Scanner; // keyboard input
import java.util.TreeSet; // evil hangman game

public class Test
{
	public static int shelterSize = 5; // used in petShelter(), how many animals can fit in the shelter
	public static boolean showDebug = true; // used in debugPrint(), false = mute debug statements
	public static TreeSet<String> hangmanDict = new TreeSet<String>(); // all words for evil hangman game
	
	public static void main (String[] args) {
		debugPrint("Main init");
		Scanner keyboard = new Scanner(System.in);		
		
		
		customPrint("Welcome!", true);
		
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
		if (showDebug) System.out.println(toPrint);
	}
	
	// menuContinue - Main menu loop, this is the "true main"
	// in: keyboard object to read from
	// out: F = exit, T = continue
	public static boolean menuContinue(Scanner kb) {
		//debugPrint("Beginning menuContinue");
		String keyboardString = "";
		
		printMainMenu();
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
			case '2':
				evilHangman(kb);
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
		customPrint("=== Menu: Enter the letter corresponding to your choice ===");
		customPrint("M : Print this menu");
		//customPrint("O : Configure global options");
		customPrint("1 : Play pet shelter game");
		customPrint("2 : Play evil hangman");
		customPrint("Q : Exit");
	}
	
	// tedious grunt work, I'll do this on a calmer day
	/*
	public static void configureOptions(Scanner kb) {
		String optChoice = " ";
		while (optChoice.length() > 0 && optChoice.charAt(0) != 'Q') {
			customPrint("1 : Show debug prints: " + showDebug);
			customPrint("2 : Pet game shelter size: " + shelterSize);
		}
	}
	/*
	
	
	/* Should I put these in separate .java files?
	 * But then I can't use customPrint() in them without
	 * creating a circular dependency, and that's annoying
	 * (Even though without a GUI, it's the same as println)
	 */
	//------------------------ Function 1 - Pet shelter ------------------------
	/* For now, I want to generate random shelter cats / dogs / (others?)
	 *  and let the user interact with them, maybe get them adopted / take in
	 *  new animals.
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
		ArrayList<Animal> fosterAnimals = new ArrayList<Animal>();
		
		customPrint("\n=== Welcome to the pet shelter! ===", true);
		customPrint("Current shelter population: ".concat(shelterSize+""));
		customPrint(Arrays.toString(shelterAnimals));
		
		while (petChoice.length() > 0 && petChoice.charAt(0) != 'Q') {
			customPrint("What would you like to do? (Q to quit, P to panic quit if something is wrong)", true);
			customPrint("1 : View shelter animals");
			customPrint("2 : View your fosters");
			customPrint("3 : Foster an animal");
			customPrint("4 : Get your fosters adopted");
			customPrint("5 : Go to a new shelter");
			petChoice = kb.next();
			kb.nextLine();
			
			if (petChoice.charAt(0) == 'P') return; // in case the loop goes rogue
			switch (petChoice.charAt(0)) {
				case 'Q': 
					customPrint("Goodbye from the Pet Shelter, back to the main menu!");
					return;
				case '1': // view shelter animals
					customPrint(Arrays.toString(shelterAnimals));
					break;
				case '2': // view your fosters
					if (fosterAnimals.size() < 1) 
						{
							customPrint("Oops, you have no fosters yet!"); 
							break;
						}
					customPrint(fosterAnimals.toString());
					break;
				case '3': // foster an animal
					customPrint("Which animal would you like to foster? (Number 1-" + shelterSize + ")");
					int newFoster = kb.nextInt() - 1;
					kb.nextLine();
					if (newFoster < 0 || newFoster >= shelterSize) {
						customPrint("Oops! You went to the shelter, but there was no pet at that kennel number.");
						break;
					}
					
					// add animal to foster arraylist
					fosterAnimals.add(shelterAnimals[newFoster]);
					
					// randomize new animal in shelter array
					shelterAnimals[newFoster] = newRandomAnimal(); // don't want to randomise the one we just got
					
					customPrint("Added to your foster animals list! [2] to see all of your fosters.");
					break;
				case '4': // get your fosters adopted
					if (fosterAnimals.size() < 1) {
						customPrint("You have no foster animals. Why don't you get one from the shelter?");
						break;
					}
					// remove animal from foster arraylist
					customPrint("Which animal would you like to get adopted? (Number 1-" + fosterAnimals.size() + ")");
					int newAdopt = kb.nextInt() - 1;
					kb.nextLine();
					if (newAdopt < 0 || newAdopt >= fosterAnimals.size()) {
						customPrint("Oops! You looked through your foster animals and realized you don't have one with that kennel number.");
						break;
					}
					
					// Do I need to destroy the memory of this object or can I trust the garbage collector to get it?
					// In a "full product" version I probably would try to explicitly destroy it just to be safe
					fosterAnimals.remove(newAdopt);
					
					break;
				case '5': // go to a new shelter
					// randomize all animals in shelter array
					customPrint("You go to a new shelter...");
					for (int i = 0; i < shelterSize; i++) { // same possible weirdness with memory?
						shelterAnimals[i] = newRandomAnimal();
					}
					customPrint("New shelter animals: ", false);
					customPrint(Arrays.toString(shelterAnimals));
					break;
				default:
					customPrint("I didn't recognize that choice, sorry!");
			}
			customPrint("===============");
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
			case 1: // Dog
				newRandom = new Dog();
				break;
			default: // Uh oh
				debugPrint("Default case in randomSpecies switch - how?!");
		}
		
		newRandom.randomise();
		
		debugPrint("New animal generated: " + newRandom.toString());
		return newRandom;
	}
	// ------------------------ End Function 1 ------------------------
	
	//------------------------ Function 2 - Evil hangman ------------------------
	/* Evil hangman: Similar to normal hangman game, the user guesses
	 * the hidden word one letter at a time. Except it's evil so the word
	 * actually isn't pre-chosen! The game starts with a set of possible words,
	 * and as the user guesses, the game will narrow the set down to make
	 * sure the user's guess is wrong!
	 */
	//in: Keyboard object
	// ideas for future development: handling spaces better, guessing a sequence of letters at a time, drawing the hangman
	public static void evilHangman(Scanner kb) {
		// assumptions: currentHangmanLength is always at least 1,
		//   a space in the winning word counts as a letter
		debugPrint("Default hangman values for testing");
		int currentHangmanLength = 3;
		
		char userChoice = ' ';
		String currentHint = "_";

		int currentHangmanLives = 3;
		String currentGuesses = "";
		boolean gameOver = false;
		boolean gameWin = false;
		
		hangmanInit();
		
		// TODO (after adding words of other lengths - maybe 3 through 6)
		// step 1: user selects (or randomises) difficulty
		// difficulty determines word size and number of lives
		currentHangmanLength = 3; // to be replaced with user selection
		currentHangmanLives = 3;
		
		// done
		// step 2: create a copy of the set of words of chosen length
		// (share pointers to save memory, except the set object itself)
		TreeSet<String> currentDict = hangmanOfLength(currentHangmanLength);
		// hint starts as blanks _ _ _ ...
		currentHint = "_";
		for (int i=1; i<currentHangmanLength; i++) {
			currentHint = currentHint + " _";
		}
		
		// step 3: print the hangman spaces, loop: what letter choice?
		while (!gameOver) {
			customPrint("\n\n");
			customPrint(currentHint);
			
			debugPrint(currentDict.toString());
			
			customPrint("You have " + currentHangmanLives + " lives remaining.", true);
			if (currentGuesses.length() > 0) customPrint("Your guesses so far: " + currentGuesses, true);
			else customPrint("You haven\'t guessed anything yet.", true);
			customPrint("What letter will you guess next? (To quit, hit \'enter\' without a guess): ", false);
			
			// set user input (or quit early)
			String tmpNextLine = kb.nextLine();
			if (tmpNextLine.length() > 0) userChoice = tmpNextLine.charAt(0);
			else {
				customPrint("Press enter again to confirm quit");
				if (kb.nextLine().length() < 1) {
					customPrint("Quitting evil hangman");
					gameWin = false;
					gameOver = true;
					
					return;
				}
			}

			// record their guess
			userChoice = (userChoice+"").toUpperCase().charAt(0);
			
			if (currentGuesses.length() > 0) currentGuesses = currentGuesses.concat(", ");
			currentGuesses = currentGuesses.concat(userChoice+"");
			
			// checking if guess is correct
			if (hangmanGuess(currentDict, userChoice)) {
				customPrint("Your guess is correct!");
				currentHint = updateHint(currentHint, currentDict, userChoice);
				
				//did they win?
				if (currentDict.size() == 1 && currentDict.first().replaceAll("\\s","").equals(currentHint.replaceAll("\\s", ""))) {
					gameWin = true;
					gameOver = true;
				}
			} else {
				currentHangmanLives--;
				customPrint("Your guess is incorrect! Lives remaining: " + currentHangmanLives);
				if (currentHangmanLives < 1) {
					gameWin = false;
					gameOver = true;
				}
			}
		}

		
  		if (gameWin) customPrint("Congratulations, you won! The word was " + currentDict.first() + ".", true);
		else customPrint("Muahaha! You ran out of lives, better luck next time. Possible words were: " + currentDict.toString(), true);
  		
  		customPrint("Back to the main menu...\n\n");
	}
	
	// populate hangman dictionary
	// this should only need to be called once
	public static void hangmanInit() {
		hangmanDict.add("BAT");
		hangmanDict.add("CAT");
		hangmanDict.add("COT");
		hangmanDict.add("CCC");
		hangmanDict.add("TTT");
		hangmanDict.add("FILTER ME");
		
		// adding multiple at once
		hangmanDict.addAll(Arrays.asList("THIS", "IS", "A", "LIST"));
		
		
		// TODO - add more words
	}
	
	// return a new set for hangman but only with words of the desired length
	public static TreeSet<String> hangmanOfLength(int wordLength){
		TreeSet<String> toReturn = new TreeSet<String>();
		for (String tmpWord : hangmanDict) {
			if (tmpWord.length() == wordLength) toReturn.add(tmpWord);
		}
		return toReturn;
	}
	
	/* hangmanGuess: narrow the working set of hangman words down so that the user guess is wrong
	* if unsuccessful, return true (user guess is correct)
	* else return false (user loses a life; possible words are narrowed down)
	* note: possible words gets narrowed down further in updateHint()
	*/
	// in: set of possible words in the current game, letter the user guessed
	// out: modifies currentDict if possible to not contain the guess, *or* returns true for correct guess
	// note: case does not matter
	public static boolean hangmanGuess(TreeSet<String> currentDict, char userGuess) {
		if (currentDict.isEmpty()) {
			debugPrint("HangmanGuess error: currentDict is empty somehow");
			return false;
		}
		
		int biggestSetSize = -1; // for selecting which space the guess goes into
		int biggestSetIndex = -1;
		
		/* Sort words into mini-sets based on where the userGuess occurs
		 * ex: if userGuess is 'E', then "ease, ever, eyes, elks" would get split up
		 * and the biggest list "ever, eyes" would be chosen
		 */
		// It seems like there is a better way to do this
		ArrayList<TreeSet<String>> miniSets = new ArrayList<TreeSet<String>>();
		for (String tmpWord : currentDict) {
			boolean added = false;
			for (int j=0; j<miniSets.size() && !added; j++) { // iterate through existing miniSets
				String firstWord = miniSets.get(j).first();
				added = true;
				for (int l=0; l<tmpWord.length() && added; l++) { // iterate through chars in word
					if ((tmpWord.charAt(l) == userGuess) != (firstWord.charAt(l) == userGuess)) {
						// one word has the guess at this spot but the other does not
						added = false;
					}
				}
				if (added) miniSets.get(j).add(tmpWord);
			}
			if (!added) { // no matches in current miniSets
				miniSets.add(new TreeSet<String>());
				miniSets.get(miniSets.size()-1).add(tmpWord);
			}
		}
		
		// pick biggest miniSet to be our new dict
		for (int k=0; k<miniSets.size(); k++) {
			if (miniSets.get(k).size() > biggestSetSize) { // note: I could do a "tiebreaker" for same-size to have least correct guesses but... this is evil enough
				biggestSetSize = miniSets.get(k).size();
				biggestSetIndex = k;
			}
		}
		
		debugPrint("MiniSets: " + miniSets.toString());
		debugPrint("Biggest set index " + biggestSetIndex);
			
			/* WONTFIX - This isn't 100% perfect because we'd want to further narrow the list to the least guesses for repeat letters
			*  ex: Guess is 'e', then the set "eeeA, eeeB" currently gets chosen over "ABCe" despite being easier to guess
			*  But the algorithm to fix that would have to be super complex
			*  Further narrow the list to words with most unique letters? Or fewest of the guessed letter?
			*  ex: Would need to split hairs to decide between "ABee, BCee, ACee, ABCe" vs "eAAA, eBBB, eCCC, eABC"
			*  To save memory and processing power, I won't be addressing it for this demo.
			*  
			*  But in real life, I would solve this by further sorting each set in proposedDicts into
			*  mini-sets based on which indices the userGuess appears in, and pick only the mini-set from that
			*  sort with the most average unique characters per entry, to take the spot in proposedDicts[i].
			*/
			
		// all words in currentDict contained the guess
		if (miniSets.get(biggestSetIndex).isEmpty()) return true;
		
		// or, successfully narrowed down currentDict to words that don't contain the guess
		// OR, the bigger array is one in which the guess was correct
		currentDict.clear();
		currentDict.addAll(miniSets.get(biggestSetIndex));
		
		debugPrint("hangmanGuess dict after narrowing " + currentDict.toString());
		return currentDict.first().contains(userGuess+"");
	}
	
	// updateHint: fill out the hint (ex: _ _ L A _) with current user guess
	// Note - modifies currentDict further
	public static String updateHint(String currentHint, TreeSet<String> currentDict, char userGuess) {
		String toReturn = currentHint;
		
		// replace chars in hint string with user guess
		String currentFirst = currentDict.first();
		for (int i=0; i<currentFirst.length(); i++) {
			if (currentFirst.charAt(i) == userGuess) {
				// "EasE" -> "E _ _ E"
				toReturn = toReturn.substring(0, i*2) + userGuess + toReturn.substring((i*2) + 1);
			}
		}
		
		debugPrint("toReturn : " + toReturn);
		
		return toReturn;
	}
	
	// ------------------------ End Function 2 ------------------------
}
