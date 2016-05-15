package me.timefall.timefall.level.conflict;

public class RenderConflictException extends Exception {
    public RenderConflictException(int x, int y, String layer) {
        super("Cannot solve render conflict at X: " + x + ", Y: " + y + " (" + layer + " layer)!\n" +
                "Map cannot be loaded, solve the conflict in '/" + layer + "Conflicts.txt' and restart the game!");
    }

    public RenderConflictException(String msg) {
        super(msg);
    }
}
