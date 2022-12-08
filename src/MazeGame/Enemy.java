package MazeGame;
import java.util.ArrayList;
import java.util.Random;

import MazeGame.MazeGame.Side;
import edu.macalester.graphics.*;

/**
 * An Enemy that moves toward the Player when the Player is close by to attack.
 */
public class Enemy extends Character{
    private final Player mainPlayer; 
    private static final double responseRadius = 10;
    private EnemyCamp enemyCamp;

    private int stepCounter = -1;

    private CanvasWindow canvas;
    
    public Enemy(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup minimap, EnemyCamp enemyCamp, Player mainPlayer,GraphicsGroup enemyGroup){
        super(canvas, maze, minimap,enemyGroup);
        this.enemyCamp = enemyCamp;
        this.mainPlayer = mainPlayer;

        animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
        animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
        animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
        animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");

        graphic = new Image(animMapFront.next());

        WIDTH = graphic.getImageWidth();
        HEIGHT = graphic.getHeight();
        
        graphic = new Image("anim1Front.bmp");

        //TODO: Set init position
        //graphic.setCenter(enemyCamp.getGraphics().getCenter().getX() * 40 + graphic.getWidth() * 0.25, enemyCamp.getGraphics().getCenter().getX() * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);

        graphic.setCenter(MazeGenerator.getBeginningPoint().getX() * 40 + 20, MazeGenerator.getBeginningPoint().getY() * 40 + 20);
        
        canvas.add(graphic);
        this.canvas = canvas;
    }
    
    public void moveTowardPlayer() {
        if (mainPlayer.getGraphics().getCenter().distance(graphic.getCenter()) < responseRadius) {
            if (Math.abs(mainPlayer.getGraphics().getCenter().getX() - graphic.getCenter().getX()) > Math.abs(mainPlayer.getGraphics().getCenter().getY() - graphic.getCenter().getY())) {
                if (graphic.getCenter().getX() > mainPlayer.getGraphics().getCenter().getX()) {
                    move(Side.LEFT);
                } else {
                    move(Side.RIGHT);
                }
            } else {
                if (graphic.getCenter().getY() > mainPlayer.getGraphics().getCenter().getY()) {
                    move(Side.UP);
                } else {
                    move(Side.DOWN);
                }
            }
        }
        
        // if (stepCounter == -1) {
        //     if (planMove((int) MazeGenerator.getBeginningPoint().getX(), (int) MazeGenerator.getBeginningPoint().getY(), xEnd, yEnd, 0, roadMatrix)) {
        //         stepCounter = movementPlan.size() - 1;
        //         System.out.println(movementPlan.get(stepCounter));
        //         scroll(movementPlan.get(stepCounter));
        //         stepCounter--;
        //     }
        // } else {
        //     System.out.println(stepCounter);
        //     System.out.println(movementPlan.get(stepCounter));
        //     scroll(movementPlan.get(stepCounter));
        //     stepCounter--;
        // }
        
    }

    public double distanceTo(double targetX, double targetY) {
        return Math.sqrt(Math.pow((targetX - graphic.getCenter().getX()),2) + Math.pow((targetY - graphic.getCenter().getY()),2));
    }
}
