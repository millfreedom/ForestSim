package forest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mill
 *
 */
public class Forest {

    private int countBees = 15;
    private HoneyPot theHoneyPot = HoneyPot.getPot(100);
    private Bee[] theBees = new Bee[countBees];
    private Bear theBear = new Bear(theHoneyPot);
    public static Forest theForest = new Forest();

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
	theForest.doRoutine();

    }

    public synchronized void doRoutine() {

	theBear.setThePot(theHoneyPot);
	theBear.setName("TheBear");
	theBear.start();

	for (int i = 0; i < theBees.length; i++) {
	    // bee = ;
	    theBees[i] = new Bee(theHoneyPot, theBear);
	    theBees[i].setName(String.valueOf(i));
	    theBees[i].start();
	}

	while (true) {
	    try {
		this.wait(1000);
	    } catch (InterruptedException e) {
	    }
	}

    }

    public static void tell(String who, String what) {
	System.out.format("%s[%s]: %s%n",  who, (new SimpleDateFormat("mm:ss:SS")).format(new Date()), what);
    }

}
