package org.andrianov;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake extends JComponent {
    /* Константы направления движения головы змейки*/

    private GameWindow gameWindow;

    private int amountBody = 1;

    private Map<Integer,Integer[]> freeFields;
    public final static int North = 0;
    public final static int South = 1;
    public final static int West = 2;
    public final static int East = 3;

    /*Список всех частей тела змейки (Включая голову)*/
    LinkedList<Body> snake;

    private int speed;


    /*Текущее направление головы змейки*/
    public static int direction;

    private boolean canTurn;

    public Snake(GameWindow gameWindow){
        this.gameWindow = gameWindow;
        this.freeFields = gameWindow.getFreeFields();
        direction = Snake.South;
        this.snake = new LinkedList<>(List.of(new Head()));
        this.speed = 80;
        this.canTurn = true;
        for (int i=1;i<=3;i++){
            int[] location = snake.getLast().locate;
            snake.add(new Body(new int[]{location[0],location[1]-21}));
            amountBody+=1;
        }
    }

    public boolean isCanTurn() {
        return canTurn;
    }

    public void setCanTurn(boolean canTurn) {
        this.canTurn = canTurn;
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

        private Color color;
        public Body(int[] locate){
            this.color = Color.GREEN;
            this.size = 20;
            this.locate = locate;
            freeFields.remove(((locate[0]-1)/21)+(locate[1]-1)/21*100);
            System.out.println(freeFields.size());
        }

        public Color getColor() {
            return this.color;
        }

        public void setColor(Color color){
            this.color = color;
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        for (Body body:snake){
            if (body instanceof Head){
                g.setColor(body.getColor());
                switch (Snake.direction){
                    case Snake.South -> {
                        g.fillRect(body.locate[0],body.locate[1],20,15);
                        g.fillOval(body.locate[0],body.locate[1],20,20);
                        break;
                    }
                    case Snake.North -> {
                        g.fillRect(body.locate[0],body.locate[1]+5,20,15);
                        g.fillOval(body.locate[0],body.locate[1],20,20);
                    }
                    case Snake.West -> {
                        g.fillRect(body.locate[0]+5,body.locate[1],15,20);
                        g.fillOval(body.locate[0],body.locate[1],20,20);
                    }
                    case Snake.East -> {
                        g.fillRect(body.locate[0],body.locate[1],15,20);
                        g.fillOval(body.locate[0],body.locate[1],20,20);
                    }
                }
            }else{
                g.setColor(body.getColor());
                g.fillRect(body.locate[0],body.locate[1],body.size, body.size);
            }
        }
    }

    public boolean step(){


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
                if (!freeFields.containsKey((body.locate[0]-1)/21+(body.locate[1]-1)/21*100)){
                    body.locate = previousCoordinate;
                    snake.getFirst().setColor(Color.RED);
                    snake.getFirst().locate = previousCoordinate;
                    gameWindow.repaint();
                    return false;
                }
                freeFields.remove(((body.locate[0]-1)/21)+(body.locate[1]-1)/21*100);

            }else{
                int[] helper;
                helper = body.locate;
                body.locate = previousCoordinate;
                previousCoordinate = helper;
            }
        }
        if (snake.getFirst().locate[0]==gameWindow.getCandy().giveMeLocation()[0] && snake.getFirst().locate[1]==gameWindow.getCandy().giveMeLocation()[1]){
            this.snake.add(new Body(previousCoordinate));
            this.amountBody+=1;
            gameWindow.getScore().setText("Score: "+(amountBody-4));
            if (amountBody==10 || amountBody==20) setSpeed(speed-15);
            this.gameWindow.getCandy().changeLocation();
        }else freeFields.put((previousCoordinate[0]-1)/21+(previousCoordinate[1]-1)/21*100,new Integer[]{previousCoordinate[0],previousCoordinate[1]});

        this.repaint();
        gameWindow.check = true;
        gameWindow.getSnake().setCanTurn(true);
        return true;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
