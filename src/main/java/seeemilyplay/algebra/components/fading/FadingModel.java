package seeemilyplay.algebra.components.fading;

import seeemilyplay.core.listeners.Listener;

public interface FadingModel {

	public float getOpacity();
	
	public boolean isFaded();
	
	public void addListener(Listener listener);
}
