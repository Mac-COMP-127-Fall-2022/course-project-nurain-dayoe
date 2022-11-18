package MazeGame;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;


public class MazeGame {
    
    private CanvasWindow canvas ;
    private Player zelda;
    private GraphicsGroup maze = new GraphicsGroup();
    public final static double CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 1000, SPEED = 10;

    public MazeGame() { 
        resetGame();
        canvas.onKeyDown((key)->{
            move(key.getKey());
        });
    }
    public static void main(String[] args){
        new MazeGame();
    }
    protected void resetGame(){
        canvas = new CanvasWindow("Breath of the Maze", 800, 400);
        this.zelda = new Player(canvas, maze);
        maze.setPosition(0,0);
    }

    public void move(Key key) {
        if (key == Key.UP_ARROW){
            scroll(new Point (0,-SPEED));
        }else if(key == Key.DOWN_ARROW){
            scroll(new Point (0, SPEED));
        }else if (key == Key.RIGHT_ARROW){
            scroll(new Point (SPEED,0));
        }else if (key == Key.LEFT_ARROW){
            scroll(new Point (-SPEED,0));
        }
    }

    public void scroll(Point scrollVector){
        if (maze.getPosition().getX() > 0 && maze.getPosition().getY() > 0 && maze.getPosition().getX() + maze.getWidth() < CANVAS_WIDTH && maze.getPosition().getY() + maze.getHeight() < CANVAS_HEIGHT) {
            double newX =  scrollVector.getX() + maze.getPosition().getX();
            double newY = scrollVector.getY() + maze.getPosition().getY();
            maze.setPosition(newX, newY);
        }
        zelda.move(scrollVector);
    }
}
