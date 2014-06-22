package dictionary;

// Written by Patrick Chieppe

import java.util.ArrayList;

public class RLG {
	static private final ArrayList<Character> LETTERS = new ArrayList<Character>(100);
	static private ArrayList<Character> letterBag = null;
	static private char[] lookahead = new char[2];

	/*
	 * The RLG is seeded with the standard Scrabble distribution, eliminating
	 * letters as they are drawn, resulting in a non-linear distribution that
	 * increases the chance of drawing a letter that has been drawn less times
	 * than the rest, thus approximating the original distribution in a
	 * pseudo-random way. This way scenarios where unusable letters are
	 * repeatedly drawn are made less likely.
	 */

	static {
		int i;
		for (i = 0; i != 12; ++i) {
			LETTERS.add('e');
		}
		for (i = 0; i != 9; ++i) {
			LETTERS.add('a');
			LETTERS.add('i');
		}
		for (i = 0; i != 8; ++i) {
			LETTERS.add('o');
		}
		for (i = 0; i != 6; ++i) {
			LETTERS.add('n');
			LETTERS.add('r');
			LETTERS.add('t');
		}
		for (i = 0; i != 4; ++i) {
			LETTERS.add('l');
			LETTERS.add('s');
			LETTERS.add('u');
			LETTERS.add('d');
		}
		for (i = 0; i != 3; ++i) {
			LETTERS.add('g');
		}
		for (i = 0; i != 2; ++i) {
			LETTERS.add('b');
			LETTERS.add('c');
			LETTERS.add('m');
			LETTERS.add('p');
			LETTERS.add('f');
			LETTERS.add('h');
			LETTERS.add('v');
			LETTERS.add('w');
			LETTERS.add('y');
		}
		// 1 letter
		LETTERS.add('k');
		LETTERS.add('j');
		LETTERS.add('x');
		LETTERS.add('q');
		LETTERS.add('z');

		shuffleBag();
		lookahead[0] = letterBag.get(0);
		letterBag.remove(0);
		lookahead[1] = letterBag.get(0);
		letterBag.remove(0);
	}

	static public void shuffleBag() {
		letterBag = new ArrayList<Character>(LETTERS);
		java.util.Collections.shuffle(letterBag);
	}

	static public char next() {
		char returnletter = lookahead[0];
		lookahead[0] = lookahead[1];
		if (letterBag.size() == 0) 
			shuffleBag();
		lookahead[1] = letterBag.get(0);
		letterBag.remove(0);

		return returnletter;
	}

	static public char[] getLookahead() {
		return lookahead;
	}

	static public void resetForNextLevel() {
		// Make sure the lookahead has changed for the next level
		next();
		next();
	}
}
