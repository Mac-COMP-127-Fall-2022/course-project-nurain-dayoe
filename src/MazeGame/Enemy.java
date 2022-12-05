package MazeGame;
import java.util.ArrayList;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class Enemy {
    
    private Player mainPlayer; 
    private double responseRadiusSquared;
    private EnemyCamp enemyCamp;
    private Random rand = new Random();

    public final static double SPEED = 10;
    private final double WIDTH, HEIGHT;

    private Point position;
    private int healthStatus;
    private PlayerImage animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
    private PlayerImage animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
    private PlayerImage animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
    private PlayerImage animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");
    private GraphicsGroup maze;
    private Image graphic = new Image(animMapFront.next());

    public Enemy(EnemyCamp enemyCamp, Player mainPlayer,GraphicsGroup maze){
        this.enemyCamp = enemyCamp;
        this.mainPlayer = mainPlayer;
        this.maze = maze;
        

        // canvas.add(line1);
        // canvas.add(line2);

        
        position = graphic.getPosition();
        WIDTH = graphic.getWidth();
        HEIGHT = graphic.getHeight();
        
        
        responseRadiusSquared  = 10000;
        graphic = new Image("anim1Front.bmp");
    }
    

    public void AI(){
        if (Math.pow(graphic.getCenter().getX()- mainPlayer.getGraphics().getCenter().getX(),2) + Math.pow(graphic.getCenter().getY()- mainPlayer.getGraphics().getCenter().getY(),2) < responseRadiusSquared){
            boolean collision = false;
            if (!collision){
                Integer [] weights = new Integer[4];// north , east, south , west 
                weights[0] = -1;
                weights[1] = -1;
                weights[2] = -1;
                weights[3] = -1;
                if(mainPlayer.getPosition().getX()> graphic.getCenter().getX())
                    weights[1] = 1;
                else if (mainPlayer.getPosition().getY()<graphic.getCenter().getY())
                    weights[3] = 3;
                
                
                if (mainPlayer.getPosition().getY()>graphic.getCenter().getY())
                    weights[2] = 2;
                else if (mainPlayer.getPosition().getY()<graphic.getCenter().getY())
                    weights[0] = 0;
                
                ArrayList<Integer> nonNegativeWeightsIndex = new ArrayList<Integer>();
                
                for (int weight: weights){
                    if (weight>-1){
                        nonNegativeWeightsIndex.add(weight);
                    }
                }
                if (nonNegativeWeightsIndex.size()>0){
                    
                    int randomIndex = rand.nextInt(nonNegativeWeightsIndex.size());
                    int randomMovementIndex = nonNegativeWeightsIndex.get(randomIndex);
                    
                    switch (randomMovementIndex){
                        case 0:
                            if (!collision(MazeGame.Side.TOP)){
                                move(MazeGame.Side.TOP);
                                
                            }
                            
                            break;
                        case 1:
                            if (!collision(MazeGame.Side.RIGHT)){
                                move(MazeGame.Side.RIGHT);
                                System.out.println("h");
                            }
                            break;
                        case 2 : 
                            if (!collision(MazeGame.Side.BOTTOM)){
                                move(MazeGame.Side.BOTTOM);
                                System.out.println("h");
                            }
                            break;
                        case 3: 
                            if (!collision(MazeGame.Side.LEFT)){
                                move(MazeGame.Side.LEFT);
                                System.out.println("h");
                            }
                            break;
                    }
                
                }else{
                    System.out.println("hitting player.");
                }
            }
        
            
            //chase player but stay within certain range. 
        }else{

        }
        
    }
    public void addToGraphicsGroup(GraphicsGroup gr, Point initialPosition){
        gr.add(graphic);
        graphic.setCenter((initialPosition.getX()) * 40 + graphic.getWidth() * 0.25, (initialPosition.getY()) * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);
    }
    public void addToCanvas(CanvasWindow canvas, Point initialPosition){
        canvas.add(graphic);
        graphic.setCenter((initialPosition.getX()) * 40 + graphic.getWidth() * 0.25, (initialPosition.getY()) * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);
    }
    public Image getGraphics(){
        return graphic;
    }
    

    // private Line line1 = new Line(0, 0, 0, 0);
    // private Line line2 = new Line(0, 0, 0, 0);
    


    public Point getPosition() {
        return position;
    }

    public void move(MazeGame.Side side) {
        
        if (!collision(side)) {
            Point newPosition = position.add(MazeGame.directionVectors.get(side));
            position = newPosition;
            graphic.setPosition(newPosition);
        }
    }
    public void addHealth(){
        this.healthStatus +=1;
    }
    public void removeHealth(){
        this.healthStatus -=1;
    }
    public int getHealthStatus(){
        int healthCopy = this.healthStatus;
        return  healthCopy;
    }
    public void changeImage(MazeGame.Side side) {
        System.out.println("movement capture");
        switch (side) {
            case RIGHT:
                graphic.setImagePath(animMapRight.next());
                break;
            case LEFT:
                graphic.setImagePath(animMapLeft.next());
                break;
            case TOP:
                graphic.setImagePath(animMapBack.next());
                break;
            case BOTTOM:
                graphic.setImagePath(animMapFront.next());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean collision(MazeGame.Side side) {
        Point moveVector = MazeGame.directionVectors.get(side);
        Point point1, point2, point3;
        Point newPosition = position.add(moveVector);
        double x = newPosition.getX(), y = newPosition.getY();
        System.out.println(side);
        switch (side) {
            case RIGHT:
                point1 = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.35);
                point2 = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.5);
                point3 = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.65);
                break;
            case LEFT:
                point1 = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.35);
                point2 = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.5);
                point3 = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.65);
                break;
            case TOP:
                point1 = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.35);
                point2 = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.35);
                point3 = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.35);
                break;
            case BOTTOM: 
                point1 = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.65);
                point2 = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.65);
                point3 = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.65);
                break;
            default:
                throw new IllegalArgumentException();
        }

        // line1.setStartPosition(new Point(x + WIDTH * 0.65, y + HEIGHT * 0.35));
        // line1.setEndPosition(new Point(x + WIDTH * 0.65, y + HEIGHT * 0.65));
        // line2.setStartPosition(new Point(x + WIDTH * 0.35, y + HEIGHT * 0.35));
        // line2.setEndPosition(new Point(x + WIDTH * 0.65, y + HEIGHT * 0.35));
        System.out.println(maze.getElementAt(point1));
        System.out.println(maze.getElementAt(point2));
        System.out.println(maze.getElementAt(point3));
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {

            return true;
            
        }
    
        Point right = new Point(x + WIDTH * 0.7, y + HEIGHT * 0.5);
        Point left = new Point(x + WIDTH * 0.3, y + HEIGHT * 0.5);
        Point top = new Point(x + WIDTH * 0.5, y + WIDTH * 0.3);
        Point bottom = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.7);
        if (right.getX() >= MazeGame.CANVAS_WIDTH || left.getX() <= 0 || top.getY() <= 0 || bottom.getY() >= MazeGame.CANVAS_HEIGHT) {
            return true;
        }
        
        return false;
    }
    
}
