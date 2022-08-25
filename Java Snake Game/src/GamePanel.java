import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth=600;
    static final int screenHeight=600;
    static int unitSize=25;
    int speed=75;
    static int game_unit=(screenHeight*screenWidth)/(unitSize * unitSize);
    int[] x=new int[game_unit];
    int [] y=new int[game_unit];
    boolean running=false;
    int bodyParts=6;
    int appelEaten;
    int appelx;
    int appely;
    char direction='R';
    Random random;
    Timer timer;

    GamePanel(){

        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame(){
        running=true;
        newApple();
//        timer = new Timer(speed,this);
//        timer.start();
        snakeSpeed();
    }
    public void snakeSpeed(){
        timer = new Timer(speed,this);
        timer.start();
    }
    public void move(){
        for (int i=bodyParts; i>0; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]=y[0]-unitSize;
                break;

            case 'D':
                y[0]=y[0]+unitSize;
                break;
            case 'L':
                x[0]=x[0]-unitSize;
                break;
            case 'R':
                x[0]=x[0]+unitSize;
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if(running) {
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
            g.setColor(Color.red);
            g.fillOval(appelx, appely, unitSize, unitSize);

            for(int i = 0; i< bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
                else {
                    g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
//            g.setColor(Color.red);
//            g.setFont( new Font("Ink Free",Font.BOLD, 40));
//            FontMetrics metrics = getFontMetrics(g.getFont());
//            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    public void gameOver(Graphics g){

        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());

        g.drawString("Score: "+appelEaten, (screenWidth - metrics1.stringWidth("Score: "+appelEaten))/2, g.getFont().getSize());


        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight/2);

        //printing heighest score
        /*
        g.setFont( new Font("Ink Free",Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Heighest Score : ",(screenWidth -metrics.stringWidth("Heighest Score : "))/2,screenHeight-50);
//        g.drawString("Game Over", (screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight/2); */


    }
    public void newApple(){
        appelx= random.nextInt((int)(screenWidth/unitSize))*unitSize;
        appely=random.nextInt((int)(screenHeight/unitSize))*unitSize;


    }
    public void checkApple(){
        if (appelx==x[0] && appely==y[0]){
            bodyParts++;
            appelEaten++;
//            speed-=1;
//            snakeSpeed();
            newApple();
        }

    }
    public void checkCollisions(){

//        checks if head collides with body
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > screenWidth) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > screenHeight) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

}


