import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, keyListener{

    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;


    //snake
    Tile snakeHead;

    //food
    Tile food;
    Random random;

    //game logic

    Timer gameLoop;
    int velocityX;
    int velocityY;

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);


        snakeHead = new Tile(5,5);

        food = new Tile(20,20);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Grid
   
        for(int i = 0; i < boardWidth/tileSize; i++){
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        //food
        g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y* tileSize, tileSize, tileSize);

        //Snake
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y* tileSize, tileSize, tileSize);
    }
    

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public void move(){
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
       repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP){
            velocityX = 0;
            velocityY = -1;
        }

        if(key == KeyEvent.VK_DOWN){
            velocityX = 0;
            velocityY = 1;
        }

        if(key == KeyEvent.VK_LEFT){
            velocityX = -1;
            velocityY = 0;
        }

        if(key == KeyEvent.VK_RIGHT){
            velocityX = 1;
            velocityY = 0;
        }
       
    }


    // we don't need to implement the other methods of the interface
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
