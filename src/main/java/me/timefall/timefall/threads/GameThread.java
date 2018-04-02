package me.timefall.timefall.threads;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.display.Display;
import me.timefall.timefall.events.keys.Keys;
import me.timefall.timefall.graphics.components.Screen;

import java.awt.image.BufferedImage;

public class GameThread implements Runnable
{
    public static final int TICKS = 30;

    private boolean capped = false;
    private boolean started = false;
    private static boolean isRunning;

    @Override
    public void run()
    {
        if (!started)
        {
            isRunning = true;
            started = true;
        }

        long beginTime = System.nanoTime();
        double tickTime = 1000000000D / 30D;

        int ticksPassed = 0;
        int framesDrawn = 0;

        long currentFrameTimer = System.currentTimeMillis();
        double deltaTime = 0;

        while (isRunning)
        {
            long currentTime = System.nanoTime();

            deltaTime += (currentTime - beginTime) / tickTime;

            beginTime = currentTime;

            while (deltaTime >= 1)
            {
                ticksPassed++;

                tick(deltaTime);
                deltaTime -= 1;
            }

            if (capped)
            {
                try
                {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            framesDrawn++;
            render();

            if (System.currentTimeMillis() - currentFrameTimer >= 1000)
            {
                currentFrameTimer += 1000;
                //System.out.println("Ticks: " + ticksPassed + ", Frames: " + framesDrawn + " (" + Thread.currentThread().getName() + " - " + EventQueue.isDispatchThread() + ")");

                framesDrawn = 0;
                ticksPassed = 0;
            }
        }
    }

    public static synchronized void stop()
    {
        isRunning = false;
    }

    /**
     * Method for logic calculations of the game
     */
    private void tick(double deltaTime)
    {
        Timefall.getSettings().getCurrentState().tick(deltaTime);
        Timefall.getTime().tickTime();

        // Tick button cooldown
        for (Keys key : Keys.values())
        {
            key.tickCooldown();
        }

        //TODO: Remove debug code
        Display display = Timefall.getMainDisplay();

        /*if (display != null)
        {
            JFrame jFrame = display.jFrame;

            if (jFrame != null)
            {
                jFrame.setTitle("xOff: " + Timefall.getTileManager().getCurrentWorld().getX() + " yOff: " + Timefall.getTileManager().getCurrentWorld().getY()
                        + " pX: " + Timefall.getTileManager().getEntityManager().getPlayer().xOff
                        + " pY: " + Timefall.getTileManager().getEntityManager().getPlayer().yOff
                        + " rPX: " + Timefall.getTileManager().getEntityManager().getPlayer().getxOff() +
                        " rPY: " + Timefall.getTileManager().getEntityManager().getPlayer().getyOff()
                        + " cW: " + Timefall.getTileManager().getEntityManager().getPlayer().getLocation().getWorldName()
                        + " gender: " + Timefall.getTileManager().getEntityManager().getPlayer().getGender());
            }
        }*/
    }

    /**
     * Method to render all the logic calculations and show them to the user
     */
    private void render()
    {
        Display display = Timefall.getMainDisplay();
        Screen screen;

        if (display == null)
            return;

        screen = display.getScreen();

        if (screen == null)
            return;

        // Render sprites
        Timefall.getMainDisplay().getMenuCanvas().repaint();
        Timefall.getMainDisplay().getGameCanvas().repaint();
    }
}
