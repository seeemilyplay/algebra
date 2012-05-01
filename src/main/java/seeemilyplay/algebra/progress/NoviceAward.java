package seeemilyplay.algebra.progress;

import seeemilyplay.algebra.progress.text.ProgressPhrases;

final class NoviceAward extends BaseAward {

	private static final String SMALL_TEMPLATE_FILE_NAME =
		"/seeemilyplay/algebra/progress/bronzemedal_small.xml";
	private static final String LARGE_TEMPLATE_FILE_NAME =
		"/seeemilyplay/algebra/progress/bronzemedal_large.xml";

	public NoviceAward() {
		super();
	}

	protected String getNameFromPhrases(ProgressPhrases phrases) {
		return phrases.getNoviceAwardName();
	}

	protected String getSmallTemplateFileName() {
		return SMALL_TEMPLATE_FILE_NAME;
	}

	protected String getLargeTemplateFileName() {
		return LARGE_TEMPLATE_FILE_NAME;
	}
}
