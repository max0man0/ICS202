import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Testing {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your list of letters> ");
		// create an array of string letters from the users input
		String[] input = sc.nextLine().replaceAll("\\s+", "").split("");
		List<String> inputList = Arrays.asList(input);
		
		Trie trie = new Trie();
		Scanner dictionary = new Scanner(new File("dictionary.txt"));
		String word;
		boolean containsAllLetters;
		List<String> checked;
		
		// look through the dictionary and add any permutation of the input to the list words
		while (dictionary.hasNext()) {
			word = dictionary.next();
			containsAllLetters = true;
			checked = new ArrayList<>(inputList);
			for (int i = 0; i < word.length(); i++) {
				if (checked.contains(word.charAt(i)+"")) {
					checked.remove(word.charAt(i)+"");
				}
				else {
					containsAllLetters = false;
				}
			}
			if (containsAllLetters) {
				trie.insert(word);
			}
		}		
		
		
		System.out.println(trie.size());
		
		System.out.print("Enter a word to insert> ");
		word = sc.nextLine();
		trie.insert(word);
		System.out.println(trie.size());
		
		System.out.print("Enter a word to delete> ");
		word = sc.nextLine();
		trie.delete(word);
		System.out.println(trie.size());
		
		System.out.print("Enter a word to search> ");
		word = sc.nextLine();
		System.out.println(trie.contains(word));
		System.out.println(trie.size());
		
		System.out.print("Enter a word to PREFIX> ");
		word = sc.nextLine();
		System.out.println(trie.contains(word));
		System.out.println(trie.size());
		System.out.println(trie);
		
		sc.close();
	}
}