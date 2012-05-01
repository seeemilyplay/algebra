package seeemilyplay.algebra.sequences.crossword;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.base.BaseGameComponent;
import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.core.GamePlay;
import seeemilyplay.algebra.games.core.PlayResources;

public final class CrosswordGamePlay implements GamePlay {

	public CrosswordGamePlay() {
		super();
	}

	public JComponent play(PlayResources playResources) {
		BaseGameModel baseGameModel = new BaseGameModel(
				playResources,
				new CrosswordGameComponentsFactory());
		return new BaseGameComponent(baseGameModel);
	}
}
