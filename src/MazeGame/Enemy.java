package MazeGame;

import java.awt.Color;

import MazeGame.MazeGame.Side;
import edu.macalester.graphics.*;

/**
 * An Enemy that moves toward the Player when the Player is close by to attack.
 */
public class Enemy extends Character{
    private final Player mainPlayer; 
    private static final double responseRadius = 1000;
    private EnemyCamp enemyCamp;
    private GraphicsGroup enemyGroup;
    
    public Enemy(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup minimap, EnemyCamp enemyCamp, Player mainPlayer, GraphicsGroup enemyGroup, Point position){
        super(canvas, maze, minimap, enemyGroup);
        this.enemyCamp = enemyCamp;
        this.mainPlayer = mainPlayer;
        this.enemyGroup = enemyGroup;
        this.position = position;

        animMapFront = new PlayerImage("resPack/front1.jpg", "resPack/front2.jpg", "resPack/front3.jpg");
        animMapLeft = new PlayerImage("resPack/left1.jpg", "resPack/left2.jpg", "resPack/left3.jpg");
        animMapRight = new PlayerImage("resPack/right1.jpg", "resPack/right2.jpg", "resPack/right3.jpg");
        animMapBack = new PlayerImage("resPack/back1.jpg", "resPack/back2.jpg", "resPack/back3.jpg");

        graphic = new Image(animMapFront.next());

        WIDTH = graphic.getImageWidth();
        HEIGHT = graphic.getHeight();
        
        graphic = new Image("resPack/front1.jpg");

        graphic.setScale(0.9);
        graphic.setCenter(position);
    }
    
    public void moveTowardPlayer() {
        double enemyCenterX = graphic.getCenter().getX();
        double enemyCenterY = graphic.getCenter().getY();
        double playerCenterX = mainPlayer.getGraphics().getCenter().getX() + Math.abs(maze.getX());
        double playerCenterY = mainPlayer.getGraphics().getCenter().getY() + Math.abs(maze.getY());
        if (distanceToPlayer(playerCenterX, playerCenterY) < responseRadius && graphic.getX() + graphic.getWidth() + enemyGroup.getX() + 20 < MazeGame.CANVAS_WIDTH && graphic.getX() + enemyGroup.getX() > 20 && graphic.getY() + enemyGroup.getY() + graphic.getHeight() + 20 < MazeGame.CANVAS_HEIGHT && graphic.getY() + enemyGroup.getX() > 20) {
            if (enemyCenterX > playerCenterX + 5 && !collision(Side.LEFT)) {
                move(Side.LEFT);
            } 
            if (enemyCenterX + 5 < playerCenterX && !collision(Side.RIGHT)) {
                move(Side.RIGHT);
            }
            if (enemyCenterY > playerCenterY + 5 && !collision(Side.UP)) {
                move(Side.UP);
            }
            if (enemyCenterY + 5 < playerCenterY && !collision(Side.DOWN)) {
                move(Side.DOWN);
            }
        }
    }

    @Override
    public void move(Side side) {
        for (int i = 0; i < 10; i++) {
            position = position.add(side.getDirectionVector());
            graphic.setPosition(position);
        }
        changeImage(side);
    }

    public double distanceToPlayer(double playerCenterX, double playerCenterY) {
        return Math.sqrt(Math.pow((playerCenterX - graphic.getCenter().getX()),2) + Math.pow((playerCenterY - graphic.getCenter().getY()),2));
    }

    @Override
    public boolean collision(Side side) {
        Point point1, point2, point3;
        double x = graphic.getPosition().add(side.getDirectionVector().scale(10)).add(enemyGroup.getPosition()).getX();
        double y = graphic.getPosition().add(side.getDirectionVector().scale(10)).add(enemyGroup.getPosition()).getY();
        
        switch (side) { //Set the collision points to check based on the direction of movement
            case RIGHT:
                point1 = new Point(x + WIDTH * 0.55, y + HEIGHT * 0.15);
                point2 = new Point(x + WIDTH * 0.55, y + HEIGHT * 0.5);
                point3 = new Point(x + WIDTH * 0.55, y + HEIGHT * 0.85);
                break;
            case LEFT:
                point1 = new Point(x + WIDTH * 0.05, y + HEIGHT * 0.15);
                point2 = new Point(x + WIDTH * 0.05, y + HEIGHT * 0.5);
                point3 = new Point(x + WIDTH * 0.05, y + HEIGHT * 0.85);
                break;
            case UP:
                point1 = new Point(x + WIDTH * 0.05, y + HEIGHT * 0.15);
                point2 = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.15);
                point3 = new Point(x + WIDTH * 0.55, y + HEIGHT * 0.15);
                break;
            case DOWN: 
                point1 = new Point(x + WIDTH * 0.05, y + HEIGHT * 0.85);
                point2 = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.85);
                point3 = new Point(x + WIDTH * 0.55, y + HEIGHT * 0.85);
                break;
            default:
                throw new IllegalArgumentException();
        }
        
        //If there is an obstacle at any checked point, there is a collision
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
            return true;
        }
        
        if (enemyGroup.getElementAt(point1) != null || enemyGroup.getElementAt(point2) != null || enemyGroup.getElementAt(point3) != null) {
            return true;
        }
        
        if (canvas.getElementAt(point1) == mainPlayer.getGraphics() || canvas.getElementAt(point2) == mainPlayer.getGraphics() || canvas.getElementAt(point3) == mainPlayer.getGraphics()) {
            mainPlayer.decrementHealth();
            return true;
        }

        //If the Character will hit the minimap, there is a collision
        if (minimap.getElementAt(point1) != null || minimap.getElementAt(point2) != null || minimap.getElementAt(point3) != null) {
            return true;
        }
        
        return false;
    }
}
