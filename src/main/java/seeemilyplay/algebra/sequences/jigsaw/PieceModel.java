package seeemilyplay.algebra.sequences.jigsaw;

import javax.swing.Icon;


final class PieceModel {

	private final PieceEdge leftEdge;
	private final PieceEdge rightEdge;
	private final int value;
	
	private Icon icon;
	private IconEdgeModel iconEdgeModel;
	

	public PieceModel(
			PieceEdge leftEdge, 
			PieceEdge rightEdge,
			int value) {
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
		this.value = value;
		
		initIcon();
		initIconEdgeModel();
	}
	
	private void initIcon() {
		PieceIconContext context = getPieceIconContext();
		icon = context.getIcon(leftEdge, rightEdge);
	}
	
	private void initIconEdgeModel() {
		PieceIconContext context = getPieceIconContext();
		iconEdgeModel = context.getIconEdgeModel(leftEdge, rightEdge);
	}
	
	private PieceIconContext getPieceIconContext() {
		return PieceIconContext.getInstance();
	}
	
	public PieceEdge getLeftEdge() {
		return leftEdge;
	}
	
	public PieceEdge getRightEdge() {
		return rightEdge;
	}
	
	public int getValue() {
		return value;
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public boolean isWithinIconEdge(int x, int y) {
		return iconEdgeModel.isWithinEdge(x, y);
	}
	
	public boolean fitsOnRightSide(PieceModel other) {
		return isKey(rightEdge, other.getLeftEdge());
	}
	
	public boolean fitsOnLeftSide(PieceModel other) {
		return isKey(leftEdge, other.getRightEdge());
	}
	
	private boolean isKey(PieceEdge a, PieceEdge b) {
		switch (a) {
			case FLAT: {
				return false;
			} case OUTTEY: {
				return (PieceEdge.INNEY.equals(b));
			} case INNEY: {
				return (PieceEdge.OUTTEY.equals(b));
			} default: {
				throw new Error();
			}
		}
	}
	
	public boolean isOutteyOnLeft() {
		return PieceEdge.OUTTEY.equals(leftEdge);
	}

	public boolean isOutteyOnRight() {
		return PieceEdge.OUTTEY.equals(rightEdge);
	}
}
