package seeemilyplay.core.international;

import junit.framework.TestCase;

public class InternationalFactoryTest extends TestCase {

	private static final String UK = "UK";

	private LocalFactory<String> localFactory;
	private InternationalFactory<String> internationalFactory;
	private String created;

	protected void setUp() throws Exception {
		super.setUp();
		localFactory = null;
		internationalFactory = null;
		created = null;
	}

	public void testWhenCreateCalledUKInstanceIsReturned() {

		givenAnInternationalFactory();

		whenCreateCalled();

		thenUKInstanceReturned();
	}

	public void testWhenStaticCreateCalledUKInstanceIsReturned() {

		givenALocalFactory();

		whenStaticCreatedCalled();

		thenUKInstanceReturned();
	}

	private void givenALocalFactory() {
		createLocalFactory();
	}

	private void givenAnInternationalFactory() {
		createLocalFactory();
		createInternationalFactory();
	}

	private void createLocalFactory() {
		localFactory = new LocalFactory<String>() {
			public String createUK() {
				return UK;
			}
		};
	}

	private void createInternationalFactory() {
		internationalFactory = new InternationalFactory<String>(localFactory);
	}

	private void whenCreateCalled() {
		created = internationalFactory.create();
	}

	private void whenStaticCreatedCalled() {
		created = InternationalFactory.create(localFactory);
	}

	private void thenUKInstanceReturned() {
		assertEquals(UK, created);
	}
}
