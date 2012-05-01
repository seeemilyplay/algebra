package seeemilyplay.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;

/**
 * This class is used to manage the database.
 *
 * @deprecated
 */
public class DBConnectionManager {

	private static final String DB_USERNAME = "sa";
	private static final String DB_PASSWORD = "";
	private static final String DB_URL_PREFIX = "jdbc:hsqldb:";
	private static final String DB_IN_MEM_URL_PREF = "mem:";
	private static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
	private static final String SHUTDOWN_SQL = "SHUTDOWN";

	static {
		loadDriverClass();
	}

	private static boolean isTestMode = false;
	private static String dbName;
	private static volatile DBConnectionManager instance;

	private final String dbUrl;

	/**
	 * Creates the singleton <code>DBConnectionManager</code>
	 * instance.
	 *
	 * @param name			the name of the database
	 * @param isPermanent	true if permanent
	 */
	private DBConnectionManager(
			String dbName,
			boolean isPermanent) {
		this.dbUrl = createDbUrl(dbName, isPermanent);
	}

	/**
	 * This creates the DB URL
	 *
	 * @param name			the name of the database
	 * @param isPermanent	true if permanent
	 */
	private static String createDbUrl(
			String dbName,
			boolean isPermanent) {
		return (DB_URL_PREFIX
				+ ((!isPermanent) ? DB_IN_MEM_URL_PREF: "")
				+ dbName);
	}

	/**
	 * Clears out all of the information relating
	 * to the current <code>DBConnectionManager</code>
	 * instance.
	 */
	private synchronized static void clear() {
		instance = null;
		isTestMode = false;
		dbName = null;
	}

	/**
	 * Sets the database name.  This should
	 * be called just once, before the
	 * <code>getInstance</code> method is called.
	 *
	 * @param dbName	the name of the database
	 */
	public synchronized static void setDbName(String dbName) {
		if (instance!=null) {
			throw new Error();
		}
		DBConnectionManager.dbName = dbName;
	}

	/**
	 * Sets the database to test mode, which will
	 * mean that it isn't permanently persisted,
	 * but just held in memory.
	 */
	public synchronized static void setTestMode() {
		if (instance!=null) {
			throw new Error();
		}
		isTestMode = true;
	}

	/**
	 * Returns the singleton <code>DBConnectionManager</code>
	 * instance.
	 *
	 * @return	the <code>ConnectionManager</code>
	 */
	public synchronized static DBConnectionManager getInstance() {
		if (instance==null) {
			if (dbName==null) {
				throw new Error();
			}
			instance = new DBConnectionManager(
					dbName,
					!isTestMode);
		}
		return instance;
	}

	/**
	 * Gets a database connection from the manager.
	 *
	 * @return	a database connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return createConnection();
	}

	/**
	 * Shuts down the database instance
	 *
	 * @throws SQLException
	 */
	public void shutdown() throws SQLException {
		Connection conn = getConnection();
		try {
			Statement stm = conn.createStatement();
			try {
				stm.execute(SHUTDOWN_SQL);
			} finally {
				stm.close();
			}
		} finally {
			returnConnection(conn);
		}

		clear();
	}

	/**
	 * Returns a database connection to the manager
	 * so that it may be possibly re-used.
	 *
	 * @param conn
	 * @throws SQLException
	 */
	public void returnConnection(Connection conn) throws SQLException {
		conn.close();
	}

	/**
	 * This creates a brand new connection.
	 *
	 * @return a new connection
	 * @throws SQLException
	 */
	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(
				dbUrl,
				DB_USERNAME,
				DB_PASSWORD);
	}

	/**
	 * This loads up the driver class
	 */
	private static void loadDriverClass() {
		ErrorHandler.getInstance().run(new ExceptionThrowingTask() {
			public void run() throws ClassNotFoundException {
				Class.forName(DRIVER_CLASS_NAME);
			}
		});
	}
}
