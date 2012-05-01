package seeemilyplay.quizzer.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Defines a collection of questions that are ordered and unique.
 *
 * @param <Q> the question type
 */
public final class QuestionSpace<Q> implements Iterable<Q>{

	private final List<Q> questions;

	public QuestionSpace(Set<Q> questions) {
		this.questions = Collections.unmodifiableList(new ArrayList<Q>(questions));
	}

	public int getQuestionCount() {
		return questions.size();
	}

	public Iterator<Q> iterator() {
		return questions.iterator();
	}
}
