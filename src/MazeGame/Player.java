package MazeGame;
import edu.macalester.graphics.*;

/**
 * A visual player that can move along the road with arrow key presses.
 */
public class Player extends Character{
    
    /**
     * Generate a new player at the given inital position within the canvas.
     */
    public Player(CanvasWindow canvas, GraphicsGroup maze, Minimap minimap, Point initialPosition){
        super(canvas, maze);
        this.healthStatus = 5;

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

        minimap.setPosition(graphic.getCenter().getX(), graphic.getCenter().getY());
    }
}
