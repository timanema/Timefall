package me.timefall.timefall.events;

public enum Keys {
    VK_W(87),
    VK_S(83),
    VK_A(65),
    VK_D(68),
    VK_UP(38),
    VK_DOWN(40),
    VK_LEFT(37),
    VK_RIGHT(39);

    private int keyID;
    private boolean isPressed;

    private Keys(int keyID) {
        this.keyID = keyID;
        this.isPressed = false;
    }

    public void togglePressed(boolean isPressed){
        this.isPressed = isPressed;
    }

    public int getKeyID() {
        return keyID;
    }

    public boolean isPressed() {
        return isPressed;
    }
}
