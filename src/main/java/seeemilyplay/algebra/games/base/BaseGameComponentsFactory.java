package seeemilyplay.algebra.games.base;

import javax.swing.JComponent;

public interface BaseGameComponentsFactory {

	public JComponent createInstructionsComponent(BaseGameModel baseGameModel);

	public JComponent createTutorialComponent(BaseGameModel baseGameModel);

	public JComponent createPlayComponent(BaseGameModel baseGameModel);
}
