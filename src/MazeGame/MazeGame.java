package MazeGame;

import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;
import java.util.Map;
import java.util.HashMap;


public class MazeGame {
    public static enum Side {RIGHT, LEFT, TOP, BOTTOM}
    public final static int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 1000;
    public static final HashMap<Side, Point> directionVectors = new HashMap<Side, Point>(Map.of(Side.RIGHT, new Point(1,0), Side.LEFT, new Point(-1, 0), Side.TOP, new Point(0, -1), Side.BOTTOM, new Point(0, 1)));

    private CanvasWindow canvas ;
    private Player zelda;
    // private GraphicsGroup maze = new GraphicsGroup();
    MazeGenerator i = new MazeGenerator();
    private GraphicsGroup maze = i.getMap();
    
    private Minimap minimap;
    public MazeGame() {

        resetGame();
        
        canvas.onKeyDown((key)->{
            move(key.getKey());
        });
    }
    public static void main(String[] args){
        MazeGame game = new MazeGame();
    }
    protected void resetGame(){
        canvas = new CanvasWindow("Breath of the Maze", CANVAS_WIDTH, CANVAS_HEIGHT);
        
        Rectangle pathBg = new Rectangle(0,0, 2000,2000);
        pathBg.setFillColor(Color.LIGHT_GRAY);
        canvas.add(pathBg);
        canvas.add(maze);
        
        maze.setScale(0.25, 0.25);
        maze.setCenter(canvas.getCenter());
        canvas.screenShot("res/maze1minimap.jpg");
        maze.setScale(1,1);

        minimap = new Minimap(220,canvas);
        minimap.addToCanvas(canvas);
        this.zelda = new Player(canvas, maze, minimap);

        maze.setPosition(-220, 0);
    }

    public void move(Key key) {
        if (key == Key.UP_ARROW){
            scroll(Side.TOP);
        }else if(key == Key.DOWN_ARROW){
            scroll(Side.BOTTOM);
        }else if (key == Key.RIGHT_ARROW){
            scroll(Side.RIGHT);
        }else if (key == Key.LEFT_ARROW){
            scroll(Side.LEFT);
        }
    }

    public void scroll(Side side){
        Point scrollVector = directionVectors.get(side);
        for (double i = 0; i < Player.SPEED; i++) {
            if (!zelda.collision(side)){
                double newX =  -scrollVector.getX() + maze.getPosition().getX();
                double newY = -scrollVector.getY() + maze.getPosition().getY();
                double newPlayerX = zelda.getPosition().add(directionVectors.get(side)).getX(), newPlayerY = zelda.getPosition().add(directionVectors.get(side)).getY();
                if ((newPlayerX > 550 && side == Side.LEFT) || (newPlayerX < 450 && side == Side.RIGHT) || (newPlayerY > 550 && side == Side.TOP) || (newPlayerY < 450 && side == Side.BOTTOM)) {
                    
                    zelda.move(side);
                } else if (newX >= CANVAS_WIDTH - maze.getWidth() && newY >= CANVAS_HEIGHT - maze.getHeight() && newX <= 0 && newY <= 0){
                    maze.setPosition(newX, newY);
                } else {
                    zelda.move(side);
                }
            }
        }
        minimap.setPosition(zelda.getGraphics().getCenter().getX()+Math.abs(maze.getPosition().getX()),zelda.getGraphics().getCenter().getY() + Math.abs(maze.getPosition().getY()));
        
        zelda.changeImage(side);
    }
}
