package seeemilyplay.algebra.progress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seeemilyplay.core.swing.SwingModel;

final class ProgressModelImpl extends SwingModel implements ProgressModel {

	private final ProgressDAO progressDao;

	private final Map<String,ProgressRecord> progressRecords =
		new HashMap<String,ProgressRecord>();

	public ProgressModelImpl(ProgressDAO progressDao) {
		this.progressDao = progressDao;
		loadProgress();
	}

	private void loadProgress() {
		for (ProgressRecord progress : loadProgressRecords()) {
			saveLocally(progress);
		}
	}

	private List<ProgressRecord> loadProgressRecords() {
		return progressDao.loadProgressRecords();
	}

	private void saveLocally(ProgressRecord progress) {
		progressRecords.put(
				progress.getId(),
				progress);
	}

	public Level getLevel(String id) {
		if (!isRecordFor(id)) {
			createAndSaveRecordFor(id);
		}
		return getRecordedLevel(id);
	}

	private boolean isRecordFor(String id) {
		return progressRecords.containsKey(id);
	}

	private void createAndSaveRecordFor(String id) {
		ProgressRecord progress = createRecordFor(id);
		saveRecord(progress);
	}

	private ProgressRecord createRecordFor(String id) {
		return new ProgressRecord(id);
	}

	private void saveRecord(ProgressRecord progress) {
		saveLocally(progress);
		saveToDao(progress);
	}

	private void saveToDao(ProgressRecord progress) {
		progressDao.saveProgress(progress);
	}

	private Level getRecordedLevel(String id) {
		ProgressRecord progress = getProgress(id);
		return asEnum(progress.getLevel());
	}

	private ProgressRecord getProgress(String id) {
		return progressRecords.get(id);
	}

	public void setLevel(String id, Level level) {
		if (!isRecordFor(id)) {
			createAndSaveRecordFor(id);
		}
		ProgressRecord progress = getProgress(id);
		progress.setLevel(asOrdinal(level));
		saveToDao(progress);
		fireChange();
	}

	private int asOrdinal(Level level) {
		return level.ordinal();
	}

	private Level asEnum(int ordinal) {
		return Level.values()[ordinal];
	}
}
