package seeemilyplay.algebra.sequences.jigsaw;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.base.BaseGameComponent;
import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.core.GamePlay;
import seeemilyplay.algebra.games.core.PlayResources;

public final class JigsawGamePlay implements GamePlay {

	public JigsawGamePlay() {
		super();
	}

	public JComponent play(PlayResources playResources) {
		BaseGameModel baseGameModel = new BaseGameModel(
				playResources,
				new JigsawGameComponentsFactory());
		return new BaseGameComponent(baseGameModel);
	}
}
