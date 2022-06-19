package thedrake;

import java.io.PrintWriter;

public enum GameResult implements JSONSerializable {
	VICTORY, DRAW, IN_PLAY;

	@Override
	public void toJSON(PrintWriter writer) {
		writer.write("\"" + this.toString() + "\"");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
