import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Interface {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		boolean terminated = false;
		Trie trie = new Trie();
		String word;
		while (!terminated) {
			// Display menu
			System.out.println("Enter your choice:\n"
					+ "1) Create an empty trie\n"
					+ "2) Create a trie with initial letters\n"
					+ "3) Insert a word\n" 
					+ "4) Delete a word\n"
					+ "5) List all words that begin with a prefix\n" 
					+ "6) Size of the trie\n"
					+ "7) Search for a word\n"
					+ "8) Print a breadth-first traversal of the trie\n" 
					+ "9) End");

			String in = sc.nextLine();
			switch (in) {
			case "1":
				trie.clear();
				break;
			case "2":
				// start with an empty trie
				trie.clear();;
				System.out.print("Enter your list of letters> ");
				// create an array of string letters from the users input
				String[] input = sc.nextLine().toUpperCase().replaceAll("\\s+", "").split("");
				List<String> inputList = Arrays.asList(input);
				insertAllPermutations(inputList, trie);
				break;
			case "3":
				System.out.print("Enter a word to insert> ");
				word = sc.nextLine().toUpperCase();
				trie.insert(word);
				break;
			case "4":
				System.out.print("Enter a word to delete> ");
				word = sc.nextLine().toUpperCase();
				trie.delete(word);
				break;
			case "5":
				System.out.print("Enter a prefix> ");
				word = sc.nextLine().toUpperCase();
				if (trie.isPrefix(word))
					System.out.println("All words that begin with " + word + ": " + Arrays.toString(trie.allWordsPrefix(word)));
				else 
					System.out.println(word + " is not a prefix");
				break;
			case "6":
				System.out.println("The size of the trie is " + trie.size());
				break;
			case "7":
				System.out.print("Enter a word to search for> ");
				word = sc.nextLine().toUpperCase();
				System.out.println(word + (trie.contains(word)?"":" not") + " found");
				break;
			case "8":
				System.out.println(trie);
			case "9":
				terminated = true;
				break;
			}
			System.out.println();
		}
		sc.close();
	}

	public static void insertAllPermutations(List<String> inputList, Trie trie) throws FileNotFoundException {
		Scanner dictionary = new Scanner(new File("dictionary.txt"));
		boolean containsAllLetters;
		List<String> notChecked;
		String word;
		// look through the dictionary and add any permutation of the input to the trie
		while (dictionary.hasNext()) {
			word = dictionary.next();
			containsAllLetters = true;
			notChecked = new ArrayList<>(inputList);
			for (int i = 0; i < word.length(); i++) {
				if (notChecked.contains(word.charAt(i) + "")) {
					notChecked.remove(word.charAt(i) + "");
				} else {
					containsAllLetters = false;
				}
			}
			if (containsAllLetters) {
				trie.insert(word);
				System.out.println(word + "has been inserted");
			}
		}
	}
}
