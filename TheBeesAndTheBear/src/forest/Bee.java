package forest;
 
import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Bee extends Thread implements Runnable, Observer {

    private int beeTravelTime = 3000;
    private int beeTravelTimeTolerance = 1000;

    private Bear theBear;
    private HoneyPot thePot;
    private int honetToBring = ThreadLocalRandom.current().nextInt(2, 6);
    private boolean isAlive = true;

    public Bee(HoneyPot pot, Bear bear) {
	setThePot(pot);
	setTheBear(bear);
    }

    @Override
    public void run() {
	String beeName = "Bee(" + Thread.currentThread().getName() + ")";
	boolean lastFlightIsOk = true;
	while (isAlive) {

	    if (lastFlightIsOk) {
		Forest.tell(beeName, "Going to find some honey...");
		synchronized (this) {
		    try {
			wait(ThreadLocalRandom.current().nextInt(beeTravelTime - beeTravelTimeTolerance,
				beeTravelTime + beeTravelTimeTolerance + 1));
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    } else {
		synchronized (this) {
		    try {
			Forest.tell(beeName, "Flying around, waiting for pot to be free!");
			TimeUnit.SECONDS.sleep(5);
		    } catch (InterruptedException e1) {
			e1.printStackTrace();
		    }
		}
	    }

	    try {
		synchronized (this) {
		    getThePot().addHoney(honetToBring);
		    lastFlightIsOk = true;
		    Formatter formatter = new Formatter();
		    Forest.tell(beeName, formatter.format("brought %d more honey! Now there are %d honey in the pot!",
			    honetToBring, thePot.getHoneyAmmount()).toString());
		    formatter.close();
		}
	    } catch (FullPotException e) {
		lastFlightIsOk = false;
		wakeUpTheBear();
	    }

	}

    }

    private synchronized void wakeUpTheBear() {
	if (getTheBear().isSleeping()) {
	    String beeName = "Bee(" + Thread.currentThread().getName() + ")";
	    Forest.tell(beeName, "Waiting for the bear to eat the honey!");
	    getTheBear().wakeUp();
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

    /**
     * @return the theBear
     */
    public Bear getTheBear() {
	return theBear;
    }

    /**
     * @param theBear
     *            the theBear to set
     */
    public void setTheBear(Bear theBear) {
	this.theBear = theBear;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
