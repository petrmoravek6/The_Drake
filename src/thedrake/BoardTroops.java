package thedrake;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable {
	private final PlayingSide playingSide;
	private final Map<BoardPos, TroopTile> troopMap;
	private final TilePos leaderPosition;
	private final int guards;

	public BoardTroops(PlayingSide playingSide, Map<BoardPos, TroopTile> troopMap, TilePos leaderPosition, int guards) {
		this.playingSide = playingSide;
		this.troopMap = troopMap;
		this.leaderPosition = leaderPosition;
		this.guards = guards;
	}

	public BoardTroops(PlayingSide playingSide) {
		this.playingSide = playingSide;
		troopMap = Collections.emptyMap();
		leaderPosition = TilePos.OFF_BOARD;
		guards = 0;
	}

	public Optional<TroopTile> at(TilePos pos) {
		if (troopMap.containsKey(pos)) {
			return Optional.of(troopMap.get(pos));
		}
		return Optional.empty();
	}

	public boolean hasNeighbours(BoardPos pos) {
		for (BoardPos neighbour:pos.neighbours()) {
			if (troopMap.containsKey(neighbour)) {
				return true;
			}
		}
		return false;
	}
	
	public PlayingSide playingSide() {
		return playingSide;
	}
	
	public TilePos leaderPosition() {
		return leaderPosition;
	}

	public int guards() {
		return guards;
	}
	
	public boolean isLeaderPlaced() {
		return leaderPosition == TilePos.OFF_BOARD ? false : true;
	}
	
	public boolean isPlacingGuards() {
		return this.isLeaderPlaced() && guards < 2;
	}	
	
	public Set<BoardPos> troopPositions() {
		return new HashSet<>(troopMap.keySet());
	}

	public BoardTroops placeTroop(Troop troop, BoardPos target) {
		if (troopMap.containsKey(target)) {
			throw new IllegalArgumentException();
		}
		Map<BoardPos, TroopTile> newTroopMap = new HashMap<>(troopMap);
		TroopTile newTroopTile = new TroopTile(troop, playingSide, TroopFace.AVERS);
		newTroopMap.put(target, newTroopTile);
		if (troopMap.isEmpty()) {
			return new BoardTroops(playingSide, newTroopMap, target, guards);
		}
		else if (!troopMap.isEmpty() && guards < 2) {
			return new BoardTroops(playingSide, newTroopMap, this.leaderPosition, guards + 1);
		}
		else {
			return new BoardTroops(playingSide, newTroopMap, this.leaderPosition, guards);
		}

	}
	
	public BoardTroops troopStep(BoardPos origin, BoardPos target) {
		if (!isLeaderPlaced() || isPlacingGuards()) {
			throw new IllegalStateException();
		}
		if (at(origin).isEmpty() || at(target).isPresent()) {
			throw new IllegalArgumentException();
		}
		Map<BoardPos, TroopTile> newTroopMap = new HashMap<>(troopMap);
		TroopTile tile = newTroopMap.remove(origin);
		newTroopMap.put(target, tile.flipped());
		if (origin.equals(leaderPosition)) {
			return new BoardTroops(playingSide, newTroopMap, target, guards);
		}
		return new BoardTroops(playingSide, newTroopMap, leaderPosition, guards);
	}
	
	public BoardTroops troopFlip(BoardPos origin) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed.");			
		}

		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot move troops before guards are placed.");			
		}

		if(!at(origin).isPresent())
			throw new IllegalArgumentException();
		
		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(origin);
		newTroops.put(origin, tile.flipped());

		return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
	}
	
	public BoardTroops removeTroop(BoardPos target) {
		if (!isLeaderPlaced() || isPlacingGuards()) {
			throw new IllegalStateException();
		}
		if (at(target).isEmpty()) {
			throw new IllegalArgumentException();
		}
		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		newTroops.remove(target);
		if (target.equals(leaderPosition)) {
			return new BoardTroops(playingSide, newTroops, TilePos.OFF_BOARD, guards);
		}
		return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
	}

	@Override
	public void toJSON(PrintWriter writer) {
		writer.write("{\"side\":");
		playingSide.toJSON(writer);
		writer.write(",\"leaderPosition\":");
		leaderPosition.toJSON(writer);
		writer.write(",\"guards\":" + guards + ",\"troopMap\":{");
		List<BoardPos> keys = new ArrayList<>(troopMap.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size() - 1; i++) {
			BoardPos bp = keys.get(i);
			bp.toJSON(writer);
			writer.write(":");
			TroopTile tt = troopMap.get(bp);
			tt.toJSON(writer);
			writer.write(",");
		}
		if (! keys.isEmpty()) {
			BoardPos bp = keys.get(keys.size() - 1);
			bp.toJSON(writer);
			writer.write(":");
			TroopTile tt = troopMap.get(bp);
			tt.toJSON(writer);
		}
		writer.write("}}");
	}
}
