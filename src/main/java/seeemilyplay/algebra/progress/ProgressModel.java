package seeemilyplay.algebra.progress;

import seeemilyplay.core.listeners.Listener;

public interface ProgressModel {

	public Level getLevel(String id);

	public void setLevel(String id, Level level);

	public void addListener(Listener listener);
}
