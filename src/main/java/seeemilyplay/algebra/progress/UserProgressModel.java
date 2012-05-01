package seeemilyplay.algebra.progress;

import seeemilyplay.core.listeners.Listener;

final class UserProgressModel implements ProgressModel {

	private final String user;
	private final ProgressModel delegate;

	public UserProgressModel(
			String user,
			ProgressModel delegate) {
		this.user = user;
		this.delegate = delegate;
	}

	public Level getLevel(String id) {
		return delegate.getLevel(processId(id));
	}

	public void setLevel(String id, Level level) {
		delegate.setLevel(processId(id), level);
	}

	public void addListener(Listener listener) {
		delegate.addListener(listener);
	}

	private String processId(String id) {
		StringBuilder sb = new StringBuilder(user);
		sb.append(id);
		return sb.toString();
	}
}
