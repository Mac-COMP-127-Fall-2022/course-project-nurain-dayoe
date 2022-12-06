package MazeGame;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.util.Map;
import java.util.HashMap;


public class MazeGame {
    public static enum Side {RIGHT, LEFT, UP, DOWN}
    public final static int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 1000;
    public static final HashMap<Side, Point> directionVectors = new HashMap<Side, Point>(Map.of(Side.RIGHT, new Point(1,0), Side.LEFT, new Point(-1, 0), Side.UP, new Point(0, -1), Side.DOWN, new Point(0, 1)));

    private CanvasWindow canvas;
    private Player zelda;
    
    private Hearts hearts;
    private GraphicsGroup maze = new GraphicsGroup();
    private algo algo;
    
    private Minimap minimap;

    
    private Image startImageButton;
    private Image cutSceneBG;
    private Image groundBackGround; 
    public static int level ; 
    private boolean cutScene1shown = false;
    public MazeGame() {
        canvas = new CanvasWindow("Breath of the Maze", CANVAS_WIDTH, CANVAS_HEIGHT);
        level = 0 ;
        if (!cutScene1shown){
            cutSceneBG = new Image(0,0,"cutscene1.jpg");
            startImageButton = new Image("start.jpg");
            startImageButton.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/2 + CANVAS_HEIGHT/4);
            canvas.onClick((i)->{
                if (!cutScene1shown){
                    canvas.remove(cutSceneBG);
                    canvas.remove(startImageButton);
                    cutScene1shown = true;
                }
            });
        }
        resetGame();



        canvas.onKeyDown((key)->{
            move(key.getKey());
        });
    }

    public static void main(String[] args){
        MazeGame game = new MazeGame();
    }
    
    private void resetGame(){
        algo = new algo();
        maze = algo.getMaze();
        
        groundBackGround = new Image("ground.jpg");
        canvas.add(groundBackGround);
        
        canvas.add(maze);
        
        maze.setScale(0.25, 0.25);
        maze.setCenter(canvas.getCenter());
        canvas.screenShot("res/mazeminimap.jpg");
        maze.setScale(1,1);

        canvas.pause(1000);

        minimap = new Minimap(220,canvas);
        minimap.addToCanvas(canvas);
        minimap.setTargetLocation(algo.getEndingPoint().getX() * 40 + 20, algo.getEndingPoint().getY() * 40 + 20);
        
        this.zelda = new Player(canvas, maze, minimap, algo.getBeginningPoint());

        maze.setPosition(0, 0);
        hearts = new Hearts(zelda.getHealthStatus(),canvas);
        hearts.addToCanvas(canvas);

        EnemyCamp enemyCamp = new EnemyCamp(4);
        Enemy enemy = new Enemy(canvas, maze, enemyCamp, zelda);
        enemy.addToGraphicsGroup(maze, algo.getBeginningPoint());

        canvas.animate(x -> enemy.AI());

        if(!cutScene1shown){
            canvas.add(cutSceneBG);
            canvas.add(startImageButton);
        }

    }
    
    public void move(Key key) {
        if (key == Key.UP_ARROW){
            scroll(Side.UP);
        }else if(key == Key.DOWN_ARROW){
            scroll(Side.DOWN);
        }else if (key == Key.RIGHT_ARROW){
            scroll(Side.RIGHT);
        }else if (key == Key.LEFT_ARROW){
            scroll(Side.LEFT);
        }
        Rectangle temp = new Rectangle(0,0,40,40);
        temp.setPosition(algo.getEndingPoint().getX()*40-3000,(algo.getEndingPoint().getY()*40)-3000);
        canvas.add(temp);
        double deltaX = Math.abs(zelda.getGraphics().getCenter().getX()-((algo.getEndingPoint().getX()*40)-3000+20));
        double deltaY = Math.abs(zelda.getGraphics().getCenter().getY()-((algo.getEndingPoint().getY()*40)-3000+20));
        System.out.println(deltaX);
        System.out.println(deltaY);

        System.out.println("_________");
        if (deltaX<=10 && deltaY<=10){
            canvas.removeAll();
            resetGame();
            System.out.println( "Level passed ");
        }
    }

    public void scroll(Side side){
        Point scrollVector = directionVectors.get(side);
        for (double i = 0; i < Player.SPEED; i++) {
            if (!zelda.collision(side)){
                double newX =  -scrollVector.getX() + maze.getPosition().getX();
                double newY = -scrollVector.getY() + maze.getPosition().getY();
                double newPlayerX = zelda.getPosition().add(directionVectors.get(side)).getX(), newPlayerY = zelda.getPosition().add(directionVectors.get(side)).getY();
                if ((newPlayerX > 550 && side == Side.LEFT) || (newPlayerX < 450 && side == Side.RIGHT) || (newPlayerY > 550 && side == Side.UP) || (newPlayerY < 450 && side == Side.DOWN)) {
                    
                    zelda.move(side);
                } else if (newX >= CANVAS_WIDTH - maze.getWidth() && newY >= CANVAS_HEIGHT - maze.getHeight() && newX <= 0 && newY <= 0){
                    maze.setPosition(newX, newY);
                    groundBackGround.setPosition(newX,newY);
                } else {
                    zelda.move(side);
                }
            }
        }
        minimap.setPosition(zelda.getGraphics().getCenter().getX()+Math.abs(maze.getPosition().getX()),zelda.getGraphics().getCenter().getY() + Math.abs(maze.getPosition().getY()));
        
        zelda.changeImage(side);
    }
}
