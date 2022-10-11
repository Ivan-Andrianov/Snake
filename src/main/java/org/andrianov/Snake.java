package org.andrianov;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake extends JComponent {
    /* Константы направления движения головы змейки*/

    private GameWindow gameWindow;

    private Map<Integer,Integer[]> freeFields;
    public final static int North = 0;
    public final static int South = 1;
    public final static int West = 2;
    public final static int East = 3;

    /*Список всех частей тела змейки (Включая голову)*/
    LinkedList<Body> snake;


    /*Текущее направление головы змейки*/
    public static int direction;

    public Snake(GameWindow gameWindow){
        this.gameWindow = gameWindow;
        this.freeFields = gameWindow.getFreeFields();
        direction = Snake.South;
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
            freeFields.remove((locate[0]/21-1)+(locate[1]/21-1)*100);
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
                        if (body.locate[0]==610) body.locate[0]=1;
                        else body.locate[0]+=21;
                        break;
                    case (Snake.West):
                        if (body.locate[0]==1) body.locate[0]=610;
                        else body.locate[0]-=21;
                        break;
                    case (Snake.North):
                        if (body.locate[1]==1) body.locate[1] = 400;
                        else body.locate[1]-=21;
                        break;
                    case (Snake.South):
                        if (body.locate[1]==400) body.locate[1] = 1;
                        else body.locate[1]+=21;
                        break;
                }
                freeFields.remove((body.locate[0]/21-1)+(body.locate[1]/21-1)*100);

            }else{
                int[] helper;
                helper = body.locate;
                body.locate = previousCoordinate;
                previousCoordinate = helper;
            }
        }
        if (snake.getFirst().locate[0]==gameWindow.getCandy().giveMeLocation()[0] && snake.getFirst().locate[1]==gameWindow.getCandy().giveMeLocation()[1]){
            snake.add(new Body(previousCoordinate));
            gameWindow.getCandy().changeLocation();
        }else freeFields.put((previousCoordinate[0]/21-1)+(previousCoordinate[1]/21-1)*100,new Integer[]{previousCoordinate[0],previousCoordinate[1]});
        this.repaint();
    }
}
