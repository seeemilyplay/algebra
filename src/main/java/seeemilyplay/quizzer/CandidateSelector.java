package seeemilyplay.quizzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.network.Graph;
import seeemilyplay.quizzer.network.Network;

final class CandidateSelector<Q extends Question> {

	private final QuizzerConfig config;
	private final ShortTermMemory<Q> shortTermMemory;
	private final Network<Q> network;

	private final List<Graph<Q>> candidates = new ArrayList<Graph<Q>>();

	public CandidateSelector(
			QuizzerConfig config,
			ShortTermMemory<Q> shortTermMemory,
			Network<Q> network) {
		this.config = config;
		this.shortTermMemory = shortTermMemory;
		this.network = network;
	}

	public List<Graph<Q>> selectCandidates() {
		initCandidates();
		selectEasyOnes();
		selectOnesToLearnNext();
		if (isMoreCandidatesRequired()) {
			selectEasiestEarlyOnes();
		}
		return getCandidates();
	}

	private void initCandidates() {
		candidates.clear();
	}

	private boolean isMoreCandidatesRequired() {
		return candidates.isEmpty();
	}

	private List<Graph<Q>> getCandidates() {
		return Collections.unmodifiableList(candidates);
	}

	private void selectEasyOnes() {
		List<Graph<Q>> easyOnes = new ArrayList<Graph<Q>>();
		for (Graph<Q> graph : network) {
			if (isEasy(graph) && isNotRecent(graph)) {
				easyOnes.add(graph);
			}
		}
		orderHighestProbabilityFirst(easyOnes);
		candidates.addAll(easyOnes);
	}

	private void selectOnesToLearnNext() {
		List<Graph<Q>> onesToLearnNext = new ArrayList<Graph<Q>>();
		for (Graph<Q> graph : network) {
			if (hasEasyParents(graph) && isNotRecent(graph)) {
				onesToLearnNext.add(graph);
			}
		}
		orderLowestProbabilityFirst(onesToLearnNext);
		candidates.addAll(onesToLearnNext);
	}

	private void selectEasiestEarlyOnes() {
		List<Graph<Q>> easiestEarlyOnes = new ArrayList<Graph<Q>>();
		easiestEarlyOnes = getEarliestOnes();
		candidates.add(easiestEarlyOnes.get(0));
	}

	private List<Graph<Q>> getEarliestOnes() {
		return getEarliestOnes(getFirstLevel());
	}

	private List<Graph<Q>> getEarliestOnes(List<Graph<Q>> level) {
		List<Graph<Q>> earliestOnes = getEarliestOnesFromThisLevel(level);
		if (earliestOnes.isEmpty()) {
			earliestOnes = getEarliestOnesFromLowerLevels(level);
		}
		return earliestOnes;
	}

	private List<Graph<Q>> getEarliestOnesFromThisLevel(List<Graph<Q>> level) {
		List<Graph<Q>> shuffledLevel = new ArrayList<Graph<Q>>(level);
		Collections.shuffle(shuffledLevel);
		List<Graph<Q>> earliestOnes = new ArrayList<Graph<Q>>();
		for (Graph<Q> graph : shuffledLevel) {
			if (isNotRecent(graph)) {
				earliestOnes.add(graph);
			}
		}
		orderHighestProbabilityFirst(earliestOnes);
		return Collections.unmodifiableList(earliestOnes);
	}

	private List<Graph<Q>> getEarliestOnesFromLowerLevels(List<Graph<Q>> level) {
		List<Graph<Q>> nextLevel = getNextLevel(level);
		return getEarliestOnes(nextLevel);
	}

	private List<Graph<Q>> getFirstLevel() {
		Set<Graph<Q>> firstLevel = new HashSet<Graph<Q>>();
		for (int i=0; i<network.getRootCount(); i++) {
			Graph<Q> root = network.getRoot(i);
			firstLevel.add(root);
		}
		return Collections.unmodifiableList(
				new ArrayList<Graph<Q>>(firstLevel));
	}

	private List<Graph<Q>> getNextLevel(List<Graph<Q>> level) {
		Set<Graph<Q>> nextLevel = new HashSet<Graph<Q>>();
		for (Graph<Q> graph : level) {
			nextLevel.addAll(graph.getChildren());
		}
		return Collections.unmodifiableList(
				new ArrayList<Graph<Q>>(nextLevel));
	}

	private boolean hasEasyParents(Graph<Q> graph) {
		for (Graph<Q> parent : graph.getParents()) {
			if (!isEasy(parent)) {
				return false;
			}
		}
		return true;
	}

	private boolean isEasy(Graph<Q> graph) {
		return (graph.getProbability().getValue() >= getTargetProbability());
	}

	private double getTargetProbability() {
		return config.getTargetProbability();
	}

	private boolean isNotRecent(Graph<Q> graph) {
		return shortTermMemory.hasNonRecentQuestions(graph);
	}

	private void orderHighestProbabilityFirst(List<Graph<Q>> list) {
		orderLowestProbabilityFirst(list);
		Collections.reverse(list);
	}

	private void orderLowestProbabilityFirst(List<Graph<Q>> list) {
		Collections.shuffle(list);
		Collections.sort(list, new ProbabilityComparator());
	}

	private class ProbabilityComparator implements Comparator<Graph<Q>> {

		public int compare(Graph<Q> o1, Graph<Q> o2) {
			return  (int)(
					o1.getProbability().getValue()
					- o2.getProbability().getValue());
		}
	}
}
