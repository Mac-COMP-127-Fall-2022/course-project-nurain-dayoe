package MazeGame;
import edu.macalester.graphics.*;
import java.time.Instant;

/**
 * A visual player that can move along the road with arrow key presses.
 */
public class Player extends Character{
    private static double SCALE_FACTOR = 0.8;
    private GraphicsGroup destinationGroup;
    private Instant timeSinceLiveLost = Instant.now();
    
    /**
     * Generate a new player at the given inital position within the canvas.
     */
    public Player(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup destinationGroup, Minimap minimap, Point initialPosition,GraphicsGroup enemyGroup){
        super(canvas, maze, minimap.getGraphics(),enemyGroup);
        this.healthStatus = 5;
        this.destinationGroup = destinationGroup;

        animMapFront = new PlayerImage("resPack/playerfront1.jpg", "resPack/playerfront2.jpg", "resPack/playerfront3.jpg");
        animMapLeft = new PlayerImage("resPack/playerleft1.jpg", "resPack/playerleft2.jpg", "resPack/playerleft3.jpg");
        animMapRight = new PlayerImage("resPack/playerright1.jpg", "resPack/playerright2.jpg", "resPack/playerright3.jpg");
        animMapBack = new PlayerImage("resPack/playerback1.jpg", "resPack/playerback2.jpg", "resPack/playerback3.jpg");

        graphic = new Image(animMapFront.next());

        graphic.setCenter(initialPosition.getX() * 40 + 20, initialPosition.getY() * 40 + 20);
        graphic.setScale(SCALE_FACTOR);
        
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
                point = new Point(x + WIDTH, y + HEIGHT * 0.5);
                break;
            case LEFT:
                point = new Point(x, y + HEIGHT * 0.5);
                break;
            case UP:
                point = new Point(x + WIDTH + 0.5, y);
                break;
            case DOWN: 
                point = new Point(x + WIDTH * 0.5, y + HEIGHT);
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
                point1 = new Point(x + WIDTH, y);
                point2 = new Point(x + WIDTH, y + HEIGHT * 0.5);
                point3 = new Point(x + WIDTH, y + HEIGHT);
                break;
            case LEFT:
                point1 = new Point(x, y);
                point2 = new Point(x, y + HEIGHT * 0.5);
                point3 = new Point(x, y + HEIGHT);
                break;
            case UP:
                point1 = new Point(x, y);
                point2 = new Point(x + WIDTH * 0.5, y);
                point3 = new Point(x + WIDTH, y);
                break;
            case DOWN: 
                point1 = new Point(x, y + HEIGHT);
                point2 = new Point(x + WIDTH * 0.5, y + HEIGHT);
                point3 = new Point(x + WIDTH, y + HEIGHT);
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
        Point right = new Point(x + WIDTH, y + HEIGHT * 0.5);
        Point left = new Point(x, y + HEIGHT * 0.5);
        Point up = new Point(x + WIDTH * 0.5, y);
        Point down = new Point(x + WIDTH * 0.5, y + HEIGHT);
        if (right.getX() >= MazeGame.CANVAS_WIDTH || left.getX() <= 0 || up.getY() <= 0 || down.getY() >= MazeGame.CANVAS_HEIGHT) {
            return true;
        }
        
        return false;
    }

    public void decrementHealth() {
        if (Instant.now().minusSeconds(1).isAfter(timeSinceLiveLost)) {
            healthStatus--;
            Hearts.update(healthStatus);
            canvas.draw();
            timeSinceLiveLost = Instant.now();
        }
    }

    public boolean isDead() {
        return healthStatus <= 0;
    }
}
