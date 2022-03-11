import java.util.LinkedList;
import java.util.Queue;

public class Trie {
	private TrieNode root;

	public Trie() {
		root = new TrieNode('\0');
	}

	public void insert(String word) {
		TrieNode current = root;
		char c;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			// If the letter is not in the tree insert it
			if (current.children[c - 'A'] == null) {
				current.children[c - 'A'] = new TrieNode(c);
			}
			// Go to the next level
			current = current.children[c - 'A'];
		}
		// Mark he last letter is a word end
		current.wordEnd = true;
	}

	public void clear() {
		root = new TrieNode('\0');
	}

	public boolean contains(String word) {
		TrieNode current = root;
		char c;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			// If the letter does not exist in the trie
			if (current.children[c - 'A'] == null) {
				return false;
			}
			// Go to the next level
			current = current.children[c - 'A'];
		}
		return current.wordEnd;
	}

	public boolean isPrefix(String word) {
		TrieNode current = root;
		char c;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			// If the letter does not exist in the trie
			if (current.children[c - 'A'] == null) {
				return false;
			}
			// Go to the next level
			current = current.children[c - 'A'];
		}
		return true;
	}

	public void delete(String word) {
		if (this.contains(word)) {
			delete(root, word, 0);
		}
		else {
			throw new IllegalArgumentException("Word is not in this trie");
		}
	}

	private boolean delete(TrieNode node, String word, int index) {
		// When trie is empty
		if (node == null) {
			return false;
		}
		// When reaching the last character in the word
		else if (index == word.length()) {
			// If node is not the end of a word
			if (!node.wordEnd) {
				return false;
			}
			node.wordEnd = false;
			
			// delete the node if it has no children
			return isEmpty(node);
		}
		// When the character is not the last
		else {
			char c = word.charAt(index);
			boolean shouldDeleteChild = delete(node.children[c - 'A'], word, index + 1) && !node.children[c-'A'].wordEnd;
			
			if (shouldDeleteChild) {
				node.children[c-'A'] = null;
				return isEmpty(node);
			}
			else {
				return false;
			}
		}
	}

	private boolean isEmpty(TrieNode node) {
		for (int i = 0; i < node.children.length; i++) {
			// If the child is not empty
			if (node.children[i] != null) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty() {
		return isEmpty(root);
	}

	public int size() {
		return size(root);
	}

	private int size(TrieNode node) {
		// Breadth-first traversal
		Queue<TrieNode> queue = new LinkedList<>();
		int size = 0;
		// Start from root
		queue.add(root);
		// While queue is not empty
		while (!queue.isEmpty()) {
			// Remove and return first queue element
			TrieNode current = queue.poll();
			// For node's children
			for (int i = 0; i < current.children.length; i++) {
				// If child exists
				if (current.children[i] != null) {
					// Add node to queue
					queue.add(current.children[i]);
					// increment size
					size++;
				}
			}
		}
		return size;
	}

	public String[] allWordsPrefix(String prefix) {
		// Find the last letter of the prefix
		TrieNode current = root;
		char c;
		for (int i = 0; i < prefix.length(); i++) {
			c = prefix.charAt(i);
			current = current.children[c - 'A'];
		}
		LinkedList<String> list = new LinkedList<>();
		findAllWordsOf(current, prefix, list);
		return list.toArray(new String[list.size()]);
	}

	// Recursively find all words that start with node and add prefix before it (the
	// words are stored in list)
	private void findAllWordsOf(TrieNode node, String word, LinkedList<String> list) {
		if (node == null) {
			return;
		}
		// when reaching the end of the word
		else {
			if (node.wordEnd) {
				list.add(word);
			}
			for (int i = 0; i < node.children.length; i++) {
				// if child exists add the the child after the word
				if (node.children[i] != null) {
					findAllWordsOf(node.children[i], word + node.children[i].letter, list);
				}
			}
		}
	}

	@Override
	public String toString() {
		// Breadth-first traversal
		Queue<TrieNode> queue = new LinkedList<>();
		String result = "";
		// Start from root
		queue.add(root);
		// While queue is not empty
		while (!queue.isEmpty()) {
			// Remove and return first queue element
			TrieNode current = queue.poll();
			// For node's children
			for (int i = 0; i < current.children.length; i++) {
				// If child exists
				if (current.children[i] != null) {
					// Add node to queue
					queue.add(current.children[i]);
					// Add node's char to output string
					result += current.children[i].letter + (current.children[i].wordEnd?" End of word":"") + ", ";
				}
			}
		}
		return result;

	}
}

class TrieNode {
	public char letter;
	public boolean wordEnd;
	public TrieNode[] children;

	public TrieNode(char letter) {
		this.letter = letter;
		this.wordEnd = false;
		// There are 26 capital letters
		this.children = new TrieNode[26];
	}
}