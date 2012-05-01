package seeemilyplay.core.db;

import seeemilyplay.core.db.DatabaseConfig;

import junit.framework.TestCase;

public class UrlBuilderTest extends TestCase {

	private DatabaseConfig config;
	private String url;

	protected void setUp() throws Exception {
		super.setUp();
		config = new DatabaseConfig();
		url = null;
	}

	public void testCanProperlyBuildAPermanentURL() {

		givenConfig("testName", true);

		whenURLBuilt();

		thenUrlIs("jdbc:hsqldb:file:testName;shutdown=true;autocommit=true");
	}

	public void testCanProperlyBuildAnInMemoryURL() {

		givenConfig("testName", false);

		whenURLBuilt();

		thenUrlIs("jdbc:hsqldb:mem:testName;shutdown=true;autocommit=true");
	}

	private void givenConfig(
			String name,
			boolean isPermanent) {
		DatabaseConfig config = getConfig();
		config.setName(name);
		config.setPermanent(isPermanent);
	}

	private void whenURLBuilt() {
		createUrl();
	}

	private void createUrl() {
		UrlBuilder builder = createBuilder();
		createUrl(builder);
	}

	private void createUrl(UrlBuilder builder) {
		url = builder.createUrl();
	}

	private UrlBuilder createBuilder() {
		DatabaseConfig config = getConfig();
		return new UrlBuilder(config);
	}

	private DatabaseConfig getConfig() {
		return config;
	}

	private void thenUrlIs(String expectedUrl) {
		assertEquals(expectedUrl, url);
	}
}
