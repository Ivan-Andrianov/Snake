package org.andrianov;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake extends JComponent {
    /* Константы направления движения головы змейки*/

    public static int North = 0;
    public static int South = 1;
    public static int West = 2;
    public static int Earn = 3;

    /*Список всех частей тела змейки (Включая голову)*/
    LinkedList<Body> snake;


    /*Текущее направление головы змейки*/
    public static int direction;

    public Snake(){
        direction = Snake.Earn;
        snake = new LinkedList<>(List.of(new Head()));
        for (int i=1;i<=3;i++){
            int[] location = snake.getLast().locate;
            snake.add(new Body(new int[]{location[0],location[1]-21}));
        }
    }

    /*Класс головы змейки*/

    class Head extends Body{
        public Head(){
            super(new int[]{64,64});
        }
    }

    class Body{

        private int size;
        private int[] locate;
        public Body(int[] locate){
            size = 20;
            this.locate = locate;
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        for (Body body:snake){
            g.fillRect(body.locate[0],body.locate[1],20, 20);
        }
    }

    public void step(){

        int[] previousCoordinate = Arrays.copyOf(snake.getFirst().locate,2);

        for (Body body:snake){
            if (body instanceof Head){
                body.locate[0]+=21;
            }else{
                int[] helper;
                helper = body.locate;
                body.locate = previousCoordinate;
                previousCoordinate = helper;
            }
        }
        this.repaint();
    }
}
