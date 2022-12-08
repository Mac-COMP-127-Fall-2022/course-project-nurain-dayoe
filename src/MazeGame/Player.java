package MazeGame;
import edu.macalester.graphics.*;

/**
 * A visual player that can move along the road with arrow key presses.
 */
public class Player extends Character{

    GraphicsGroup destinationGroup;
    
    /**
     * Generate a new player at the given inital position within the canvas.
     */
    public Player(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup destinationGroup, Minimap minimap, Point initialPosition){
        super(canvas, maze, minimap.getGraphics());
        this.healthStatus = 5;
        this.destinationGroup = destinationGroup;

        animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
        animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
        animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
        animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");

        graphic = new Image(animMapFront.next());

        graphic.setCenter(initialPosition.getX() * 40 + 20, initialPosition.getY() * 40 + 20);
        graphic.setScale(0.3);
        canvas.add(graphic);
        
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
}
