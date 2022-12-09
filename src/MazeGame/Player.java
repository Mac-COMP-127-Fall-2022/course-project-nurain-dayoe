package MazeGame;
import edu.macalester.graphics.*;
import java.time.Instant;

/**
 * A visual player that can move along the road with arrow key presses.
 */
public class Player extends Character{

    GraphicsGroup destinationGroup;
    Instant timeSinceLiveLost = Instant.now();
    
    /**
     * Generate a new player at the given inital position within the canvas.
     */
    public Player(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup destinationGroup, Minimap minimap, Point initialPosition,GraphicsGroup enemyGroup){
        super(canvas, maze, minimap.getGraphics(),enemyGroup);
        this.healthStatus = 5;
        this.destinationGroup = destinationGroup;

        animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
        animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
        animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
        animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");

        graphic = new Image(animMapFront.next());

        graphic.setCenter(initialPosition.getX() * 40 + 20, initialPosition.getY() * 40 + 20);
        graphic.setScale(0.3);
        
        position = graphic.getPosition();
        WIDTH = graphic.getWidth();
        HEIGHT = graphic.getHeight();

        minimap.setPlayerPosition(graphic.getCenter().getX(), graphic.getCenter().getY());
    }

    public boolean atDestination(MazeGame.Side side) {
        Point point;
        double x = graphic.getX();
        double y = graphic.getY();
        switch (side) { //Set the collision points to check based on the direction of movement
            case RIGHT:
                point = new Point(x + WIDTH * 0.65, y + HEIGHT * 0.35);
                break;
            case LEFT:
                point = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.35);
                break;
            case UP:
                point = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.35);
                break;
            case DOWN: 
                point = new Point(x + WIDTH * 0.35, y + HEIGHT * 0.65);
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (destinationGroup.getElementAt(point) != null) {
            return true;
        }

        return false;
    }

    /**
     * Move the player one pixel to the specfied side, if there is no collision.
     */
    @Override
    public void move(MazeGame.Side side) {
        if (!collision(side)) {
            Point newPosition = position.add(side.getDirectionVector());
            position = newPosition;
            graphic.setPosition(newPosition);
        }
    }

    @Override
    public boolean collision(MazeGame.Side side) {
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
        if (enemyGroup.getElementAt(point1) != null || enemyGroup.getElementAt(point2) != null || enemyGroup.getElementAt(point3) != null) {
            decrementHealth();
            return true;
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
        if (right.getX() >= MazeGame.CANVAS_WIDTH || left.getX() <= 0 || up.getY() <= 0 || down.getY() >= MazeGame.CANVAS_HEIGHT) {
            return true;
        }
        
        return false;
    }

    public void decrementHealth() {
        if (Instant.now().minusSeconds(1).isAfter(timeSinceLiveLost)) {
            healthStatus--;
            Hearts.update(healthStatus);
            timeSinceLiveLost = Instant.now();
        }
    }

    public boolean isDead() {
        return healthStatus <= 0;
    }
}
