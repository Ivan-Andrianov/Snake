package org.andrianov;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake extends JComponent {
    /* Константы направления движения головы змейки*/

    public final static int North = 0;
    public final static int South = 1;
    public final static int West = 2;
    public final static int East = 3;

    /*Список всех частей тела змейки (Включая голову)*/
    LinkedList<Body> snake;


    /*Текущее направление головы змейки*/
    public static int direction;

    public Snake(){
        direction = Snake.East;
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
            g.fillRect(body.locate[0],body.locate[1],body.size, body.size);
        }
    }

    public void step(){

        int[] previousCoordinate = Arrays.copyOf(snake.getFirst().locate,2);

        for (Body body:snake){
            if (body instanceof Head){
                switch (Snake.direction) {
                    case (Snake.East):
                        body.locate[0]+=21;
                        break;
                    case (Snake.West):
                        body.locate[0]-=21;
                        break;
                    case (Snake.North):
                        body.locate[1]-=21;
                        break;
                    case (Snake.South):
                        body.locate[1]+=21;
                        break;
                }
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
