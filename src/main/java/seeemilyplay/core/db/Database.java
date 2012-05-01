package seeemilyplay.core.db;

import hu.netmind.persistence.Store;

/**
 * Static class for accessing and configuring the database.
 */
public final class Database {

	private static final DatabaseConfig config =
		new DatabaseConfig();
	private static Store store;

	private Database() {
		super();
	}

	public static DatabaseConfig getConfig() {
		return config;
	}

	public synchronized static Store getStore() {
		createStoreIfRequired();
		return store;
	}

	public synchronized static void shutdown() {
		if (store!=null) {
			store.close();
			store = null;
		}
	}

	private static void createStoreIfRequired() {
		if (store==null) {
			createStore();
		}
	}

	private static void createStore() {
		store = new Store(getDriverClassName(), getUrl());
	}

	private static String getUrl() {
		UrlBuilder urlBuilder = new UrlBuilder(config);
		return urlBuilder.createUrl();
	}

	private static String getDriverClassName() {
		return config.getDriverClassName();
	}
}
