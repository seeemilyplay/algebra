package seeemilyplay.algebra.progress;


public final class ProgressRecord {

	private String id;
	private int level;

	public ProgressRecord() {
		super();
	}

	public ProgressRecord(String id) {
		this();
		setId(id);
		setLevel(Level.BEGINNER.ordinal());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int hashCode() {
		return id==null ? 0 : id.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		ProgressRecord other = (ProgressRecord)obj;
		return id!=null ? id.equals(other.id) : other.id==null;
	}
}
