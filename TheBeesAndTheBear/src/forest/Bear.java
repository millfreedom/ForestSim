package forest;

import java.util.concurrent.TimeUnit;

public class Bear extends Thread implements Runnable {

    private HoneyPot thePot;
    private boolean isAlive = true;
    private boolean isSleeping = false;

    public Bear(HoneyPot pot) {
	thePot = pot;
    }

    public boolean isSleeping() {
	return isSleeping;
    }

    private synchronized void goToSleep() {
	isSleeping = true;
	try {
	    wait();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public synchronized void wakeUp() {
	if (isSleeping) {
	    isSleeping = false;
	    this.notify();
	} else {
	    Forest.tell("Bear", "hey, stop that! Already not sleeping!");
	}
    }

    @Override
    public void run() {
	while (isAlive) {
	    synchronized (this) {
		Forest.tell("Bear", "H-z-s-s-s-s....");

		goToSleep();

		Forest.tell("Bear", "Ugh, " + thePot.getHoneyAmmount() + " of Honey! Gonna swallow it all!");
		try {
		    TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		thePot.emptyPot();
		Forest.tell("Bear", "Om-nom-nom! So sweet honey!");
		Forest.tell("Bear", "Now, It's time to sleep more....");
		//Forest.clearScreen();
	    }
	}

    }

    public void Die() {
	isAlive = false;
    }

    /**
     * @return the thePot
     */
    public HoneyPot getThePot() {
	return thePot;
    }

    /**
     * @param thePot
     *            the thePot to set
     */
    public void setThePot(HoneyPot thePot) {
	this.thePot = thePot;
    }

}
