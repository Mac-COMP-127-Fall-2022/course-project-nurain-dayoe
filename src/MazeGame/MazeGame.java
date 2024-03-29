package MazeGame;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.File;

/**
 * A single-player MazeGame controlled by the arrow keys. This class contains the main method.
 */
public class MazeGame {
    public static enum Side {
        RIGHT(new Point(1, 0)), 
        LEFT(new Point(-1, 0)), 
        UP(new Point(0, -1)), 
        DOWN(new Point(0, 1));

        private Point directionVector;

        private Side(Point directionVector) {
            this.directionVector = directionVector;
        }

        public Point getDirectionVector() {
            return directionVector;
        }
    } //A enum to represent the four directions and their associated movement vectors
    
    public final static int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 1000;
    
    private static int level = -1; 

    private int animationCounter = 0;
    private boolean cutSceneShown = true, gameOver = false;

    private CanvasWindow canvas;
    private GraphicsGroup maze = new GraphicsGroup(), enemyGroup, nonCollidingElements, destinationGroup;
    private Image cutSceneBG, groundBackGround, destinationDoor; 

    private Minimap minimap;
    private Player player;
    public EnemyCamp[] camps = new EnemyCamp[4];

    private Random rand = new Random();
    private Thread deleteScreenshotThread = new Thread(), screenshotThread = new Thread();
   
    /**
     * Create a new MazeGame instance starting at level 1. 
     */
    public MazeGame() {
        canvas = new CanvasWindow("Breath of the Maze", CANVAS_WIDTH, CANVAS_HEIGHT);
        
        resetGame();

        canvas.onClick((i)->{
            if (cutSceneShown && !gameOver){
                canvas.remove(cutSceneBG);
                cutSceneShown = false;
            }
        });
        
        canvas.onKeyDown((key)->{
            if(!cutSceneShown) { 
                scroll(key.getKey());
            }
        });        
    }

    public static void main(String[] args){
        MazeGame game = new MazeGame();
    }
    
