package seeemilyplay.algebra.bulletin;

public interface NoticeListener <N extends Notice> {

	public void onPost(N notice);
}
