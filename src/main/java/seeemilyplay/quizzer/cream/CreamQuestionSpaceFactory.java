package seeemilyplay.quizzer.cream;

import java.util.HashSet;
import java.util.Set;

import jp.ac.kobe_u.cs.cream.DefaultSolver;
import jp.ac.kobe_u.cs.cream.Network;
import jp.ac.kobe_u.cs.cream.Solution;
import jp.ac.kobe_u.cs.cream.SolutionHandler;
import jp.ac.kobe_u.cs.cream.Solver;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;
import seeemilyplay.quizzer.core.QuestionSpaceFactory;

/**
 * Creates question spaces that are defined using the
 * Cream constraint solver packages.
 *
 * @param Q the type of the questions
 */
public final class CreamQuestionSpaceFactory<Q extends Question> implements QuestionSpaceFactory<Q> {

	private final CreamSpaceDefinition<Q> definition;

	private Network network;
	private CreamSolutionInterpretter<Q> solutionInterpretter;
	private Solver solver;
	private Set<Q> questions = new HashSet<Q>();

	public CreamQuestionSpaceFactory(CreamSpaceDefinition<Q> definition) {
		this.definition = definition;
	}

	public QuestionSpace<Q> createQuestionSpace() {
		setupNetwork();
		solveAndCreateQuestions();
		return getQuestionSpace();
	}

	private void setupNetwork() {
		createNetwork();
		setupConstraints();
	}

	public void solveAndCreateQuestions() {
		createSolver();
		saveSolutionsAsQuestions();
	}

	private void createNetwork() {
		network = new Network();
	}

	private void setupConstraints() {
		solutionInterpretter = definition.installConstraints(network);
	}

	private void createSolver() {
		solver = new DefaultSolver(network);
	}

	private void saveSolutionsAsQuestions() {
		clearQuestionsCache();
		solver.findAll(new QuestionSaver());
	}

	private void clearQuestionsCache() {
		questions.clear();
	}

	private void saveSolutionAsQuestion(Solution solution) {
		Q question = interpretAsQuestion(solution);
		saveQuestion(question);
	}

	private Q interpretAsQuestion(Solution solution) {
		return solutionInterpretter.convertToQuestion(solution);
	}

	private void saveQuestion(Q question) {
		questions.add(question);
	}

	private QuestionSpace<Q> getQuestionSpace() {
		return new QuestionSpace<Q>(questions);
	}

	private class QuestionSaver implements SolutionHandler {

		public QuestionSaver() {
			super();
		}

		public void solved(Solver solver, Solution solution) {
			if (solution!=null) {
				saveSolutionAsQuestion(solution);
			}
		}
	}
}
