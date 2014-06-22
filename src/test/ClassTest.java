package test;

// Written by Harvey Delaney

import java.util.ArrayList;


import org.junit.Test;

import graphics.LetterTile;

public class ClassTest extends JFXTest {
	static final int WIDTH=640;
	static final int HEIGHT=480;
	
	int boxCount=0;
	int rowCount=0;
	
    ArrayList<LetterTile> boxList = new ArrayList<LetterTile>();
	
	@Test
	    public void yourTestName() throws InterruptedException {
	      runJFXTest(new Runnable() {
	        @Override
	        public void run() { 
	        	/*Group root = new Group();
	        	new Scene (root, WIDTH, HEIGHT);
	        	
	        	//Creates the test objects
	            boxList.add(new LetterTile('a', 85));
	            boxList.add(new LetterTile('b', 85));
	            boxList.add(new LetterTile('c', 85));
	            boxList.add(new LetterTile('d', 85));
	            boxList.add(new LetterTile('e', 85));
	            boxList.add(new LetterTile('f', 85));
	            boxList.add(new LetterTile('g', 85));
	           
	            
	            for (int i=0; i<boxList.size(); i++){
	            	boxList.get(i).addTo(root);
	            	boxCount++;
	            	
                    if(rowCount==6){
        				rowCount=0;
        			}
                   
                    //Every 6 letters, the y increases. Making rows
    				int rows=(int) Math.floor(i/6);
    				
                    boxList.get(i).setY(0, (rows+1)*50);
	            	boxList.get(i).setX(0, ((rowCount+1)*100));
	            	rowCount++;
	            }
	            //End of creating test objects
	            
	            //Start removing objects
	  	        boxList.get(2).setX(10000, 100000);
				boxList.remove(2);
				boxCount--;
				
				boxList.get(3).setX(10000, 100000);
				boxList.remove(3);
				boxCount--;
	            //End removing objects
				
				//Start appending existing charboxes ( this is the code being tested)
				int counts=0;
				for (int z = 0; z < boxList.size(); z++) {
					
										
					if(counts==6){
        				counts=0;
        			}
					
					int rows=(int) Math.floor(z/6);
					boxList.get(z).setY(0, (rows+1)*50);
					boxList.get(z).setX(0, ((counts+1) * 100));
					counts++;
				}
				//End appending
				
				//Add objects
				boxList.add(new LetterTile('b', 85));
				boxList.get(boxCount).addTo(root);
				
				
				int rows=(int) Math.floor(boxCount/6);
                boxList.get(boxCount).setX(0 , (boxCount%6+1)*100);
				boxList.get(boxCount).setY(0,(rows+1)*50);
				boxCount++;
				
			
				boxList.add(new LetterTile('a', 85));
				boxList.get(boxCount).addTo(root);
				
				int rows2=(int) Math.floor(boxCount/6);
				boxList.get(boxCount).setX(0 , (boxCount%6+1)*100);
				boxList.get(boxCount).setY(0,(rows2+1)*50);
				boxCount++;
				
				//Start checking
	        	int counts2=0;
	        	for(int i=0; i<boxList.size(); i++ ){
	        		
	        		
					if(counts2==6){
        				counts2=0;
        			}
	        		
	        		assertJFXTrue(boxList.get(i).getX()== (counts2+1)*100);
	        		
	        		
	        		int rows3=(int) Math.floor(i/6);
	        		assertJFXTrue((int)(boxList.get(i).getY())== (rows3+1)*50);
	        		counts2++;
	        	}*/
	          endOfJFXTest();  // must always finish with this
	        }
	    });
	  }
	
 }
            

        	 