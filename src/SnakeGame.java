import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

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
    ArrayList<Tile> snakeBody;
    //food
    Tile food;
    Random random;

    //game logic

    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

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
   
        // for(int i = 0; i < boardWidth/tileSize; i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        // }

        //food
        g.setColor(Color.RED);
        // g.fillRect(food.x * tileSize, food.y* tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y* tileSize, tileSize, tileSize, true);

        //Snake head
        g.setColor(Color.GREEN);
        // g.fillRect(snakeHead.x * tileSize, snakeHead.y* tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y* tileSize, tileSize, tileSize, true);
        //Snake body
        for(Tile snakePart : snakeBody){
            // g.fillRect(tile.x * tileSize, tile.y* tileSize, tileSize, tileSize);
        g.fill3DRect(snakePart.x * tileSize, snakePart.y* tileSize, tileSize, tileSize, true);

        }



  
if(gameOver) {

    g.setFont(new Font("Arial", Font.BOLD, 40));
    g.setColor(Color.RED);
    String gameOverText = "Game Over!";
    g.drawString(gameOverText, boardWidth/2 - 100, boardHeight/2 - 20);
    
 
    g.setFont(new Font("Arial", Font.PLAIN, 25));
    g.setColor(Color.WHITE);
    String scoreText = "Final Score: " + snakeBody.size();
    g.drawString(scoreText, boardWidth/2 - 70, boardHeight/2 + 30);
    
   
    
} else {

    g.setFont(new Font("Arial", Font.BOLD, 20));
    
   
    g.setColor(new Color(0, 0, 0, 150));
    g.fillRect(10, 10, 120, 30);
    

    g.setColor(Color.GREEN);
    g.drawString("Score: " + snakeBody.size(), 20, 32);
}
   
   
    }
    

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){


        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        if(snakeBody.size() > 0){
            for(int i = snakeBody.size() - 1; i > 0; i--){
                snakeBody.get(i).x = snakeBody.get(i-1).x;
                snakeBody.get(i).y = snakeBody.get(i-1).y;
            }

            snakeBody.get(0).x = snakeHead.x;
            snakeBody.get(0).y = snakeHead.y;
        }


        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for(int i = 0; i< snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
       repaint();
       if(gameOver){
           gameLoop.stop();
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }

        if(key == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }

        if(key == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        } 

        if(key == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
       
    }


    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
