package MazeGame;
import java.util.ArrayList;
import java.util.Random;

import MazeGame.MazeGame.Side;
import edu.macalester.graphics.*;

/**
 * An Enemy that moves toward the Player when the Player is close by to attack.
 */
public class Enemy extends Character{
    private final Player mainPlayer; 
    private static final double responseRadius = 200;
    private EnemyCamp enemyCamp;

    private int stepCounter = -1;

    private CanvasWindow canvas;
    
    public Enemy(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup minimap, EnemyCamp enemyCamp, Player mainPlayer, GraphicsGroup enemyGroup){
        super(canvas, maze, minimap,enemyGroup);
        this.enemyCamp = enemyCamp;
        this.mainPlayer = mainPlayer;
        this.canvas = canvas;

        animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
        animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
        animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
        animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");

        graphic = new Image(animMapFront.next());

        WIDTH = graphic.getImageWidth();
        HEIGHT = graphic.getHeight();
        
        graphic = new Image("anim1Front.bmp");

        //TODO: Set init position
        //graphic.setCenter(enemyCamp.getGraphics().getCenter().getX() * 40 + graphic.getWidth() * 0.25, enemyCamp.getGraphics().getCenter().getX() * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);

        graphic.setCenter(MazeGenerator.getBeginningPoint().getX() * 40 + 20, MazeGenerator.getBeginningPoint().getY() * 40 + 20);
        
        canvas.add(graphic);
        
    }
    
    public void moveTowardPlayer() {
        position = graphic.getPosition().add(maze.getPosition().scale(-1));
        if (distanceToPlayer() < responseRadius) {
            if (Math.abs(mainPlayer.getGraphics().getCenter().getX() - graphic.getCenter().getX()) > Math.abs(mainPlayer.getGraphics().getCenter().getY() - graphic.getCenter().getY())) {
                if (graphic.getCenter().getX() > mainPlayer.getGraphics().getCenter().getX()) {
                    move(Side.LEFT);
                } else {
                    move(Side.RIGHT);
                }
            } else {
                if (graphic.getCenter().getY() > mainPlayer.getGraphics().getCenter().getY()) {
                    move(Side.UP);
                } else {
                    move(Side.DOWN);
                }
            }
        }
        
        // if (stepCounter == -1) {
        //     if (planMove((int) MazeGenerator.getBeginningPoint().getX(), (int) MazeGenerator.getBeginningPoint().getY(), xEnd, yEnd, 0, roadMatrix)) {
        //         stepCounter = movementPlan.size() - 1;
        //         System.out.println(movementPlan.get(stepCounter));
        //         scroll(movementPlan.get(stepCounter));
        //         stepCounter--;
        //     }
        // } else {
        //     System.out.println(stepCounter);
        //     System.out.println(movementPlan.get(stepCounter));
        //     scroll(movementPlan.get(stepCounter));
        //     stepCounter--;
        // }
        
    }

    public double distanceToPlayer() {
        double playerX = mainPlayer.getGraphics().getCenter().getX() + Math.abs(maze.getPosition().getX());
        double playerY = mainPlayer.getGraphics().getCenter().getY() + Math.abs(maze.getPosition().getY());
        return Math.sqrt(Math.pow((playerX - graphic.getCenter().getX()),2) + Math.pow((playerY - graphic.getCenter().getY()),2));
    }

    @Override
    public boolean collision(Side side) {
        Point point1, point2, point3;
        Point position = graphic.getPosition();
        double x = position.add(side.getDirectionVector()).getX();
        double y = position.add(side.getDirectionVector()).getY();
        
        switch (side) { //Set the collision points to check based on the direction of movement
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
            case UP:
                point1 = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.35);
                point2 = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.35);
                point3 = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.35);
                break;
            case DOWN: 
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
        
        //If there is an obstacle at any checked point, there is a collision
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
            return true;
        }
        
        //If the Character will hit the minimap, there is a collision
        if (minimap.getElementAt(point1) != null || minimap.getElementAt(point2) != null || minimap.getElementAt(point3) != null) {
            return true;
        }

        //if any side of the Character would move out of bounds, there is a collision
        Point right = new Point(x + WIDTH * 0.7, y + HEIGHT * 0.5);
        Point left = new Point(x + WIDTH * 0.3, y + HEIGHT * 0.5);
        Point up = new Point(x + WIDTH * 0.5, y + WIDTH * 0.3);
        Point down = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.7);
        if (right.getX()  >= MazeGame.CANVAS_WIDTH + Math.abs(maze.getPosition().getX()) || left.getX() <= 0 + Math.abs(maze.getPosition().getX()) || up.getY() <= 0 + Math.abs(maze.getPosition().getY()) || down.getY() >= MazeGame.CANVAS_HEIGHT + Math.abs(maze.getPosition().getY())) {
            return true;
        }
        
        return false;
    }
}
