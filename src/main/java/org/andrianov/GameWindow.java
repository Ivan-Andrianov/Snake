package org.andrianov;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JFrame {

    private Snake snake;
    private Candy candy;
    private JPanel gamePanel;
    private static Color textColor = new Color(213, 211, 43);
    private Map<Integer,Integer[]> freeFields = new HashMap<>();


    public GameWindow(){
        super("Snake");

        Container rootPane = this.getRootPane();
        BoxLayout bl = new BoxLayout(rootPane,BoxLayout.X_AXIS);
        rootPane.setLayout(bl);

        /*Создаем контейнер игры*/
        JPanel  gameContainer = new JPanel();
        BoxLayout gameContainerLayout = new BoxLayout(gameContainer,BoxLayout.Y_AXIS);
        gameContainer.setLayout(gameContainerLayout);
        gameContainer.setPreferredSize(new Dimension(700,1080));
        gameContainer.setMaximumSize(new Dimension(700,1080));
        gameContainer.setMinimumSize(new Dimension(700,1080));


        /*Нумеруем свободные поля*/
        for (int i=0 ; i<=19 ; i++){
            for (int j=0;j<=29;j++){
                freeFields.put(i*100+j,new Integer[]{1+21*j,1+21*i});
            }
        }


        /*Создаем игровую панель и помещаем ее в контейнер игры*/
        this.gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setPreferredSize(new Dimension(641,431));
        gamePanel.setMaximumSize(new Dimension(641,431));
        gamePanel.setMinimumSize(new Dimension(641,431));
        gamePanel.setBorder(new LineBorder(Color.BLACK,5));
        gamePanel.setBackground(Color.DARK_GRAY);
        rootPane.setBackground(new Color(62,60,92));
        gamePanel.setLayout(null);
        this.snake = new Snake(this);
        gamePanel.add(snake);
        this.candy = new Candy(this);
        gamePanel.add(candy);
        candy.setBounds(5,5,635,425);
        snake.setBounds(5,5,635,425);




        /*Создаем панель, для отрисовки количества очков*/
        JPanel scorePanel = new JPanel();
        scorePanel.setMinimumSize(new Dimension(700,300));
        scorePanel.setPreferredSize(new Dimension(700,300));
        scorePanel.setMaximumSize(new Dimension(700,300));
        scorePanel.setOpaque(false);
        SpringLayout sl = new SpringLayout();
        scorePanel.setLayout(sl);
        JTextField gameName = new JTextField("Snake");
        scorePanel.add(gameName);
        gameName.setOpaque(false);
        gameName.setBorder(null);
        gameName.setEditable(false);
        gameName.setFont(new Font(Font.DIALOG,Font.PLAIN,70));
        gameName.setForeground(textColor);
        sl.putConstraint(SpringLayout.WEST,gameName,230,SpringLayout.WEST,scorePanel);





        rootPane.add(Box.createHorizontalStrut(50));
        gameContainer.add(scorePanel);
        gameContainer.add(gamePanel);
        gameContainer.add(Box.createVerticalGlue());
        rootPane.add(gameContainer);
        rootPane.add(Box.createHorizontalGlue());

        gameContainer.setOpaque(false);

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (!snake.isCanTurn()){
                    System.out.println(snake.isCanTurn());
                }
                switch (e.getActionCommand()){
                    case ("w"):
                        if (Snake.direction!=Snake.South) Snake.direction = Snake.North;
                        break;
                    case ("d"):
                        if (Snake.direction!=Snake.West) Snake.direction = Snake.East;
                        break;
                    case ("s"):
                        if (Snake.direction!=Snake.North) Snake.direction = Snake.South;
                        break;
                    case ("a"):
                        if (Snake.direction!=Snake.East) Snake.direction = Snake.West;
                        break;
                }
                snake.setCanTurn(false);
            }
        };

        getRootPane().getInputMap().put(KeyStroke.getKeyStroke('s'),"s");
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke('d'),"d");
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke('w'),"w");
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke('a'),"a");
        getRootPane().getActionMap().put("s",action);
        getRootPane().getActionMap().put("w",action);
        getRootPane().getActionMap().put("a",action);
        getRootPane().getActionMap().put("d",action);




        this.setSize(800,800);
        this.setLocation(300,20);
        this.setVisible(true);
    }


    public JPanel getGamePanel(){
        return gamePanel;
    }

    public Snake getSnake(){
        return snake;
    }

    public Map<Integer, Integer[]> getFreeFields() {
        return freeFields;
    }

    public Candy getCandy(){return this.candy;}
}
