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
	 * Stock of food of A
	 */
    //q7 : use 10000 food
    Stock stockA = new Stock("a", 10000, 10000);
    /**
     * Stock of food of B
     */
    Stock stockB = new Stock("b", 0, 10000);
    /**
     * Stock of food of C
     */
    // q4 : we need to create a third stock for intermediate storage
    // q4 - observe the trace : the problem is that the stove2 tries to take food
            // from stockC before the stove1 has put food in it
    Stock stockC = new Stock("c", 0, 200);
    /**
     * Stoves for the preparations
     */
    // q6 : give half of the food to the third stove
    Stove stove1 = new Stove(stockA, stockC, 5000, "Thread 1");
    Stove stove2 = new Stove(stockC, stockB, 5000, "Thread 2");
    Stove stove3 = new Stove(stockC, stockB, 5000, "Thread 3");
    // q7 : double the number of preparations for the first stove
    Stove stove1_copy = new Stove(stockA, stockC, 5000, "Thread 4");

    /**
     * Main entry point: proceed to operate the kitchen work of preparation
     */
    public void work() {
    	System.out.println("Starting kitchen work ...");
    	long initialTime = System.currentTimeMillis();

        // q6 : start stove last
        stove2.start();
        stove3.start();
        stove1.start();
        // q7 : double stove1
        stove1_copy.start();

        //q1 : must join the threads to the main thread or else program terminate before computation is done
        try
        {
            stove1.join();
            // q7 : double stove1
            stove1_copy.join();

            stove2.join();
            stove3.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        stockA.display();
        stockB.display();
        stockC.display();

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