    /**
     * Reset the board for a new level or game.
     */
    private void resetGame(){
        //Sources: https://stackoverflow.com/questions/2435397/calling-invokeandwait-from-the-edt and https://stackoverflow.com/questions/7315941/java-lang-illegalthreadstateexception
        
        deleteScreenshotThread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                File mapImageFile = new File("res/mazeminimap.jpg");
                mapImageFile.delete();
            }
        });

        //Delete the old screenshot and take a new one, when this method is run. 
        screenshotThread = new Thread(new Runnable() {
            @Override
            public void run() {
                canvas.add(groundBackGround);
                canvas.add(maze);
                
                maze.setScale(0.25, 0.25);
                maze.setCenter(canvas.getCenter());
                canvas.draw();

                deleteScreenshotThread.start();
                try {
                    deleteScreenshotThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                canvas.pause(1000);
                canvas.screenShot("res/mazeminimap.jpg");
                File mapImageFile;
                do {
                    mapImageFile = new File("res/mazeminimap.jpg");
                    canvas.pause(1000);
                } while (!mapImageFile.exists());
                canvas.pause(1000);
                canvas.removeAll();
            }
        });

        MazeGenerator.generateMaze();
        maze = MazeGenerator.getMaze();

        enemyGroup = new GraphicsGroup(0,0);
        destinationGroup = new GraphicsGroup(0,0);
        destinationDoor = new Image("resPack/destinationdoor1.jpg");
        groundBackGround = new Image("ground.jpg");
        
        if (level == -1) { //When the program is run for the first time, take a screenshot for the minimap
            screenshotThread.start();
            try {
                screenshotThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            minimap = new Minimap(canvas);
        }

        canvas.add(groundBackGround);
        canvas.add(maze);
        maze.setScale(1,1);
        maze.setCenter(0,0);

        //Start the cutscene by showing the image and button
        if (level == -1) {
            cutSceneBG = new Image(0,0,"cutscene0.jpg");
        } else if (level != 0) {
            cutSceneBG = new Image(0,0,"nextLevelCutScene.jpg");
        }

        nonCollidingElements = new GraphicsGroup(0, 0);
        
        this.player = new Player(canvas, maze, destinationGroup, minimap, MazeGenerator.getBeginningPoint(),enemyGroup);

        createEnemyCamps();
        
        minimap.setTargetPosition(MazeGenerator.getEndingPoint().getX() * 40 + 20, MazeGenerator.getEndingPoint().getY() * 40 + 20);
        destinationDoor.setPosition(MazeGenerator.getEndingPoint().getX()*40,(MazeGenerator.getEndingPoint().getY()*40));
        
        destinationGroup.add(destinationDoor);
        
        maze.setPosition(0, 0);
        Hearts.generateHearts(5);

        //GraphicsObjects must be added to the canvas in a specific order.
        canvas.add(destinationGroup);
        canvas.add(Hearts.getGraphics());
        canvas.add(nonCollidingElements);
        canvas.add(enemyGroup);
        canvas.add(destinationGroup);
        canvas.add(player.getGraphics());
        if (level == -1) {
            canvas.add(minimap.getGraphics());
            level++;
        }
        canvas.add(cutSceneBG);

        canvas.animate((i)->{
            if (!cutSceneShown) {
                if (player.isDead()) {
                    endGame();
                }
                if (animationCounter % 10 == 0){ //Every ten runs of the animate function, move the enemies
                    for (EnemyCamp camp : camps) {
                        for (Enemy enemy : camp.getEnemies()) {
                            enemy.moveTowardPlayer();
                        }
                    }
                }
                animationCounter++;
            }
        });  
    }

    private void endGame() {
        canvas.removeAll();
        cutSceneBG = new Image(0, 0, "gameover.jpg");
        canvas.add(cutSceneBG);
        cutSceneShown = true;
        gameOver = true;
    }

    /**
     * Create four enemy camps in valid spots on the maze, each with four enemies.
     */
    public void createEnemyCamps(){
        HashSet<ArrayList<Integer>> possibleEnemyCamp = new HashSet<>();
        possibleEnemyCamp = MazeGenerator.enemyCampLocation();
        for (int i = 0; i < 4; i++){

            int randInd = rand.nextInt(possibleEnemyCamp.size()-1);
            ArrayList<Integer> pos = (ArrayList<Integer>) possibleEnemyCamp.toArray()[randInd];

            possibleEnemyCamp.remove(pos);
            
            int x = pos.get(0)*40;
            int y = pos.get(1)*40;
            camps[i] = new EnemyCamp(canvas, maze, minimap.getGraphics(), player);
            camps[i].populateEnemies(x, y, nonCollidingElements, enemyGroup);
        }
    }

    /**
     * Move the background and maze or move the Player directly so the Player's position moves SPEED pixels.
     * @param side The direction to move
     */
    public void scroll(Key key){
        Side side;

        if (key == Key.UP_ARROW){
            side = Side.UP;
        } else if(key == Key.DOWN_ARROW){
            side = Side.DOWN;
        } else if (key == Key.RIGHT_ARROW){
            side = Side.RIGHT;
        } else if (key == Key.LEFT_ARROW){
            side = Side.LEFT;
        } else {
            return;
        }

        if (player.atDestination(side)) {
            levelWon();
        }

        Point scrollVector = side.getDirectionVector();

        for (double i = 0; i < Player.SPEED; i++) {
            if (!player.collision(side)){
                double newX =  -scrollVector.getX() + maze.getPosition().getX();
                double newY = -scrollVector.getY() + maze.getPosition().getY();
                double newPlayerX = player.getPosition().add(side.getDirectionVector()).getX();
                double newPlayerY = player.getPosition().add(side.getDirectionVector()).getY();

                //if moving the Player will keep it within the center of the screen, more the Player. Else, if moving the maze is valid, move the maze. Otherwise, move the Player.
                if ((newPlayerX > 550 && side == Side.LEFT) || (newPlayerX < 450 && side == Side.RIGHT) || (newPlayerY > 550 && side == Side.UP) || (newPlayerY < 450 && side == Side.DOWN)) {
                    player.move(side);
                } else if (newX >= CANVAS_WIDTH - maze.getWidth() && newY >= CANVAS_HEIGHT - maze.getHeight() && newX <= 0 && newY <= 0){
                    maze.setPosition(newX, newY);
                    groundBackGround.setPosition(newX,newY);
                    nonCollidingElements.setPosition(newX,newY);
                    destinationGroup.setPosition(newX,newY);
                    enemyGroup.setPosition(newX, newY);
                } else {
                    player.move(side);
                }
            }
        }

        minimap.setPlayerPosition(player.getGraphics().getCenter().getX() + Math.abs(maze.getPosition().getX()), player.getGraphics().getCenter().getY() + Math.abs(maze.getPosition().getY()));
        player.changeImage(side);
    }

    private void levelWon() {
        cutSceneShown = true;
        canvas.removeAll();
        enemyGroup.removeAll();
        nonCollidingElements.removeAll();
        destinationGroup.removeAll();
        maze.removeAll();
        level++;
        resetGame();
    }
}
