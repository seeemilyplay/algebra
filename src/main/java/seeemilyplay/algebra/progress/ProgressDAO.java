package seeemilyplay.algebra.progress;

import hu.netmind.persistence.Store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seeemilyplay.core.db.Database;

final class ProgressDAO {

	public List<ProgressRecord> loadProgressRecords() {
		Store store = getStore();
		return cast(
				store.find("find progressRecord"),
				ProgressRecord.class);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> cast(List<?> uncastList, Class<T> type) {
		List<T> checkedList =
			Collections.checkedList(new ArrayList<T>(), type);
		checkedList.addAll((List<T>)uncastList);
		return Collections.unmodifiableList(checkedList);
	}

	public void saveProgress(ProgressRecord progress) {
		Store store = getStore();
		store.save(progress);
	}

	private Store getStore() {
		return Database.getStore();
	}
}
