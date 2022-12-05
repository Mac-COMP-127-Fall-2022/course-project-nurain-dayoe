package MazeGame;

import edu.macalester.graphics.*;

public abstract class Character {
    protected PlayerImage animMapLeft;
    protected PlayerImage animMapFront;
    protected PlayerImage animMapRight;
    protected PlayerImage animMapBack;

    protected Image graphic;
    protected GraphicsGroup maze;

    protected Point position;
    protected int healthStatus;

    public final static double SPEED = 10;
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
    
    public int getHealthStatus(){
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

    public void move(MazeGame.Side side) {
        if (!collision(side)) {
            Point newPosition = position.add(MazeGame.directionVectors.get(side));
            position = newPosition;
            graphic.setPosition(newPosition);
        }
    }

    public boolean collision(MazeGame.Side side) {
        Point moveVector = MazeGame.directionVectors.get(side);
        Point point1, point2, point3;
        Point newPosition = position.add(moveVector);
        double x = newPosition.getX(), y = newPosition.getY();
        switch (side) {
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
        
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
            return true;
        }
    
        Point right = new Point(x + WIDTH * 0.7, y + HEIGHT * 0.5);
        Point left = new Point(x + WIDTH * 0.3, y + HEIGHT * 0.5);
        Point UP = new Point(x + WIDTH * 0.5, y + WIDTH * 0.3);
        Point DOWN = new Point(x + WIDTH * 0.5, y + HEIGHT * 0.7);
        if (right.getX() >= MazeGame.CANVAS_WIDTH || left.getX() <= 0 || UP.getY() <= 0 || DOWN.getY() >= MazeGame.CANVAS_HEIGHT) {
            return true;
        }
        
        return false;
    }
}
