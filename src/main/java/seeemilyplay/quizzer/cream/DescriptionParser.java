package seeemilyplay.quizzer.cream;

import seeemilyplay.quizzer.core.Question;

public interface DescriptionParser <Q extends Question> {

	public CreamSpaceDefinition<Q> createSpaceDefinition(String description);
}
