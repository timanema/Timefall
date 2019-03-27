package me.timefall.timefall.events.keys;

import me.timefall.timefall.threads.GameThread;

public enum Keys
{
    VK_W(87, 0),
    VK_S(83, 0),
    VK_A(65, 0),
    VK_D(68, 0),
    VK_UP(38, 0),
    VK_DOWN(40, 0),
    VK_LEFT(37, 0),
    VK_RIGHT(39, 0),
    VK_I(73, 0),
    VK_F3(114, 0.2 * GameThread.TICKS),
    VK_ESC(27, 0),
    VK_P(80, 0),
    VK_O(79, 0),
    VK_R(82,0),
    VK_T(84, 0),
    VK_Y(89, 0),
    VK_K(75, 0),
    VK_L(76, 0),
    VK_J(74, 0),
    VK_Q(81, 0);

    private int keyID;
    private boolean isPressed;
    private boolean clicked;
    private int timeout;
    private int cooldown;

    private Keys(int keyID, double timeout)
    {
        this.keyID = keyID;
        this.isPressed = false;
        this.clicked = false;
        this.timeout = (int) timeout;
    }

    public void togglePressed(boolean isPressed)
    {
        this.isPressed = isPressed;

        if (this.cooldown <= 0 && isPressed)
        {
            this.clicked = true;
        }
    }

    public int getKeyID()
    {
        return this.keyID;
    }

    public boolean isPressed()
    {
        return this.isPressed;
    }

    public boolean isClicked()
    {
        if (this.clicked)
        {
            this.clicked = false;
            this.cooldown = this.timeout;

            return true;
        }

        return false;
    }

    public void tickCooldown()
    {
        if (this.cooldown > 0)
        {
            this.cooldown -= 1;
        }
    }
}
