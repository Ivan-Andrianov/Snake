package org.andrianov;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

public class GameWindow extends JFrame {

    private Snake snake;
    private JPanel gamePanel;
    private static Color textColor = new Color(153, 184, 68);


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



        /*Создаем игровую панель и помещаем ее в контейнер игры*/
        this.gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setPreferredSize(new Dimension(641,431));
        gamePanel.setMaximumSize(new Dimension(641,431));
        gamePanel.setMinimumSize(new Dimension(641,431));
        gamePanel.setBorder(new LineBorder(Color.BLACK,5));
        gamePanel.setBackground(Color.GRAY);
        rootPane.setBackground(new Color(62,60,92));
        gamePanel.setLayout(null);
        this.snake = new Snake();
        gamePanel.add(snake);
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

                switch (e.getActionCommand()){
                    case ("w"):
                        Snake.direction = Snake.North;
                        break;
                    case ("d"):
                        Snake.direction = Snake.East;
                        break;
                    case ("s"):
                        Snake.direction = Snake.South;
                        break;
                    case ("a"):
                        Snake.direction = Snake.West;
                        break;
                }
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
}
