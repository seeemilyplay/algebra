package seeemilyplay.quizzer.db;

import seeemilyplay.quizzer.network.Network;

/**
 * Creates a <code>NetworkPersister</code>.
 */
public final class NetworkPersisterFactory {


	public NetworkPersisterFactory() {
		super();
	}

	public NetworkPersister createNetworkPersister(
			String id,
			Network<?> network) {
		NetworkDAO networkDao = new NetworkDAO();
		return new NetworkPersisterImpl(
				networkDao,
				id,
				network);
	}
}
