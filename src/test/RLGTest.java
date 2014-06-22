package test;

// Written by Patrick Chieppe


import dictionary.RLG;
import java.util.ArrayList;
import java.util.TreeMap;

public class RLGTest {
	
	private final static long TESTS = 1000000;

    public static void main(String[] args) {
    	
    	System.out.println("Testing for " + TESTS + " letters");
    	
		ArrayList<Character> sample = new ArrayList<Character>();
		
    	for (long i = 0; i != TESTS; ++i)
    		sample.add(RLG.next());
    	
    	TreeMap<Character,Integer> counts = new TreeMap<Character,Integer>();
    	
    	for (Character x : sample) {
    		if (counts.get(x) == null)
    			counts.put(x, 1);
    		else
    			counts.put(x, counts.get(x)+1);
    	}
    	
    	System.out.println("Expected letter frequency / Results of the test");
    	System.out.println();
    	System.out.println("E  0.12 / " + (double)counts.get('e')/TESTS);
    	System.out.println("A  0.09 / " + (double)counts.get('a')/TESTS);
    	System.out.println("I  0.09 / " + (double)counts.get('i')/TESTS);
    	System.out.println("O  0.08 / " + (double)counts.get('o')/TESTS);
    	System.out.println("N  0.06 / " + (double)counts.get('n')/TESTS);
    	System.out.println("R  0.06 / " + (double)counts.get('r')/TESTS);
    	System.out.println("T  0.06 / " + (double)counts.get('t')/TESTS);
    	System.out.println("L  0.04 / " + (double)counts.get('l')/TESTS);
    	System.out.println("S  0.04 / " + (double)counts.get('s')/TESTS);
    	System.out.println("U  0.04 / " + (double)counts.get('u')/TESTS);
    	System.out.println("D  0.04 / " + (double)counts.get('d')/TESTS);
    	System.out.println("G  0.03 / " + (double)counts.get('g')/TESTS);
    	System.out.println("B  0.02 / " + (double)counts.get('b')/TESTS);
    	System.out.println("C  0.02 / " + (double)counts.get('c')/TESTS);
    	System.out.println("M  0.02 / " + (double)counts.get('m')/TESTS);
    	System.out.println("P  0.02 / " + (double)counts.get('p')/TESTS);
    	System.out.println("F  0.02 / " + (double)counts.get('f')/TESTS);
    	System.out.println("H  0.02 / " + (double)counts.get('h')/TESTS);
    	System.out.println("V  0.02 / " + (double)counts.get('v')/TESTS);
    	System.out.println("W  0.02 / " + (double)counts.get('w')/TESTS);
    	System.out.println("Y  0.02 / " + (double)counts.get('y')/TESTS);
    }
}