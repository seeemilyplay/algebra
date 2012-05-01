package seeemilyplay.core.international.text;

import junit.framework.TestCase;

import seeemilyplay.core.international.LocalFactory;

public class SectionTest extends TestCase {

	private static final String UK = "UK";

	private LocalFactory<String> localFactory;
	private Section<String> section;


	protected void setUp() throws Exception {
		super.setUp();
		localFactory = null;
		section = null;
	}

	public void testSectionReturnsUKPhrases() {

		givenALocalFactory();

		whenSectionCreated();

		thenSectionContainsUKPhrases();
	}

	public void testSectionCachesPhrasesInstance() {

		givenALocalFactory();

		whenSectionCreated();

		thenPhrasesInstanceIsSameEachCall();
	}

	private void givenALocalFactory() {
		createLocalFactory();
	}

	private void createLocalFactory() {
		localFactory = new LocalFactory<String>() {
			public String createUK() {
				return UK;
			}
		};
	}

	private void whenSectionCreated() {
		section = new Section<String>(localFactory);
	}

	private void thenSectionContainsUKPhrases() {
		assertEquals(UK, section.getPhrases());
	}

	private void thenPhrasesInstanceIsSameEachCall() {
		String firstCall = section.getPhrases();
		String secondCall = section.getPhrases();
		assertTrue(firstCall==secondCall);
	}
}
