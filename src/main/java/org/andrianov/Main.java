package org.andrianov;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {

        GameWindow gameWindow = new GameWindow();
        Snake snake = gameWindow.getSnake();
        JPanel panel = gameWindow.getGamePanel();


        while (true){
            try {
                Thread.sleep(100);
                snake.step();
            } catch (InterruptedException e) {
                System.err.println("Ошибка приостановления потока");
            }
        }
    }
}
