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
 * Objects of class Stock represent a set of food. Food is not effectively stored,
 * only a counter is used to represent how much food is available.
 * 
 * It could be possible to use a more realistic queue (FIFO) for the Stock representation.
 * This is left as an exercise for home work. *
 */
class Stock {
	/**
	 * Amount of food
	 */
    private int nbFood;
    /**
     * Name of the stock
     */
    private String name;

    /**
     * Maximum amount of food
     */
    // q7 : we need to add a maximum amount of food
    private int maxFood;

    /**
     * Creates a new Stock object
     * @param name its name
     * @param nbFood initial number of food
     */
    public Stock(String name, int nbFood, int maxFood) {
        this.nbFood = nbFood;
        this.name = name;
        this.maxFood = maxFood;
    }

    /**
     * Adds food
     */
    public void put() {
        //q7 : we need to check if there is space before putting food (if not wait)
        while (this.nbFood >= this.maxFood) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //q5 : notify the threads that are waiting for food
        this.nbFood++;
        notifyAll();

        // q3: adapt print to display the name of the stock
        // System.out.println("put - " + Thread.currentThread().getName() + ": stock " + name + " contains " + nbFood + " food.");
    }

    /**
     * Removes (takes) food
     */
    public void get() {
        //q5 : we need to check if there is food before taking it (if not wait)
        while (this.nbFood <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.nbFood--;
        notifyAll();

        // q3: adapt print to display the name of the stock
        // System.out.println("get - " + Thread.currentThread().getName() + ": stock " + name + " contains " + nbFood + " food.");
    }

    /**
     * Display the stock status
     */
    public void display() {
        //q3 : adapt print to display the name of the thread
        System.out.println(Thread.currentThread().getName() + ": The stock " + name + " contains " + nbFood + " food.");
    }

    /** 
     * "Unit test" for class Stock
     * @param args not used
     */
    static public void main(String[] args) {
        Stock stock = new Stock("test", 5, 10);
        stock.put();
        stock.display();
        stock.get();
        stock.display();
    }
}
