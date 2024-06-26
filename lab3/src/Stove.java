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
 * Objects that are instances of the Stove class represent stoves to prepare
 * food. The principle is as follows: the call to prepare() picks an element
 * from Stock A, waits for 64 ms, and puts an element to stock B. The work() method
 * runs nbPrepare times the prepare() method, nbPrepare being set by the constructor.
 */

// q1 : we need to extend the Thread
class Stove extends Thread {

	/**
	 * The initial stock
	 */
    private Stock A;
    /**
     * The stock where to put prepared food
     */
    private Stock B;
    /** 
     * Number of preparations when calling work().
     */
    private int nbPrepare;

    /**
     * Constructs an instance of Stove
     * @param A Initial stock
     * @param B Destination stock
     * @param nbPrepare How many preparations should be made
     */
    public Stove(Stock A, Stock B, int nbPrepare, String name) {
        super(name);
        this.A = A;
        this.B = B;
        this.nbPrepare = nbPrepare;
    }

    /**
     * Proceeds to a single preparation from food of Stock A to food of Stock B
     */
    // q3 : we see that scheduler select the thread in a "random" way
    public void prepare() {
        // q2 : we need to synchronize the access to the stocks to avoid errors
        synchronized (A) {
            A.get();
            A.display();
        }
        // q2-q7 : commented out the sleep to avoid the too much waiting time
        // try { Thread.sleep(64); } catch(InterruptedException e) {}

        synchronized (B) {
                B.put();
                B.display();
            }
        }


    /**
     * Proceeds to nbPrepare preparations
     */
    // Q1 parallelize the methode : we rename it *run* because it's a runnable thread now
    public void run() {
        /* The name of this method should really be changed so as to override a
         * central method of the Thread class, but which one...? 
         */
        for(; nbPrepare > 0; nbPrepare--)
            prepare();
    }

    /** 
     * "Unit test" for class Stove (has no use for the rest of the lab!)
     * @param args not used
     */
    static public void main(String[] args) {
        Stock stockInput = new Stock("input", 4, 4);
        Stock stockOutput = new Stock("output", 1, 4);
        new Stove(stockInput, stockOutput, 2, "test").run();
        stockInput.display();
        stockOutput.display();
    }
}
