package seeemilyplay.quizzer.db;

import hu.netmind.persistence.Store;
import hu.netmind.persistence.Transaction;
import hu.netmind.persistence.TransactionTracker;

import seeemilyplay.core.db.Database;

/**
 * Persists network state at the lowest level of
 * abstraction.
 */
final class NetworkDAO {

	public NetworkDAO() {
		super();
	}

	public void saveNetwork(NetworkState network) {
		Transaction tx = getTransaction();
		tx.begin();
		saveNetworkAndGraphs(network);
		tx.commit();
	}

	private Transaction getTransaction() {
		Store store = getStore();
		TransactionTracker tt = store.getTransactionTracker();
		return tt.getTransaction(TransactionTracker.TX_REQUIRED);
	}

	private void saveNetworkAndGraphs(NetworkState network) {
		Store store = getStore();
		store.save(network);
		saveGraphs(network);
	}

	private void saveGraphs(NetworkState network) {
		Store store = getStore();
		for (GraphState graph : network.getGraphStates()) {
			store.save(graph);
		}
	}

	public NetworkState loadNetwork(String id) {
		Store store = getStore();
		return (NetworkState)store.findSingle(
				"find networkState where id=?",
				new Object[] {id});
	}

	private Store getStore() {
		return Database.getStore();
	}
}
