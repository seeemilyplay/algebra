package seeemilyplay.core.international.text;

import junit.framework.TestCase;

import seeemilyplay.core.international.LocalFactory;

public class PhraseBookTest extends TestCase {

	private static final String UK = "UK";

	private LocalFactory<String> phraseFactory;

	protected void setUp() throws Exception {
		super.setUp();
		phraseFactory = null;
	}

	public void testRegistrationAddsPhrasesToBook() {

		givenAPhraseFactory();

		whenFactoryIsRegistered();

		thenBookContainsPhrases();
	}

	public void testUnregistrationRemovesPhrasesFromBook() {

		givenARegisteredPhraseFactory();

		whenFactoryIsUnregistered();

		thenBookDoesntContainPhrases();
	}

	private void givenAPhraseFactory() {
		createPhraseFactory();
	}

	private void givenARegisteredPhraseFactory() {
		createPhraseFactory();
		registerFactory();
	}

	private void createPhraseFactory() {
		phraseFactory = new LocalFactory<String>() {
			public String createUK() {
				return UK;
			}
		};
	}

	private void whenFactoryIsRegistered() {
		registerFactory();
	}

	private void registerFactory() {
		PhraseBook.registerPhrases(String.class, phraseFactory);
	}

	private void whenFactoryIsUnregistered() {
		PhraseBook.unregisterPhrases(String.class);
	}

	private void thenBookContainsPhrases() {
		assertEquals(UK, PhraseBook.getPhrases(String.class));
	}

	private void thenBookDoesntContainPhrases() {
		assertNull(PhraseBook.getPhrases(String.class));
	}
}
