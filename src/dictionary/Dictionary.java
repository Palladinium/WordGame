package dictionary;

// Written by Patrick Chieppe

import java.io.BufferedReader;
import java.util.ArrayList;

public class Dictionary {
	private static dictionary.SuffixTree dictionary = new dictionary.SuffixTree();
	public static int longestValidWord = 0;

	private static ArrayList<String> usedWords = new ArrayList<String>();

	// Cache
	private static ArrayList<Character> cachedLetters = new ArrayList<Character>();
	private static int cachedChoice = 0;
	private static String cachedLongestWord = "";

	static {
		try {
			BufferedReader in = new BufferedReader(
					new java.io.InputStreamReader(
							Dictionary.class.getResourceAsStream("/dictionary.txt")));
			for (String word = in.readLine(); word != null; word = in.readLine()) {

				if (longestValidWord < word.length())
					longestValidWord = word.length();
				dictionary.addWord(word);
			}
			in.close();
		} catch (java.lang.Exception e) {
			throw new java.lang.RuntimeException(e);
		}
	}

	// Non-cached queries
	static public boolean isWord(String word) {
		return dictionary.containsWord(word);
	}

	static public boolean isWord(ArrayList<Character> word) {
		return dictionary.containsWord(word);
	}

	// Cached queries
	static public int choice(ArrayList<Character> letters) {
		if (!letters.equals(cachedLetters))
			updateCache(letters);
		return cachedChoice;
	}

	static public int longest(ArrayList<Character> letters) {
		if (!letters.equals(cachedLetters))
			updateCache(letters);
		return cachedLongestWord.length();
	}

	static public ArrayList<Character> longestWord(ArrayList<Character> letters) {
		if (!letters.equals(cachedLetters))
			updateCache(letters);

		ArrayList<Character> returnWord = new ArrayList<Character>(cachedLongestWord.length());

		for (int i = 0; i != cachedLongestWord.length(); ++i) 
			returnWord.add(cachedLongestWord.charAt(i));

		return returnWord;
	}

	static private void updateCache(ArrayList<Character> newCache) {
		cachedLetters = new ArrayList<Character>(newCache);
		SuffixTree.LettersData cache = dictionary.query(newCache);
		if (cache.longestWord.length() != 0)
			cachedLongestWord = cache.longestWord.substring(1, cache.longestWord.length() - 1);
		else
			cachedLongestWord = cache.longestWord;
		cachedChoice = cache.choice;
	}

	static public void clearUsedWords() {
		usedWords.clear();
	}

	static public int score(ArrayList<Character> word) {

		String wordString = new String();
		for (Character c : word)
			wordString = wordString.concat(java.lang.Character.toString(c));

		/*
		 * If a word has already been used during this session it will be usable
		 * but give 0 points
		 */

		int returnscore = 0;
		if (usedWords.contains(wordString))
			return 0;
		else
			usedWords.add(wordString);

		for (Character c : word)
			switch (c) {
			case 'l':
			case 's':
			case 'u':
			case 'n':
			case 'r':
			case 't':
			case 'o':
			case 'a':
			case 'i':
			case 'e':
				returnscore += 1;
				break;
			case 'g':
			case 'd':
				returnscore += 2;
				break;
			case 'b':
			case 'c':
			case 'm':
			case 'p':
				returnscore += 3;
				break;
			case 'f':
			case 'h':
			case 'v':
			case 'w':
			case 'y':
				returnscore += 4;
				break;
			case 'k':
				returnscore += 5;
				break;
			case 'j':
			case 'x':
				returnscore += 8;
				break;
			case 'q':
			case 'z':
				returnscore += 10;
				break;
			default:
				break;
			}
		return returnscore * word.size();
	}
}
