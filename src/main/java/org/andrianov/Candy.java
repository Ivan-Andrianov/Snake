package org.andrianov;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Candy extends JComponent {
    private Map<Integer, Integer[]> freeFields;
    private Integer[] location;

    public Candy(GameWindow gameWindow){
        Random random = new Random();
        this.freeFields = gameWindow.getFreeFields();
        this.location = (Integer[]) new ArrayList(freeFields.values()).get(random.nextInt(freeFields.size()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(location[0],location[1],20,20);
    }

    public Integer[] giveMeLocation(){return location;}

    /*Меняет локацию конфеты*/
    public void changeLocation(){
        Random random = new Random();
        this.location =  (Integer[]) new ArrayList(freeFields.values()).get(random.nextInt(freeFields.size()));
    }
}
