package seeemilyplay.core.listeners;

/**
 * A <code>Listener</code> implementation designed for
 * use in unit tests.
 *
 * @thread-safe
 * @deprecated
 */
public class UnitTestListener implements Listener {

	private final Object lock = new Object();

	private int fireCount;

	/**
	 * Constructs a fresh <code>UnitTestListener</code> that
	 * has yet to be fired.
	 */
	public UnitTestListener() {
		super();
	}

	public void onChange() {
		synchronized (lock) {
			fireCount++;
		}
	}

	/**
	 * The number of times <code>onChange</code> has been called
	 * on this <code>Listener</code>.
	 *
	 * @return	the number of fires made on this listener
	 */
	public int getFireCount() {
		synchronized (lock) {
			return fireCount;
		}
	}

	/**
	 * Whether the <code>onChange</code> method has been called
	 * on this <code>Listener</code>.
	 *
	 * @return	whether the listener has been fired
	 */
	public boolean isFired() {
		synchronized (lock) {
			return (fireCount>0);
		}
	}

	/**
	 * Resets the <code>Listener</code> status, so that it has a
	 * <code>fireCount</code> of 0, and <code>isFired</code> is false.
	 */
	public void reset() {
		synchronized (lock) {
			fireCount = 0;
		}
	}
}
