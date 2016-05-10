package me.betasterren.bsgame.threads;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.display.Display;

import javax.swing.*;
import java.awt.*;

public class GameThread implements Runnable {
    private boolean started = false;
    private static boolean isRunning;

    @Override
    public void run() {
        if (!started) {
            isRunning = true;
            started = true;
        }

        long beginTime = System.nanoTime();
        double tickTime = 1000000000D / 30D;

        int ticksPassed = 0;
        int framesDrawn = 0;

        long currentFrameTimer = System.currentTimeMillis();
        double deltaTime = 0;

        while (isRunning) {
            long currentTime = System.nanoTime();

            deltaTime += (currentTime - beginTime) / tickTime;

            beginTime = currentTime;

            while (deltaTime >= 1) {
                ticksPassed++;

                tick(deltaTime);
                deltaTime -= 1;
            }

            try {
                Thread.sleep(1000 / BSGame.getSettings().getMaxFPS());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            framesDrawn++;
            render();

            if (System.currentTimeMillis() - currentFrameTimer >= 1000) {
                currentFrameTimer += 1000;
                System.out.println("Ticks: " + ticksPassed + ", Frames: " + framesDrawn + " (" + Thread.currentThread().getName() + ")");

                framesDrawn = 0;
                ticksPassed = 0;
            }
        }
    }

    public static synchronized void stop() {
        isRunning = false;
    }

    /**
     * Method for logic calculations of the game
     */
    private void tick(double deltaTime) {
        BSGame.getSettings().getCurrentState().tick(deltaTime);

        //TODO: Remove debug code
        Display display = BSGame.getMainDisplay();

        if (display != null) {
            JFrame jFrame = display.jFrame;

            if (jFrame != null) {
                jFrame.setTitle("xOff: " + BSGame.getTileManager().getCurrentWorld().getX() + " yOff: " + BSGame.getTileManager().getCurrentWorld().getY()
                + " pX: " + BSGame.getTileManager().getEntityManager().getPlayer().xOff
                        + " pY: " + BSGame.getTileManager().getEntityManager().getPlayer().yOff
                + " rPX: " + BSGame.getTileManager().getEntityManager().getPlayer().getxOff() +
                " rPY: " + BSGame.getTileManager().getEntityManager().getPlayer().getyOff()
                + " cW: " + BSGame.getTileManager().getEntityManager().getPlayer().getLocation().getWorldName()
                + " gender: " + BSGame.getTileManager().getEntityManager().getPlayer().getGender());
            }
        }
    }

    /**
     * Method to render all the logic calculations and show them to the user
     */
    private void render() {
        EventQueue.invokeLater(() -> BSGame.getMainDisplay().getGameCanvas().render());
    }
}
