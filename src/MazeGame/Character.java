package MazeGame;

import edu.macalester.graphics.*;

/**
 * A Character shown on the board capable of moving at SPEED, animating, and detecting for collisions with the game board.
 */
public abstract class Character {
    public final static double SPEED = 20;

    protected PlayerImage animMapLeft;
    protected PlayerImage animMapFront;
    protected PlayerImage animMapRight;
    protected PlayerImage animMapBack;

    protected CanvasWindow canvas;
    protected Image graphic;
    protected GraphicsGroup maze, minimap;

    protected Point position;
    protected int healthStatus;
    protected GraphicsGroup enemyGroup;
    protected double WIDTH, HEIGHT;

    public Character(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup minimap,GraphicsGroup enemyGroup) {
        this.canvas = canvas;
        this.maze = maze;
        this.minimap = minimap;
        this.enemyGroup = enemyGroup;
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
    public abstract void move(MazeGame.Side side);

    /**
     * Check whether this Character will collide with a maze wall if it moves one unit to the specified side.
     * @return true if a collision will occur
     */
    public abstract boolean collision(MazeGame.Side side);
}
