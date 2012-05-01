package seeemilyplay.core.db;

/**
 * Data structure storing the database
 * configuration.
 */
public final class DatabaseConfig {

	private static final String DEFAULT_DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
	private static final boolean IS_PERMANENT_BY_DEFAULT = false;
	private static final String DEFAULT_NAME = "test";

	private String driverClassName = DEFAULT_DRIVER_CLASS_NAME;
	private boolean isPermanent = IS_PERMANENT_BY_DEFAULT;
	private String name = DEFAULT_NAME;

	protected DatabaseConfig() {
		super();
	}

	public synchronized String getDriverClassName() {
		return driverClassName;
	}

	public synchronized void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public synchronized boolean isPermanent() {
		return isPermanent;
	}

	public synchronized void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}
}
