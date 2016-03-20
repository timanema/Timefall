package me.betasterren.bsgame.game;

import me.betasterren.bsgame.BSGame;

public class GameThread implements Runnable {
    private boolean isRunning;

    public GameThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
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
            boolean render = false;

            while (deltaTime >= 1) {
                ticksPassed++;
                deltaTime -= 1;

                tick();
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
                System.out.println("Ticks: " + ticksPassed + ", Frames: " + framesDrawn);

                framesDrawn = 0;
                ticksPassed = 0;
            }
        }
    }

    public synchronized void start() {
        isRunning = true;
        run();
    }

    public synchronized void stop() {
        isRunning = false;
    }

    /**
     * Method for logic calculations of the game
     */
    private void tick() {
    }

    /**
     * Method to render all the logic calculations and show them to the user
     */
    private void render() {

    }
}
