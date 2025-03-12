package main;

import javax.swing.JFrame;

public class Main {
    public static final String GAME_ID = "game";

    public static String of (String id){
        return GAME_ID + ":" + id;
    }

    public static void main(String[] args) {
        register();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Test2DGame");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }

    public static void register(){
    }
}