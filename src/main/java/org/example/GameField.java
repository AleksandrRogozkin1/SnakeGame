package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE=320;
    private final int DOT_SIZE=16;
    private final int ALL_DOTS=16;
    private Image dot;
    private Image coin;
    private int coinX;
    private int coinY;
    private int[] x=new int[ALL_DOTS];
    private int[] y=new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
    private boolean inGame=true;
    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }
    public void initGame(){
        dots=3;
        for (int i=0;i<dots;i++){
           x[i]=48-i*DOT_SIZE;
           y[i]=48;
        }
        timer =new Timer(250,this);
        timer.start();
        creatCoin();

    }

    private void creatCoin() {
        coinX=new Random().nextInt(20)*DOT_SIZE;
        coinY=new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia=new ImageIcon("images/coin.png");
        coin=iia.getImage();
        ImageIcon iid=new ImageIcon("images/snake.png");
        dot=iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(coin,coinX,coinY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else {
            String str="Game over!";
            g.setColor(Color.white);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (left){
            x[0]-=DOT_SIZE;
        }
        if (right){
            x[0]+=DOT_SIZE;
        }
        if (up){
            y[0]-=DOT_SIZE;
        }
        if (down){
            y[0]+=DOT_SIZE;
        }
    }
    public void checkCoin(){
        if(x[0]==coinX&&y[0]==coinY){
            dots++;
            creatCoin();
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkCoin();
            checkCollisions();
            move();
        }
        repaint();
    }

    private void checkCollisions() {
        for (int i = dots; i <0 ; i--) {
            if(i>4&&x[0]==x[i]&&y[0]==y[i]){
                inGame=false;
            }
        }
        if(x[0]>SIZE){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(y[0]>SIZE){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
    }
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT&& !right){
                left=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_RIGHT&& !left){
                right=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_UP&& !down){
                right=false;
                up=true;
                left=false;
            }
            if(key==KeyEvent.VK_DOWN&& !up){
                right=false;
                left=false;
                down=true;
            }
        }
    }
}
