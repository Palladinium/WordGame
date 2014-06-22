package dictionary;

// Written by Patrick Chieppe and Edward Desmond-Jones


import java.util.ArrayList;


public class SuffixTree {
	private ArrayList<SuffixTree> children;
	private char node;

	public SuffixTree()
	{
		children = new ArrayList<SuffixTree>();
		node = '%';
	}

	public SuffixTree(char element)
	{
		children = new ArrayList<SuffixTree>();
		node = element;
	}

	public SuffixTree add(char element) {
		children.add(new SuffixTree(element));
		return children.get(children.size()-1);
	}

	public boolean isLeaf()
	{
		return children.isEmpty();
	}

	public char getNode() {
		return node;
	}

	public ArrayList<SuffixTree> getChildren() {
		return children;
	}

	public int contains(char element) {
		for (int i = 0; i != children.size(); ++i) {
			if (children.get(i).getNode() == element)
				return i;
		}
		return -1;
	}

	public boolean addWord(String word) {
		return this.addCharArray(word.concat("$").toCharArray(), 0);
	}


	public boolean addCharArray(char[] word, int offset) {
		for (SuffixTree child : children) {
			if (child.node == word[offset]) {
				if (word[offset] == '$')
					return false;
				else
					return child.addCharArray(word, offset + 1);
			}
		}
		if (word[offset] == '$') {
			this.add(word[offset]);
			return true;
		}
		else
			return this.add(word[offset]).addCharArray(word, offset + 1);
	}

	public boolean containsWord(String word) {
		return this.containsWord(word.concat("$").toCharArray(), 0);
	}

	public boolean containsWord(ArrayList<Character> word) {
		char[] wordArray = new char[word.size() + 1];
		for (int i = 0; i != word.size(); ++i)
			wordArray[i] = word.get(i).charValue();
		wordArray[word.size()] = '$';
		return this.containsWord(wordArray, 0);
	}

	public boolean containsWord(char[] word, int offset) {
		for (SuffixTree child : children)
			if (child.node == word[offset]) {
				if (word[offset] == '$')
					return true;
				else
					return child.containsWord(word, offset + 1);
			}
		return false;
	}

	public LettersData query(ArrayList<Character> letters) {
		LettersData temp = new LettersData();

		for (SuffixTree child : children) {
			if (child.node == '$') {
				++temp.choice;
				if (!temp.longestWord.contains("$"))
					temp.longestWord = "$";
			} else if (letters.contains(child.node)) {
				ArrayList<Character> rletters = new ArrayList<Character>(letters);
				rletters.remove(new Character(child.node));
				temp.combine(child.query(rletters));
			}
		}
		temp.longestWord = node + temp.longestWord;
		if (temp.longestWord.contains("$"))
			return temp;
		else
			return new LettersData();
	}

	@Override
	public String toString() {
		String returnstring = node + ":\n[";
		for (SuffixTree child : children)
			returnstring = returnstring.concat(child.toString() + " ");
		returnstring = returnstring.concat("]");
		return returnstring;
	}


	public class LettersData {

		public int choice = 0;
		public String longestWord = new String();

		public void combine(LettersData data) {
			choice += data.choice;

			if (data.longestWord.length() > longestWord.length())
				longestWord = data.longestWord;
		}

		@Override
		public String toString() {
			return longestWord + " " + longestWord.length() + " " + choice;
		}
	}

}