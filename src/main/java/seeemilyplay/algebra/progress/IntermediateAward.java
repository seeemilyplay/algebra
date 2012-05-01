package seeemilyplay.algebra.progress;

import seeemilyplay.algebra.progress.text.ProgressPhrases;

final class IntermediateAward extends BaseAward {

	private static final String SMALL_TEMPLATE_FILE_NAME =
		"/seeemilyplay/algebra/progress/silvermedal_small.xml";
	private static final String LARGE_TEMPLATE_FILE_NAME =
		"/seeemilyplay/algebra/progress/silvermedal_large.xml";

	public IntermediateAward() {
		super();
	}

	protected String getNameFromPhrases(ProgressPhrases phrases) {
		return phrases.getIntermediateAwardName();
	}

	protected String getSmallTemplateFileName() {
		return SMALL_TEMPLATE_FILE_NAME;
	}

	protected String getLargeTemplateFileName() {
		return LARGE_TEMPLATE_FILE_NAME;
	}
}
