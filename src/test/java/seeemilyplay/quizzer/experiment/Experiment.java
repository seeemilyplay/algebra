package seeemilyplay.quizzer.experiment;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.db.DatabaseConfig;
import seeemilyplay.core.threads.ThreadPool;
import seeemilyplay.quizzer.QuizQuestion;
import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.StatisticsModel;
import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.example.LinearEqQuizzerFactory;
import seeemilyplay.quizzer.example.LinearEquation;

public class Experiment {

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					try {
						DatabaseConfig dbConfig = Database.getConfig();
						dbConfig.setPermanent(true);
						dbConfig.setName("db/experiment");
		
						BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
						QuizzerConfig config = new QuizzerConfig();
		
						LinearEqQuizzerFactory factory = new LinearEqQuizzerFactory("experiment", config);
		
						Quizzer<LinearEquation> quizzer = factory.createLinearEqQuizzer();
						StatisticsModel<LinearEquation> stats = quizzer.getStatisticsModel();
		
						QuizQuestion<LinearEquation> question = quizzer.next();
						int questionCount = 0;
						print(question);
		
						while(true) {
		
							questionCount++;
							System.out.println("question count = " + questionCount);
							System.out.println("overall prb = " + stats.getOverallProbability());
							if (stats.getOverallProbability().getValue()==1) {
								break;
							}
							System.out.println("Answer correctly (Y|N|Q) ??");
							String answer = reader.readLine().trim();
							if ("Q".equalsIgnoreCase(answer)) {
								break;
							}
							boolean correct = "Y".equalsIgnoreCase(answer);
		
							if (correct) {
								question.answerCorrectly();
							} else {
								question.answerIncorrectly();
							}
		
							question = quizzer.next();
							print(question);
						}
		
						ThreadPool.getInstance().shutdown();
						Database.shutdown();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static void print(QuizQuestion<LinearEquation> question) {
		LinearEquation next = question.getQuestion();
		System.out.println("Next Question: m=" + next.getM() + " c=" + next.getC());
	}
}
