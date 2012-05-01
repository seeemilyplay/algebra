package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.core.listeners.Listener;

public interface ApplicationModel {

	public Level getLevel(GameId gameId);

	public Award getAward(Level level);

	public void setSelectedGame(GameId selectedGame);

	public void addListener(Listener listener);
}
