package MazeGame;
import edu.macalester.graphics.*;

/**
 * A visual player that can move along the road with arrow key presses.
 */
public class Player {
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
    private Minimap minimap;

    /**
     * Generate a new player at the given inital position within the canvas.
     */
    public Player(CanvasWindow canvas, GraphicsGroup maze, Minimap minimap, Point initialPosition){
        
        this.minimap = minimap;
        this.healthStatus = 5;
        this.maze = maze;
        canvas.add(graphic);

        graphic.setCenter(initialPosition.getX() * 40, initialPosition.getY() * 40);
        System.out.println(initialPosition);
        graphic.setScale(0.3);
        position = graphic.getPosition();
        WIDTH = graphic.getWidth();
        HEIGHT = graphic.getHeight();
        canvas.add(graphic);
    }

    public Image getGraphics() {
        return graphic;
    }

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

    /**
     * Toggles the image of the player to give the illusion of walking
     * @param side The direction Player is walking
     */
    public void changeImage(MazeGame.Side side) {
        switch (side) {
            case RIGHT:
                graphic.setImagePath(animMapRight.next());
                break;
            case LEFT:
                graphic.setImagePath(animMapLeft.next());
                break;
            case UP:
                graphic.setImagePath(animMapBack.next());
                break;
            case DOWN:
                graphic.setImagePath(animMapFront.next());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Check if Player will collide with the wall if it moves in the given direction
     * @return true if there is a collision and false if not
     */
    public boolean collision(MazeGame.Side side) {
        Point moveVector = MazeGame.directionVectors.get(side);
        Point point1, point2, point3;
        Point newPosition = position.add(moveVector);
        double x = newPosition.getX(), y = newPosition.getY();
        switch (side) { //different points on the image are tested, depending on Player's orientation
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
        
        //If there is an obstacle at any point, there is a collision
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
            return true;
        }

        //if the player's new position is out of bounds, there is a collision
        Point right = new Point(x + WIDTH * 0.7, y + HEIGHT * 0.5);
        Point left = new Point(x + WIDTH * 0.3, y + HEIGHT * 0.5);
        Point top = new Point(x + WIDTH * 0.5, y + WIDTH * 0.3);
        Point DOWN = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.7);
        if (right.getX() >= MazeGame.CANVAS_WIDTH || left.getX() <= 0 || top.getY() <= 0 || DOWN.getY() >= MazeGame.CANVAS_HEIGHT) {
            return true;
        }
        
        return false;
    }
}
