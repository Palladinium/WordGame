package test;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * This class provides a basic framework for unit testing
 * of JavaFX.
 * 
 * Use this class as a wrapper for your JavaFX tests, as follows:
 *
 * public class MyTest extends JFXTest {
 *  
 *   ...
 *  
 *   @Test
 *   public void yourTestName() throws InterruptedException {
 *     runJFXTest(new Runnable() {
 *       @Override
 *       public void run() { 
 *       
 *         < your JavaFX code >
 *         assertJFXTrue(<some condition>); // assert using this
 *         < your JavaFX code >
 *
 *         endOfJFXTest();  // must always finish with this
 *       }
 *   });
 * }
 * 
 */
public class JFXTest extends Application {
  
  /*
   * Use a CountDownLatch to coordinate between JUnit and JFX threads
   */
  static CountDownLatch countDownLatch = new CountDownLatch(1); // initialize to one, so nothing starts until JFX is up and running and has decremented the count
  static void endOfJFXTest() {
    countDownLatch.countDown();    
  }
  private static void await() throws InterruptedException {
    countDownLatch.await();
  }
  private static void reset() {
    countDownLatch = new CountDownLatch(1);   
  }
  
  /**
   * Initialize JavaFX just once, before running any tests.
   */
  @BeforeClass
  public static void initializeJFXTestClass() {
    /* launch JavaFX in a new thread */
    Runnable r = new Runnable() { 
      @Override
      public void run() {
        Application.launch(JFXTest.class);
      };
    };
    Thread th = new Thread(r);
    th.start();
  }

  /*
   * The following are mechanisms for gathering results within
   * JFX for subsequent testing within the JUnit Thread.
   */
  static int MAX_RESULTS = 100;                 // this is arbitrary, 100 should be far more than necessary.  
  int resultCount = 0;                          // count how many results have been gathered so far
  boolean results[] = new boolean[MAX_RESULTS]; // store the results here
  
  /**
   * Before running a test, clear out the results array.
   */
  @Before
  public final void initializeJFX() {
    for(int i = 0; i < results.length; i++) {
      results[i] = false;
    }
    resultCount = 0;
  }
  
  /**
   * Gather a result from within JFX for storing and subsequent
   * testing within JUnit.
   * 
   * @param condition A boolean that should hold true (if not, a JUnit failure will ensue).
   */
  final void assertJFXTrue(boolean condition) {
    if (resultCount == MAX_RESULTS)
      fail("Too many assertions in one test.  Only "+MAX_RESULTS+" supported.");
    else {
      results[resultCount] = condition;
      resultCount++;
    }
  }

  /**
   * Test that all results gathered during JFX execution hold.
   * 
   * Print an error message identifying any failing conditions.
   * 
   * @return True if all conditions were true.
   */
  final boolean allJFXTrue() {
    boolean rtn = true;
    for (int i = 0; i < resultCount; i++) {
      if (results[i] == false) {
        System.err.println("Failed assertion number "+(i+1));
        rtn = false;
      }
    }
    return rtn;
  }
 
  /**
   * Run a JFX test.  This involves launching the test in a
   * JFX context, and results are gathered into the results
   * array for testing by JUnit later.
   * 
   * @param test An instance of Runnable that contains the JFX 
   * test to be executed.
   * @throws InterruptedException
   */
  final void runJFXTest(Runnable test)  throws InterruptedException {
    await();
    reset();
    Platform.runLater(test);
    await();
    assertTrue(allJFXTrue());    
  }

  /**
   * Start the JFX platform.
   */
  @Override
  public final void start(Stage arg0) throws Exception {
    countDownLatch.countDown();  // count down to indicate that the platform is up and running
  }

}

