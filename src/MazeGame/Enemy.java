package MazeGame;

import MazeGame.MazeGame.Side;
import edu.macalester.graphics.*;

/**
 * An Enemy that moves toward the Player when the Player is close by to attack.
 */
public class Enemy extends Character{
    private static double SCALE_FACTOR = 0.7;
    private final Player mainPlayer;
    private GraphicsGroup enemyGroup;
    
    public Enemy(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup minimap, EnemyCamp enemyCamp, Player mainPlayer, GraphicsGroup enemyGroup, Point position){
        super(canvas, maze, minimap, enemyGroup);
        this.mainPlayer = mainPlayer;
        this.enemyGroup = enemyGroup;
        this.position = position;

        animMapFront = new PlayerImage("resPack/front1.jpg", "resPack/front2.jpg", "resPack/front3.jpg");
        animMapLeft = new PlayerImage("resPack/left1.jpg", "resPack/left2.jpg", "resPack/left3.jpg");
        animMapRight = new PlayerImage("resPack/right1.jpg", "resPack/right2.jpg", "resPack/right3.jpg");
        animMapBack = new PlayerImage("resPack/back1.jpg", "resPack/back2.jpg", "resPack/back3.jpg");

        graphic = new Image(animMapFront.next());

        WIDTH = graphic.getImageWidth() * SCALE_FACTOR;
        HEIGHT = graphic.getHeight() * SCALE_FACTOR;
        
        graphic = new Image("resPack/front1.jpg");

        graphic.setAnchor(0,0);
        graphic.setScale(SCALE_FACTOR);

        graphic.setCenter(position);
    }
    
    /**
     * If this Enemy is on the screen, move it up to 10 pixels in the x and y directions toward the player
     */
    public void moveTowardPlayer() {
        double enemyCenterX = graphic.getCenter().getX();
        double enemyCenterY = graphic.getCenter().getY();
        double playerCenterX = mainPlayer.getGraphics().getCenter().getX() + Math.abs(maze.getX());
        double playerCenterY = mainPlayer.getGraphics().getCenter().getY() + Math.abs(maze.getY());
        if (graphic.getX() + graphic.getWidth() + enemyGroup.getX() + 20 < MazeGame.CANVAS_WIDTH && graphic.getX() + enemyGroup.getX() > 20 && graphic.getY() + enemyGroup.getY() + graphic.getHeight() + 20 < MazeGame.CANVAS_HEIGHT && graphic.getY() + enemyGroup.getX() > 20) {
            if (enemyCenterX > playerCenterX + 10) {
                move(Side.LEFT);
            } 
            if (enemyCenterX + 10 < playerCenterX) {
                move(Side.RIGHT);
            }
            if (enemyCenterY > playerCenterY + 10) {
                move(Side.UP);
            }
            if (enemyCenterY + 10 < playerCenterY) {
                move(Side.DOWN);
            }
        }
    }

    @Override
    public void move(Side side) {
        for (int i = 0; i < 10; i++) {
            if (!collision(side)) {
                Point newPosition = position.add(side.getDirectionVector());
                position = newPosition;
                graphic.setPosition(newPosition);
            }
        }
        changeImage(side);
    }

    @Override
    public boolean collision(Side side) {
        Point point1, point2, point3;
        double x = graphic.getPosition().add(side.getDirectionVector().scale(2)).add(enemyGroup.getPosition()).getX();
        double y = graphic.getPosition().add(side.getDirectionVector().scale(2)).add(enemyGroup.getPosition()).getY();
        
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
        
        //If there is an obstacle at any checked point, there is a collision
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
            return true;
        }
        
        //If the enemy will hit the player, decrement the player health
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
