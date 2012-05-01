package seeemilyplay.algebra.bulletin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class BulletinBoard {
	
	private static final BulletinBoard INSTANCE = new BulletinBoard();
	
	private final Map<Class<?>,Listeners<?>> listenersByType =
		new HashMap<Class<?>,Listeners<?>>();
	
	private final List<Notice> notices = new ArrayList<Notice>();
	
	public static BulletinBoard getInstance() {
		return INSTANCE;
	}
	
	private BulletinBoard() {
		super();
	}
	
	public void post(Notice notice) {
		notices.add(notice);
		firePost(notice);
	}
	
	public void remove(Notice notice) {
		notices.remove(notice);
	}
	
	public Iterable<Notice> getNotices() {
		return Collections.unmodifiableList(
				new ArrayList<Notice>(notices));
	}

	public <N extends Notice> void addListener(
			Class<N> type,
			NoticeListener<N> listener) {
		Listeners<N> listeners = getOrCreateListenersFor(type);
		listeners.add(listener);
	}
	
	private <N extends Notice> void firePost(N notice) {
		Class<N> type = getType(notice);
		Listeners<N> listeners = getOrCreateListenersFor(type);
		listeners.firePost(notice);
	}
	
	@SuppressWarnings("unchecked")
	private <N extends Notice> Class<N> getType(N notice) {
		return (Class<N>)notice.getClass();
	}
	
	private <N extends Notice> Listeners<N> getOrCreateListenersFor(Class<N> type) {
		createListenersIfRequiredFor(type);
		return getListeners(type);
	}
	
	private <N extends Notice> void createListenersIfRequiredFor(Class<N> type) {
		if (!isListenersFor(type)) {
			createListenersFor(type);
		}
	}
	
	private <N extends Notice> boolean isListenersFor(Class<N> type) {
		return listenersByType.containsKey(type);
	}
	
	private <N extends Notice> void createListenersFor(Class<N> type) {
		Listeners<N> listeners = new Listeners<N>();
		listenersByType.put(type, listeners);
	}
	
	@SuppressWarnings("unchecked")
	private <N extends Notice> Listeners<N> getListeners(Class<N> type) {
		return (Listeners<N>)listenersByType.get(type);
	}
	
	private class Listeners <N extends Notice> {
		
		private final Map<NoticeListener<N>,NoticeListener<N>> listeners =
			new WeakHashMap<NoticeListener<N>,NoticeListener<N>>();
		
		public Listeners() {
			super();
		}
		
		public void add(NoticeListener<N> listener) {
			listeners.put(listener, null);
		}
		
		public void firePost(N notice) {
			for (NoticeListener<N> listener : getCurrentListeners()) {
				listener.onPost(notice);
			}
		}

		private Set<NoticeListener<N>> getCurrentListeners() {
			Set<NoticeListener<N>> currentListeners =
				new HashSet<NoticeListener<N>>(listeners.keySet());
			return Collections.unmodifiableSet(currentListeners);
		}
	}
}
