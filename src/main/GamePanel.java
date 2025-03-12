package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

import static main.TickUpdate.tickUpdate;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int maxScreenCol = 32;
    private final int maxScreenRow = 18;
    private static final int tickPerSecond = 20;
    private static final long tickTimePerTick = 1_000_000_000 / tickPerSecond;
    private static final long fps = 60;
    private static final long renderTimePerTick = 1_000_000_000 / fps;


    Thread gameThread;

    public GamePanel() {
        int tileSize = originalTileSize * scale;
        int screenWidth = tileSize * maxScreenCol;
        int screenHeight = tileSize * maxScreenRow;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        long tickLastUpdateTime = System.nanoTime();
        long renderLastUpdateTime = System.nanoTime();
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            if (currentTime - tickLastUpdateTime >= tickTimePerTick) {// update the game state
                tickUpdate();
                tickLastUpdateTime = currentTime;
            }
            if(currentTime - renderLastUpdateTime >= renderTimePerTick){// render the game
                //render
                renderLastUpdateTime = currentTime;
            }
            if (currentTime < tickLastUpdateTime) {
                tickLastUpdateTime = currentTime;
            }
            if(currentTime < renderLastUpdateTime){
                renderLastUpdateTime = currentTime;
            }
            long tickInterval = currentTime - tickLastUpdateTime;
            long renderInterval = currentTime - renderLastUpdateTime;
            long minSleepTime = Math.min(tickTimePerTick - tickInterval, renderTimePerTick - renderInterval)/1000000;
            try {
                Thread.sleep(minSleepTime);
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}