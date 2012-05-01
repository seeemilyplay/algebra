package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.cream.CreamQuizzerFactory;

final class JigsawQuizzerFactory {

	private final String id;
	private final QuizzerConfig config;
	private final int pieceCount;
	private CreamQuizzerFactory<Puzzle> creamQuizzerFactory;

	public JigsawQuizzerFactory(
			String id,
			QuizzerConfig config,
			int pieceCount) {
		super();
		this.id = id;
		this.config = config;
		this.pieceCount = pieceCount;
	}

	public Quizzer<Puzzle> createJigsawQuizzer() {

		initialise();

		defineQuestions();
		
		return createQuizzer();
	}

	private void initialise() {
		PuzzleParser puzzleParser = new PuzzleParser();
		creamQuizzerFactory = new CreamQuizzerFactory<Puzzle>(
				id,
				config,
				puzzleParser);
	}

	private void defineQuestions() {
		defineArithmeticSequences();
		defineGeometricSequences();
		defineSquaresSequences();
	}
	
	private void defineArithmeticSequences() {
		
		link(
				defineGeometricSequencesWithAllPositiveNumbers(),
				defineGeometricSequencesWithNegativeDifferences(),
				defineGeometricSequencesWithNegativeFirstTermsAndAnyDifferences());
	}
	
	private Chain defineGeometricSequencesWithAllPositiveNumbers() {
		
		return defineChain(
				"ap: 1st=0->1 d=1",
				"ap: 1st=0->1 d=2",
				"ap: 1st=0->1 d=3->5",
				"ap: 1st=2->5 d=1->5",
				"ap: 1st=0 d=1 true",
				"ap: 1st=0 d=2->5 true",
				"ap: 1st=1->5 d=1->5 true");
	}
	
	private Chain defineGeometricSequencesWithNegativeDifferences() {
		
		int firstForPosDiff1 = pieceCount;
		int firstForPosDiff2 = pieceCount * 2;
		
		return defineChain(
				"ap: 1st=" + firstForPosDiff1 + "->" + (firstForPosDiff1+5) + " d=-1",
				"ap: 1st=" + firstForPosDiff2 + "->" + (firstForPosDiff2+5) + " d-2",
				"ap: 1st=0->" + (firstForPosDiff1-1) + " d=-1",
				"ap: 1st=0->" + (firstForPosDiff2-1) + " d=-2",
				"ap: 1st=0->10 d=-3->-5",
				"ap: 1st=0 d=-1 true",
				"ap: 1st=0 d=-2->-5 true",
				"ap: 1st=-1->-5 d=-1->5 true");
	}
	
	private Chain defineGeometricSequencesWithNegativeFirstTermsAndAnyDifferences() {
		
		return defineChain(
				"ap: 1st=-1->-5 d=1->5",
				"ap: 1st=-1->-5 d=1->5 true",
				"ap: 1st=-1->-5 d=-1->-5",
				"ap: 1st=-1->-5 d=-1->-5 true");
		
	}
	
	private void defineGeometricSequences() {
		
		link(
				defineGeometricSequencesBasedOnTwo(),
				defineGeometricSequencesBasedOnThree(),
				defineGeometricSequencesBasedOnTen());
	}
	
	private Chain defineGeometricSequencesBasedOnTwo() {
		
		return defineChain(
				"gp: 1st=1 d=2",
				"gp: 1st=2 d=2");
	}
	
	private Chain defineGeometricSequencesBasedOnThree() {
		
		return defineChain(
				"gp: 1st=1 d=3",
				"gp: 1st=3 d=3");
	}	
	
	private Chain defineGeometricSequencesBasedOnTen() {
		
		return defineChain(
				"gp: 1st=1 d=10",
				"gp: 1st=10 d=10");
	}
	
	private Chain defineSquaresSequences() {
		
		return defineChain(
				"sq: 1st=1",
				"sq: 1st=4",
				"sq: 1st=9");
	}
	
	private void link(Chain ... chains) {
		Chain previous = null;
		for (Chain chain : chains) {
			if (previous!=null) {
				link(previous.getHardest(), chain.getEasiest());
			}
			previous = chain;
		}
	}
	
	private Chain defineChain(String ... descriptions) {
		String previous = null;
		for (String description : descriptions) {
			defineWithPieceCounts(description);
			if (previous!=null) {
				link(previous, description);
			}
			previous = description;
		}
		return new Chain(descriptions);
	}
	
	private void defineWithPieceCounts(String description) {
		define(description);
		String previous = description;
		for (int i=2; i<=pieceCount; i++) {
			String current = description + " h=" + i;
			define(description);
			link(previous, current);
			previous = current;
		}
	}
	
	private void define(String description) {
		creamQuizzerFactory.add(description);
	}

	private void link(String parent, String... children) {
		creamQuizzerFactory.link(parent, children);
	}

	private Quizzer<Puzzle> createQuizzer() {
		return creamQuizzerFactory.createQuizzer();
	}
	
	private static class Chain {
		
		private String easiest;
		private String hardest;
		
		public Chain(String... descriptions) {
			easiest = descriptions[0];
			hardest = descriptions[descriptions.length-1];
		}
		
		public String getEasiest() {
			return easiest;
		}
		
		public String getHardest() {
			return hardest;
		}
	}
}