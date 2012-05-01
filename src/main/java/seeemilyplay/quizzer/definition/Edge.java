package seeemilyplay.quizzer.definition;

/**
 * Defines an edge in a <code>Network</code>.
 */
public final class Edge {

	private final String a;
	private final String b;

	public Edge(String a, String b) {
		this.a = a;
		this.b = b;
		validate();
	}

	private void validate() {
		validateA();
		validateB();
	}

	private void validateA() {
		checkNotNull(a);
	}

	private void validateB() {
		checkNotNull(b);
	}

	private void checkNotNull(Object o) {
		if (o==null) {
			throw new NullPointerException();
		}
	}

	public String getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public int hashCode() {
		return a.hashCode() + b.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final Edge other = (Edge) obj;
		return (a.equals(other.a) && b.equals(other.b));
	}
}
