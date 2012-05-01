package seeemilyplay.quizzer.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state of a network.
 */
public final class NetworkState {

	private String id;
	private List<GraphState> graphStates;

	public NetworkState() {
		super();
	}

	public NetworkState(String id) {
		this(id, new ArrayList<GraphState>());
	}

	public NetworkState(
			String id,
			List<GraphState> graphStates) {
		this();
		setId(id);
		setGraphStates(graphStates);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GraphState> getGraphStates() {
		return graphStates;
	}

	public void setGraphStates(List<GraphState> graphStates) {
		this.graphStates = graphStates;
	}

	public int hashCode() {
		return id==null ? 0 : id.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		NetworkState other = (NetworkState)obj;
		return id!=null ? id.equals(other.id) : other.id==null;
	}
}
