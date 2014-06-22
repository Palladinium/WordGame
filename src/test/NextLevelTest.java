package test;

// Written by Edward Desmond-Jones

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class NextLevelTest {
	ArrayList<Character> bankVec = new ArrayList<Character>();
	
	static final int bonusMultiplier = 100;
	int score = 0;
	
	static final int maxLevels = 5;
	int currentLevel = 0;
	
	float nextTime = 1.0f;
	boolean gameOn = true;
	
	/*
	 * The function that needs to be tested.
	 * 
	 * It should cause a transition between levels
	 */
	private void nextLevel() {
		// Compute the end of level bonus
		final int bonus = (32 - bankVec.size()) * bonusMultiplier;
		
		// And add it to the score
		score += bonus;
		
		// Clear the bank vector for the next level
		bankVec.clear();
		
		if (currentLevel < maxLevels) {
			// We still haven't completed all the levels yet...
			++currentLevel;
			
			// Calculate the next time period
			nextTime = 1.0f - (0.1f * currentLevel);
		} else {
			// We've completed the game
			gameOn = false;
		}
	}
	
	@Test
	public void testNextLevel() {
		final int oldScore = 42; // An initial score, should be increased by the bonus
		final int bonus = (32 - 4) * bonusMultiplier; // Bonus for only have four letters in the bank
		final int oldLevel = 2; // Refers to the third level (Zero indexed for convience)
		final float newTime = 1.0f - (0.1f * 3); // New time for the fourth level
		
		// Add four letters such that bonus should be
		// (32 - 4) * bonusMultiplier
		bankVec.add('t');
		bankVec.add('e');
		bankVec.add('s');
		bankVec.add('t');
		
		score = oldScore; // Score before test
		currentLevel = oldLevel; // Level before test
		
		nextLevel();
		
		// The bank vector should be cleared by next level,
		// causing the size to change to zero.
		assertTrue(bankVec.size() == 0);
		
		// The score should have increased by the bonus amount
		assertTrue((oldScore + bonus) == score);
		
		// The level number should have increased by one
		assertTrue((oldLevel + 1) == currentLevel);
		
		// The next frame should be adjusted.
		assertTrue(nextTime == newTime);
		
		// Test to see that the game is ended when the last level has been won.
		currentLevel = maxLevels;
		
		bankVec.clear();
		
		// Set the bank to 32 letters...
		for (int count = 0; count < 32; ++count)
			bankVec.add('a');
		
		// ... to test if no bonus is awarded for having a full bank when winning.
		int nextOldScore = score;
		
		nextLevel();
		
		// The level number should not have changed
		assertTrue(currentLevel == maxLevels);
		
		// Make sure no bonus was collected
		assertTrue(nextOldScore == score);
		
		// The game should flagged as being finished
		assertFalse(gameOn);
	}
}
