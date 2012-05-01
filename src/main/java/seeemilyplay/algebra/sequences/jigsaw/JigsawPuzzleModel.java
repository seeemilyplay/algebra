package seeemilyplay.algebra.sequences.jigsaw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class JigsawPuzzleModel extends SwingModel {
	
	private final List<PieceModel> pieceModels;
	private final List<PuzzleSquareModel> puzzleSquareModels;
	
	private Listener puzzleSquareModelListener;
	
	public JigsawPuzzleModel(
			List<PieceModel> pieceModels,
			List<PuzzleSquareModel> puzzleSquareModels) {
		
		List<PieceModel> shuffledPieceModels = new ArrayList<PieceModel>(pieceModels);
		Collections.shuffle(shuffledPieceModels);
		this.pieceModels = Collections.unmodifiableList(shuffledPieceModels);
		
		this.puzzleSquareModels = Collections.unmodifiableList(
				new ArrayList<PuzzleSquareModel>(puzzleSquareModels));
		
		initPuzzleSquareModelListener();
	}
	
	private void initPuzzleSquareModelListener() {
		puzzleSquareModelListener = new Listener() {
			public void onChange() {
				fireChange();
			}
		};
		installPuzzleSquareModelListener();
		fireChange();
	}
	
	private void installPuzzleSquareModelListener() {
		for (PuzzleSquareModel puzzleSquareModel : puzzleSquareModels) {
			puzzleSquareModel.addListener(puzzleSquareModelListener);
		}
	}
	
	public Iterable<PieceModel> getPieceModels() {
		return pieceModels;
	}
	
	public Iterable<PuzzleSquareModel> getPuzzleSquareModels() {
		return puzzleSquareModels;
	}
	
	public boolean isCorrectlyCompleted() {
		for (PuzzleSquareModel puzzleSquare : puzzleSquareModels) {
			if (!puzzleSquare.isAnsweredCorrectly()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isIncorrectlyCompleted() {
		for (PuzzleSquareModel puzzleSquare : puzzleSquareModels) {
			if (!puzzleSquare.isAnsweredIncorrectly()) {
				return false;
			}
		}
		return true;
	}
}
