/*
 * Operating Systems - Universite de Neuchatel
 * 
 * Assignment #3: an introduction to threads and synchronization in Java
 * 
 * Do not forget to indicate with comments inside the code the 
 * modifications you have made and what problems they fix or 
 * prevent, with references to the questions of the subject (Q1, Q2, etc.)
 */

/**
 * Objects instances of Kitchen represent a kitchen with initially two stoves and 
 * two stocks: initial stock of 16 food and empty final stock. Stoves are used to
 * prepare from the former to the latter.
 */
class Kitchen {
	/**
	 * Stock of food to prepare
	 */
    Stock stockInput = new Stock("input", 16);
    /**
     * Stock of final (prepared) food
     */
    Stock stockOutput = new Stock("output", 0);
    /**
     * Stoves for the preparations
     */
    Stove stove1 = new Stove(stockInput, stockOutput, 8);
    Stove stove2 = new Stove(stockInput, stockOutput, 8);
    
    /**
     * Main entry point: proceed to operate the kitchen work of preparation
     */
    public void work() {
    	System.out.println("Starting kitchen work ...");
    	long initialTime = System.currentTimeMillis();
   		stove1.start();
   		stove2.start();
           //q1 must join the threads to the main thread or else program terminate before computation is done
        try
        {
            stove1.join();
            stove2.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
   		stockInput.display();
   		stockOutput.display();
   		System.out.println("... done ("+((double)(System.currentTimeMillis() - initialTime)/1000)+" second(s))");
    }
    
    /**
     * Entry point for the whole program
     * @param args not used
     */
    public static void main(String[] args) {
    	new Kitchen().work();
    }
}
