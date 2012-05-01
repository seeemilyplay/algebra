package seeemilyplay.core.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import junit.framework.TestCase;


public class ThreadPoolTest extends TestCase {

	private ThreadPool threadPool;


	protected void setUp() throws Exception {
		super.setUp();
		if (threadPool!=null && !threadPool.isShutdown()) {
			threadPool.shutdown();
		}
	}

	public void testThreadPoolProvidesExecutor() {

		givenThreadPool();

		thenHasExecutor();
	}

	public void testThreadPoolProvidesScheduledExecutor() {

		givenThreadPool();

		thenHasScheduledExecutor();
	}

	public void testShuttingDownPoolShutsDownTheExecutor() {

		givenThreadPool();

		whenShutdown();

		thenExecutorIsShutdown();
	}

	public void testShuttingDownPoolShutsDownTheScheduledExecutor() {

		givenThreadPool();

		whenShutdown();

		thenScheduledExecutorIsShutdown();
	}

	public void testShuttingDownPoolChangesItsStateToShutdown() {

		givenThreadPool();

		whenShutdown();

		thenPoolIsShutdown();
	}

	public void testShuttingDownPoolWillResultInNewInstance() {

		givenThreadPool();

		whenShutdown();

		thenPoolInstanceIsNew();
	}

	/**
	 * This makes sure that shutting down works.
	 */
	public void testShuttingDownIsOK() {

		ExecutorService executorService = ThreadPool.getInstance().getExecutor();
		ScheduledExecutorService scheduledExecutorService = ThreadPool.getInstance().getScheduledExecutor();
		ThreadPool.getInstance().shutdown();
		assertTrue(executorService.isShutdown());
		assertTrue(scheduledExecutorService.isShutdown());
	}

	private void givenThreadPool() {
		threadPool = ThreadPool.getInstance();
	}

	private ExecutorService getExecutor() {
		return threadPool.getExecutor();
	}

	private ScheduledExecutorService getScheduledExecutor() {
		return threadPool.getScheduledExecutor();
	}

	private void thenHasExecutor() {
		assertNotNull(getExecutor());
	}

	private void thenHasScheduledExecutor() {
		assertNotNull(getScheduledExecutor());
	}

	private void whenShutdown() {
		threadPool.shutdown();
	}

	private void thenExecutorIsShutdown() {
		assertTrue(getExecutor().isShutdown());
	}

	private void thenScheduledExecutorIsShutdown() {
		assertTrue(getScheduledExecutor().isShutdown());
	}

	private void thenPoolIsShutdown() {
		assertTrue(threadPool.isShutdown());
	}

	private void thenPoolInstanceIsNew() {
		assertNotSame(threadPool, ThreadPool.getInstance());
	}
}
