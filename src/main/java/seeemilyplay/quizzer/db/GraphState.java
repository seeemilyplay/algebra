package seeemilyplay.quizzer.db;

/**
 * State of a particular graph node.
 */
public final class GraphState {

	private String id;
	private double probability;

	public GraphState() {
		super();
	}

	public GraphState(String id, double probability) {
		this();
		setId(id);
		setProbability(probability);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
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
		GraphState other = (GraphState)obj;
		return id!=null ? id.equals(other.id) : other.id==null;
	}
}
