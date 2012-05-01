package seeemilyplay.algebra.progress;

import seeemilyplay.algebra.progress.text.ProgressPhrases;

final class ExpertAward extends BaseAward {

	private static final String SMALL_TEMPLATE_FILE_NAME =
		"/seeemilyplay/algebra/progress/goldmedal_small.xml";
	private static final String LARGE_TEMPLATE_FILE_NAME =
		"/seeemilyplay/algebra/progress/goldmedal_large.xml";

	public ExpertAward() {
		super();
	}

	protected String getNameFromPhrases(ProgressPhrases phrases) {
		return phrases.getExpertAwardName();
	}

	protected String getSmallTemplateFileName() {
		return SMALL_TEMPLATE_FILE_NAME;
	}

	protected String getLargeTemplateFileName() {
		return LARGE_TEMPLATE_FILE_NAME;
	}
}
