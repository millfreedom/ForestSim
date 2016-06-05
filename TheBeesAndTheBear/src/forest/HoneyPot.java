package forest;

import java.util.concurrent.atomic.AtomicInteger;

public class HoneyPot {

    private AtomicInteger HoneyAmmount = new AtomicInteger(0);
    private AtomicInteger MaxHoneyAmmount = new AtomicInteger(0);
    private static HoneyPot Instance = null;

    /**
     * singleton constructor
     */
    private HoneyPot() {
    }

    /**
     * @param maxHoneyAmmount
     * @return HoneyPot
     */
    public static synchronized HoneyPot getPot(int maxHoneyAmmount) {
	if (Instance == null) {
	    Instance = new HoneyPot();
	    Instance.MaxHoneyAmmount = new AtomicInteger(maxHoneyAmmount);
	}
	return Instance;
    }

    public static synchronized HoneyPot getPot() {
	return Instance;
    }

    public static synchronized void destroyPot() {
	Instance = null;
    }

    public synchronized int getHoneyAmmount() {
	return HoneyAmmount.get();

    }

    public synchronized void emptyPot() {
	HoneyAmmount.set(0);
    }

    /**
     * @param honeyAmmount
     *            the honeyAmmount to add
     */
    public synchronized int addHoney(int honeyAmmount) throws FullPotException {
	if (isFull())
	    throw new FullPotException("The pot is full!");

	if (isGonnaBeFull(honeyAmmount))
	    throw new FullPotException("The pot will overflow if I'll put my honey!");

	return HoneyAmmount.addAndGet(honeyAmmount);
    }

    public synchronized boolean isGonnaBeFull(int honeyAmmount) {
	return HoneyAmmount.intValue() + honeyAmmount > getMaxHoneyAmmount();
    }

    /**
     * 
     * @return boolean
     */
    public boolean isFull() {
	return HoneyAmmount.intValue() >= getMaxHoneyAmmount();
    }

    /**
     * @return the maxHoneyAmmount
     */
    private int getMaxHoneyAmmount() {
	return MaxHoneyAmmount.intValue();
    }

}
