package seeemilyplay.quizzer.definition;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.quizzer.core.Question;

/**
 * Stores <code>Values</code> on behalf of
 * the <code>NetworkDefinition</code>.
 *
 * @param <Q> the question type
 */
final class Values<Q extends Question> {

	private final Map<String,Value<Q>> valuesById =
		new HashMap<String,Value<Q>>();

	public Values() {
		super();
	}

	public void add(Value<Q> value) {
		check(value);
		store(value);
	}

	private void check(Value<Q> value) {
		if (contains(value)) {
			throw new IllegalStateException();
		}
	}

	private boolean contains(Value<Q> value) {
		String id = value.getId();
		return valuesById.containsKey(id);
	}

	private void store(Value<Q> node) {
		valuesById.put(node.getId(), node);
	}

	public Value<Q> getValueForId(String id) {
		return valuesById.get(id);
	}
}

