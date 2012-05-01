package seeemilyplay.algebra.sequences.jigsaw;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;


final class PieceIconContext {

	private static final String FOLDER = "/seeemilyplay/algebra/sequences/jigsaw/";
	private static final String FLAT_INNEY = FOLDER + "piece_flat_inney.xml";
	private static final String FLAT_OUTTEY = FOLDER + "piece_flat_outtey.xml";
	private static final String INNEY_FLAT = FOLDER + "piece_inney_flat.xml";
	private static final String INNEY_INNEY = FOLDER + "piece_inney_inney.xml";
	private static final String INNEY_OUTTEY = FOLDER + "piece_inney_outtey.xml";
	private static final String OUTTEY_FLAT = FOLDER + "piece_outtey_flat.xml";
	private static final String OUTTEY_INNEY = FOLDER + "piece_outtey_inney.xml";
	private static final String OUTTEY_OUTTEY = FOLDER + "piece_outtey_outtey.xml";

	private static final PieceIconContext instance = new PieceIconContext();
	
	private final Map<Edges, Icon> icons = 
		new HashMap<Edges, Icon>();
	private final Map<Edges, IconEdgeModel> iconEdgeModels = 
		new HashMap<Edges, IconEdgeModel>();

	
	public static PieceIconContext getInstance() {
		return instance;
	}
	
	private PieceIconContext() {
		super();
		initIcons();
		initIconEdgeModels();
	}
	
	private void initIcons() {
		
		init(PieceEdge.FLAT, PieceEdge.INNEY, FLAT_INNEY);
		init(PieceEdge.FLAT, PieceEdge.OUTTEY, FLAT_OUTTEY);
		init(PieceEdge.INNEY, PieceEdge.FLAT, INNEY_FLAT);
		init(PieceEdge.INNEY, PieceEdge.INNEY, INNEY_INNEY);
		init(PieceEdge.INNEY, PieceEdge.OUTTEY, INNEY_OUTTEY);
		init(PieceEdge.OUTTEY, PieceEdge.FLAT, OUTTEY_FLAT);
		init(PieceEdge.OUTTEY, PieceEdge.INNEY, OUTTEY_INNEY);
		init(PieceEdge.OUTTEY, PieceEdge.OUTTEY, OUTTEY_OUTTEY);
	}
	
	private void init(
			PieceEdge leftEdge, 
			PieceEdge rightEdge, 
			String iconPath) {
		Edges key = createKey(leftEdge, rightEdge);
		Icon icon = createIcon(iconPath);
		icons.put(key, icon);
	}
	
	private Icon createIcon(String iconPath) {
		AlgebraTheme theme = getTheme();
		URL iconResource = PieceIconContext.class.getResource(iconPath);
		return theme.getTemplateIcon(iconResource);
	}
	
	private AlgebraTheme getTheme() {
		return AlgebraTheme.getInstance();
	}
	
	private void initIconEdgeModels() {
		
		for (Map.Entry<Edges, Icon> entry : icons.entrySet()) {
			Edges edges = entry.getKey();
			Icon icon = entry.getValue();
			init(edges, icon);
		}
	}
	
	private void init(Edges edges, Icon icon) {
		iconEdgeModels.put(edges, new IconEdgeModel(icon));
	}
	
	public Icon getIcon(PieceEdge leftEdge, PieceEdge rightEdge) {
		return icons.get(createKey(leftEdge, rightEdge));
	}
	
	public IconEdgeModel getIconEdgeModel(PieceEdge leftEdge, PieceEdge rightEdge) {
		return iconEdgeModels.get(createKey(leftEdge, rightEdge));
	}
	
	private Edges createKey(PieceEdge leftEdge, PieceEdge rightEdge) {
		return new Edges(leftEdge, rightEdge);
	}

	private final static class Edges {

		private final PieceEdge leftEdge;
		private final PieceEdge rightEdge;


		public Edges(PieceEdge leftEdge, PieceEdge rightEdge) {
			this.leftEdge = leftEdge;
			this.rightEdge = rightEdge;
		}

		public PieceEdge getLeftEdge() {
			return leftEdge;
		}

		public PieceEdge getRightEdge() {
			return rightEdge;
		}

		public int hashCode() {
			return leftEdge.hashCode() + rightEdge.hashCode();		}

		public boolean equals(Object obj) {
			if (obj==null) {
				return false;
			}
			if (getClass()!=obj.getClass()) {
				return false;
			}
			Edges other = (Edges)obj;
			return (leftEdge.equals(other.getLeftEdge())
					&& rightEdge.equals(other.getRightEdge()));
		}
	}
}
