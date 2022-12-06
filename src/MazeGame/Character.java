package MazeGame;

import edu.macalester.graphics.*;

/**
 * A Character shown on the board capable of moving at SPEED, animating, and detecting for collisions with the game board.
 */
public abstract class Character {
    public final static double SPEED = 10;

    protected PlayerImage animMapLeft;
    protected PlayerImage animMapFront;
    protected PlayerImage animMapRight;
    protected PlayerImage animMapBack;

    protected Image graphic;
    protected GraphicsGroup maze;

    protected Point position;
    protected int healthStatus;

    protected double WIDTH, HEIGHT;

    public Character(CanvasWindow canvas, GraphicsGroup maze) {
        this.maze = maze;
    }

    public Image getGraphics() {
        return graphic;
    }

    public Point getPosition() {
        return position;
    }

    public void addHealth(){
        healthStatus +=1;
    }

    public void removeHealth(){
        healthStatus -=1;
    }
    
    public int getHealth(){
        return healthStatus;
    }

     /**
     * Toggles the image of the character to give the illusion of walking
     * @param side The direction Character is walking
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
     * Move this Character's graphic in the specified direction
     */
    public void move(MazeGame.Side side) {
        if (!collision(side)) {
            Point newPosition = position.add(side.getDirectionVector());
            position = newPosition;
            graphic.setPosition(newPosition);
        }
    }

    /**
     * Check whether this Character will collide with a maze wall if it moves one unit to the specified side.
     * @return true if a collision will occur
     */
    public boolean collision(MazeGame.Side side) {
        Point point1, point2, point3;
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
        
        //If there is an obstacle at any checked point, there will be a collision
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
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
}
