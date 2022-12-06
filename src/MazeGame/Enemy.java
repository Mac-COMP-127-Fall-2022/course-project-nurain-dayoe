package MazeGame;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.LayoutStyle;

import MazeGame.MazeGame.Side;
import edu.macalester.graphics.*;

/**
 * An Enemy that moves toward the Player when the Player is close by to attack.
 */
public class Enemy extends Character{
    private Player mainPlayer; 
    private double responseRadiusSquared;
    private EnemyCamp enemyCamp;
    private Random rand = new Random();
    private boolean[][] roadMatrix = MazeGenerator.getRoadMatrix();
    private ArrayList<Side> movementPlan = new ArrayList<>();

    private int stepCounter = -1;

    private CanvasWindow canvas;
    
    public Enemy(CanvasWindow canvas, GraphicsGroup maze, EnemyCamp enemyCamp, Player mainPlayer){
        super(canvas, maze);
        this.enemyCamp = enemyCamp;
        this.mainPlayer = mainPlayer;

        animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
        animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
        animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
        animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");

        graphic = new Image(animMapFront.next());

        WIDTH = graphic.getImageWidth();
        HEIGHT = graphic.getHeight();
        
        responseRadiusSquared  = 10000;
        graphic = new Image("anim1Front.bmp");

        //TODO: Set init position
        //graphic.setCenter(enemyCamp.getGraphics().getCenter().getX() * 40 + graphic.getWidth() * 0.25, enemyCamp.getGraphics().getCenter().getX() * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);

        graphic.setCenter(MazeGenerator.getBeginningPoint().getX() * 40 + 20, MazeGenerator.getBeginningPoint().getY() * 40 + 20);
        position = graphic.getPosition();
        
        maze.add(graphic);
        this.canvas = canvas;
    }

    private boolean planMove(int currentX, int currentY, int endX, int endY, int moveCount, boolean[][] validBlocks) {
        position = graphic.getPosition();
        
        if (currentX == endX && currentY == endY) {
                return true;
        }
        validBlocks[currentX][currentY] = false;

        ArrayList<Side> sideOrder = new ArrayList<>(4);

        if (endY > currentY) {
            sideOrder.add(Side.DOWN);
            sideOrder.add(Side.UP);
        } else {
            sideOrder.add(Side.UP);
            sideOrder.add(Side.DOWN);
        }
        if (endX > currentX) {
            sideOrder.add(1, Side.RIGHT);
            sideOrder.add(Side.LEFT);
        } else {
            sideOrder.add(1, Side.LEFT);
            sideOrder.add(Side.RIGHT);
        }

        for (Side side : sideOrder) {
            if (planMoveDirection(currentX, currentY, endX, endY, moveCount, validBlocks.clone(), side)) {
                return true;
            }
        }

        return false;
    }

    //TODO: Finish this method to pick strategic direction.
    private boolean planMoveDirection(int currentX, int currentY, int endX, int endY, int moveCount, boolean[][] validBlocks, Side direction) {
        if (moveCount > 500) {
            return false;
        }
        position = graphic.getPosition();
        if (currentX == endX && currentY == endY) {
            return true;
        }
        validBlocks[currentX][currentY] = false;
        switch (direction) {
            case RIGHT:
                if (currentX < MazeGenerator.MAZE_SIZE - 1 && validBlocks[currentX + 1][currentY]) {
                    if (planMove(currentX + 1, currentY, endX, endY, moveCount + 1, validBlocks.clone())) {
                        movementPlan.add(Side.RIGHT);
                        return true;
                    }
                }
                break;
            case DOWN: 
                if (currentY < MazeGenerator.MAZE_SIZE - 1 && validBlocks[currentX][currentY + 1]) {
                    if (planMove(currentX, currentY + 1, endX, endY, moveCount + 1, validBlocks.clone())) {
                        movementPlan.add(Side.DOWN);
                        return true;
                    }
                }
                break;
            case LEFT: 
                if (currentX > 0 && validBlocks[currentX - 1][currentY]) {
                    if (planMove(currentX - 1, currentY, endX, endY, moveCount + 1, validBlocks.clone())) {
                        movementPlan.add(Side.LEFT);
                        return true;
                    }
                }
                break;
            case UP: 
                if (currentY > 0 && validBlocks[currentX][currentY - 1]) {
                    if (planMove(currentX, currentY - 1, endX, endY, moveCount + 1, validBlocks.clone())) {
                        movementPlan.add(Side.UP);
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
    
    public void moveTowardPlayer() {
        int xEnd = 0, yEnd = 0;
        for (int i = 10; i < 23; i++) {
            for (int j = 10; j < 23; j++) {
                if (roadMatrix[i][j]) {
                    xEnd = i;
                    yEnd = j;
                }
            }
        }

        if (stepCounter == -1) {
            if (planMove((int) MazeGenerator.getBeginningPoint().getX(), (int) MazeGenerator.getBeginningPoint().getY(), xEnd, yEnd, 0, roadMatrix)) {
                stepCounter = movementPlan.size() - 1;
                System.out.println(movementPlan.get(stepCounter));
                scroll(movementPlan.get(stepCounter));
                stepCounter--;
            }
        } else {
            System.out.println(stepCounter);
            System.out.println(movementPlan.get(stepCounter));
            scroll(movementPlan.get(stepCounter));
            stepCounter--;
        }
    }

    public void scroll(Side side){
        Point scrollVector = side.getDirectionVector();
        for (double i = 0; i < 40; i++) {
            if (!collision(side)){
                
                double newX =  -scrollVector.getX() + maze.getPosition().getX();
                double newY = -scrollVector.getY() + maze.getPosition().getY();
                double newPlayerX = position.add(side.getDirectionVector()).getX();
                double newPlayerY = position.add(side.getDirectionVector()).getY();

                //if moving the Player will keep it within the center of the screen, more the Player. Else, if moving the maze is valid, move the maze. Otherwise, move the Player.
                if (newX >= MazeGame.CANVAS_WIDTH - maze.getWidth() && newY >= MazeGame.CANVAS_HEIGHT - maze.getHeight() && newX <= 0 && newY <= 0){
                    move(side);
                }
            }
        }
        changeImage(side);
        canvas.draw();
        canvas.pause(1000);
    }
}
