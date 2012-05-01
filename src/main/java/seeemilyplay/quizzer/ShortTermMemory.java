package seeemilyplay.quizzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;
import seeemilyplay.quizzer.network.Graph;

final class ShortTermMemory<Q extends Question> {

	private final int size;
	private final List<Q> questions = new ArrayList<Q>();

	public ShortTermMemory(int size) {
		this.size = size;
	}

	public boolean isRecent(Q question) {
		return questions.contains(question);
	}

	public void save(Q question) {
		forgetEldestIfMemoryIsFull();
		remember(question);
	}

	private void forgetEldestIfMemoryIsFull() {
		if (isMemoryFull()) {
			forgetEldest();
		}
	}

	private boolean isMemoryFull() {
		return (questions.size()>=size);
	}

	private void forgetEldest() {
		questions.remove(0);
	}

	private void remember(Q question) {
		questions.add(question);
	}

	public boolean hasNonRecentQuestions(Graph<Q> graph) {
		for (Q question : getAllQuestions(graph)) {
			if (!isRecent(question)) {
				return true;
			}
		}
		return false;
	}

	public Set<Q> getNonRecentQuestions(Graph<Q> graph) {
		Set<Q> nonRecentQuestions = new HashSet<Q>();
		for (Q question : getAllQuestions(graph)) {
			if (!isRecent(question)) {
				nonRecentQuestions.add(question);
			}
		}
		return nonRecentQuestions;
	}

	private Set<Q> getAllQuestions(Graph<Q> graph) {
		Set<Q> questions = new HashSet<Q>();
		QuestionSpace<Q> questionSpace = graph.getQuestionSpace();
		for (Q question : questionSpace) {
			questions.add(question);
		}
		return Collections.unmodifiableSet(questions);
	}
}
