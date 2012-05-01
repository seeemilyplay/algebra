package seeemilyplay.quizzer.definition;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;

/**
 * A data structure object that holds information
 * about the value of a <code>Network</code> node.
 *
 * @param <Q> the question type
 */
public final class Value<Q extends Question> {

	private final String id;
	private final QuestionSpace<Q> questionSpace;

	public Value(
			String id,
			QuestionSpace<Q> questionSpace) {
		this.id = id;
		this.questionSpace = questionSpace;
		validate();
	}

	private void validate() {
		validateId();
		validateQuestionSpace();
	}

	private void validateId() {
		checkNotNull(id);
	}

	private void validateQuestionSpace() {
		checkNotNull(questionSpace);
	}

	private void checkNotNull(Object o) {
		if (o==null) {
			throw new NullPointerException();
		}
	}

	public String getId() {
		return id;
	}

	public QuestionSpace<Q> getQuestionSpace() {
		return questionSpace;
	}

	public int hashCode() {
		return id.hashCode();
	}

	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if (!getClass().equals(o.getClass())) {
			return false;
		}
		Value<?> other = (Value<?>)o;
		return id.equals(other.id);
	}
}
