package hu.akosbalogh.map.model;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Exact copy of GameState.
 */
@XmlRootElement(name = "game_state")
public class PersistableGameState {
    private Map map;
    private int[][] houndPositions;
    private int[] foxPosition;

    public PersistableGameState() {
    }

    public PersistableGameState(Map map, int[][] houndPositions, int[] foxPosition) {
        this.map = map;
        this.houndPositions = houndPositions;
        this.foxPosition = foxPosition;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int[][] getHoundPositions() {
        return houndPositions;
    }

    public void setHoundPositions(int[][] houndPositions) {
        this.houndPositions = houndPositions;
    }

    public int[] getFoxPosition() {
        return foxPosition;
    }

    public void setFoxPosition(int[] foxPosition) {
        this.foxPosition = foxPosition;
    }
}
