package seeemilyplay.algebra.progress;

import javax.swing.Icon;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.algebra.progress.text.ProgressPhrases;
import seeemilyplay.core.international.text.PhraseBook;

abstract class BaseAward implements Award {


	public BaseAward() {
		super();
	}

	public final String getName() {
		ProgressPhrases phrases = getPhrases();
		return getNameFromPhrases(phrases);
	}

	private ProgressPhrases getPhrases() {
		return PhraseBook.getPhrases(ProgressPhrases.class);
	}

	protected abstract String getNameFromPhrases(ProgressPhrases phrases);

	public final Icon getSmallIcon() {
		return getIcon(getSmallTemplateFileName());
	}

	protected abstract String getSmallTemplateFileName();

	public final Icon getLargeIcon() {
		return getIcon(getLargeTemplateFileName());
	}

	protected abstract String getLargeTemplateFileName();

	private Icon getIcon(String fileName) {
		return AlgebraTheme.getInstance().getTemplateIcon(
				BaseAward.class.getResource(fileName));
	}
}
