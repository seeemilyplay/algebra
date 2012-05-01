package seeemilyplay.algebra.users;

/**
 * The user identifier.
 */
public final class UserId {

	private long id;
	private String name;

	public UserId() {
		super();
	}

	public UserId(long id, String name) {
		this();
		setId(id);
		setName(name);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int hashCode() {
		return (int) (id ^ (id >>> 32)) + name.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		UserId other = (UserId)obj;
		return (id==other.getId()
				&& (name.equals(other.getName())));
	}
}
