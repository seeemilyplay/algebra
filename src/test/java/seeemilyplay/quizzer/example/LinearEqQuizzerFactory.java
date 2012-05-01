package seeemilyplay.quizzer.example;

import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.cream.CreamQuizzerFactory;

public final class LinearEqQuizzerFactory {

	private final String id;
	private final QuizzerConfig config;
	private CreamQuizzerFactory<LinearEquation> creamQuizzerFactory;

	public LinearEqQuizzerFactory(
			String id,
			QuizzerConfig config) {
		super();
		this.id = id;
		this.config = config;
	}

	public Quizzer<LinearEquation> createLinearEqQuizzer() {

		initialise();

		defineValues();
		defineLinks();

		return createQuizzer();
	}

	private void initialise() {
		LinearEqParser linearEqParser = new LinearEqParser();
		creamQuizzerFactory = new CreamQuizzerFactory<LinearEquation>(
				id,
				config,
				linearEqParser);
	}

	private void defineValues() {
		defineValue("x");
		defineValue("mx");
		defineValue("x+c");
		defineValue("x-c");
		defineValue("mx+c");
		defineValue("mx-c");
		defineValue("-x");
		defineValue("-mx");
		defineValue("-x+c");
		defineValue("-x-c");
		defineValue("-mx+c");
		defineValue("-mx-c");
	}

	private void defineValue(String description) {
		creamQuizzerFactory.add(description);
	}

	private void defineLinks() {
		link("x", "-x", "x+c", "mx");
		link("mx", "-mx", "mx+c");
		link("x+c", "-x+c", "x-c", "mx+c");
		link("x-c", "-x-c", "mx-c");
		link("mx+c", "mx-c", "-mx+c");
		link("mx-c", "-mx-c");
		link("-x", "-mx", "-x+c");
		link("-mx", "-mx+c");
		link("-x+c", "-x-c", "-mx+c");
		link("-x-c", "-mx-c");
		link("-mx+c", "-mx-c");
	}

	private void link(String parent, String... children) {
		creamQuizzerFactory.link(parent, children);
	}

	private Quizzer<LinearEquation> createQuizzer() {
		return creamQuizzerFactory.createQuizzer();
	}
}