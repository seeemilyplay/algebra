package seeemilyplay.core.db;

/**
 * Builds the database connection URL.
 */
final class UrlBuilder {

	private static final String DB_URL_PREFIX = "jdbc:hsqldb:";
	private static final String DB_IN_MEM_URL_PREFIX = "mem:";
	private static final String DB_FILE_URL_PREFIX = "file:";
	private static final String DB_URL_SUFFIX = ";shutdown=true;autocommit=true";

	private final DatabaseConfig config;

	public UrlBuilder(DatabaseConfig config) {
		this.config = config;
	}

	public String createUrl() {
		return isPermanent() ?
				createPermanentUrl() :
					createTemporaryUrl();
	}

	private String createPermanentUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(DB_URL_PREFIX);
		sb.append(DB_FILE_URL_PREFIX);
		sb.append(getName());
		sb.append(DB_URL_SUFFIX);
		return sb.toString();
	}

	private String createTemporaryUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(DB_URL_PREFIX);
		sb.append(DB_IN_MEM_URL_PREFIX);
		sb.append(getName());
		sb.append(DB_URL_SUFFIX);
		return sb.toString();
	}

	private boolean isPermanent() {
		return config.isPermanent();
	}

	private String getName() {
		return config.getName();
	}
}
