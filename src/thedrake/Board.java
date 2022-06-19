package thedrake;

import java.io.PrintWriter;
import java.util.StringJoiner;

public class Board implements JSONSerializable {
	private final BoardTile[][] board;
	private final int dimension;

	// Konstruktor. Vytvoří čtvercovou hrací desku zadaného rozměru, kde všechny dlaždice jsou prázdné, tedy BoardTile.EMPTY
	public Board(int dimension) {
		this.dimension = dimension;
		this.board = new BoardTile[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				board[i][j] = BoardTile.EMPTY;
			}
		}
	}
	// Kopírující konstruktor
public Board(Board in) {
		this.dimension = in.dimension();
		this.board = new BoardTile[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				board[i][j] = in.board[i][j];
			}
		}
}
	// Rozměr hrací desky
	public int dimension() {
		return dimension;
	}

	@Override
	public void toJSON(PrintWriter writer) {
		writer.write("{\"dimension\":" + dimension + ",\"tiles\":");
		StringJoiner sj = new StringJoiner(",", "[", "]");
		for (int j = 0; j < dimension; j++) {
			for (int i = 0; i < dimension; i++) {
				sj.add("\"" + board[i][j].toString() + "\"");
			}
		}
		writer.write(sj + "}");
		

	}

	// Vrací dlaždici na zvolené pozici.
	public BoardTile at(TilePos pos) {
		return board[pos.i()][pos.j()];
	}

	// Vytváří novou hrací desku s novými dlaždicemi. Všechny ostatní dlaždice zůstávají stejné
	public Board withTiles(TileAt ...ats) {
		Board newBoard = new Board(this);
		for (int i = 0; i < ats.length; i++)
			newBoard.board[ats[i].pos.i()][ats[i].pos.j()] = ats[i].tile;
		return newBoard;
	}

	// Vytvoří instanci PositionFactory pro výrobu pozic na tomto hracím plánu
	public PositionFactory positionFactory() {
		return new PositionFactory(this.dimension);
	}
	
	public static class TileAt {
		public final BoardPos pos;
		public final BoardTile tile;
		
		public TileAt(BoardPos pos, BoardTile tile) {
			this.pos = pos;
			this.tile = tile;
		}
	}
}

