package MazeGame;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.util.Random;
import java.util.List;
import java.io.File;

//Source https://go.codetogether.com/#/14543ac2-a2bf-48c2-8fb7-66bd061bed03/dOc02knOTzti1VbaitFjLE
//Source: https://stackoverflow.com/questions/44654291/is-it-good-practice-to-use-ordinal-of-enum
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
    //public static final HashMap<Side, Point> directionVectors = new HashMap<Side, Point>(Map.of(Side.RIGHT, new Point(1,0), Side.LEFT, new Point(-1, 0), Side.UP, new Point(0, -1), Side.DOWN, new Point(0, 1))); //HashMap to relate Point objects to correspoinding directions

    private CanvasWindow canvas;
    private GraphicsGroup maze = new GraphicsGroup();
    private Minimap minimap;
    private Player zelda;
    private GraphicsGroup enemyGroup;
    private GraphicsGroup nonCollidingElements;
    private GraphicsGroup destinationGroup;
    private Image cutSceneBG;
    private Image groundBackGround; 
    private Random rand = new Random();
    public static int level; 
    private boolean cutSceneShown = true;
    public EnemyCamp[] camps = new EnemyCamp[4];
    private Image destinationDoor;
    private int animationCounter = 0;
    private Thread screenshotThread = new Thread();

    /**
     * Create a new MazeGame instance starting at level 1. 
     */
    public MazeGame() {
        canvas = new CanvasWindow("Breath of the Maze", CANVAS_WIDTH, CANVAS_HEIGHT);
        level = 0;
        
        resetGame();

        canvas.onClick((i)->{ //TODO: Fix glitch during end game
            if (cutSceneShown){
                canvas.remove(cutSceneBG);
                cutSceneShown = false;
            }
        });
        
        canvas.onKeyDown((key)->{
            if(!cutSceneShown) { 
                move(key.getKey());
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
        screenshotThread = new Thread(new Runnable() {
            @Override
            public void run() {
                canvas.screenShot("res/mazeminimap.jpg");
            }
           });
        File mapImageFile = new File("res/mazeminimap.jpg");
        mapImageFile.delete();
        MazeGenerator.generateMaze();
        maze = MazeGenerator.getMaze();
        enemyGroup = new GraphicsGroup(0,0);
        groundBackGround = new Image("ground.jpg");
        canvas.add(groundBackGround);
        destinationGroup = new GraphicsGroup(0,0);
        canvas.add(maze);
        
        maze.setScale(0.25, 0.25);
        maze.setCenter(canvas.getCenter());
        canvas.draw();

        canvas.pause(2000);
        screenshotThread.start();
        canvas.pause(1000);
        maze.setScale(1,1);

        

        //Start the cutscene by showing the image and button
        if (level == 0) {
            cutSceneBG = new Image(0,0,"cutscene1.jpg");
        } else {
            cutSceneBG = new Image(0,0,"nextLevel.jpg");
        }

        nonCollidingElements = new GraphicsGroup(0, 0);
        minimap = new Minimap(canvas);
        this.zelda = new Player(canvas, maze, destinationGroup, minimap, MazeGenerator.getBeginningPoint(),enemyGroup);

        createEnemyCamps();
            
        minimap.setTargetPosition(MazeGenerator.getEndingPoint().getX() * 40 + 20, MazeGenerator.getEndingPoint().getY() * 40 + 20);
        
        destinationDoor = new Image("resPack/destinationdoor1.jpg");//TODO: change it to the real door/destination image
        destinationDoor.setPosition(MazeGenerator.getEndingPoint().getX()*40,(MazeGenerator.getEndingPoint().getY()*40));
        destinationGroup.add(destinationDoor);
        
        maze.setPosition(0, 0);
        Hearts.generateHearts(5, canvas);

        canvas.add(destinationGroup);
        
        canvas.add(Hearts.getGraphics());
        
        canvas.add(nonCollidingElements);
        canvas.add(enemyGroup);
        canvas.add(destinationGroup);
        canvas.add(zelda.getGraphics());
        canvas.add(minimap.getGraphics());
        canvas.add(cutSceneBG);

        canvas.animate((i)->{
            
            if (!cutSceneShown) {
                if (zelda.isDead()) {
                    cutSceneShown = true;
                    endGame();
                }
                if (animationCounter % 10 == 0){
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
        //TODO: Change cutSceneBG to "Game Over"
        canvas.add(cutSceneBG);
    }


    public void createEnemyCamps(){
        int randInd = rand.nextInt(MazeGenerator.enemyCampLocation().size()-1);
        List<Integer> pos = MazeGenerator.enemyCampLocation().get(randInd);
        int x = pos.get(0)*40;
        int y = pos.get(1)*40;
        int previousX = x;
        int previousY = y;
        for (int i = 0; i < 4; i++){
            camps[i] = new EnemyCamp(canvas, maze, minimap.getGraphics(), zelda);
            camps[i].addToGraphicsGroup(nonCollidingElements, x, y, enemyGroup);
            
            while((Math.abs(x-previousX) < 600)&&(Math.abs(y-previousY)<600)){
                randInd = rand.nextInt(MazeGenerator.enemyCampLocation().size()-1);
                pos = MazeGenerator.enemyCampLocation().get(randInd);
                x = pos.get(0)*40;
                y = pos.get(1)*40;

            }
            previousX = x;
            previousY = y;
        }  
    }
    /**
     * Move the Player in the direction of the key pressed by SPEED pixels.
     * @param key The key pressed
     */
    public void move(Key key) {
        if (key == Key.UP_ARROW){
            scroll(Side.UP);
        } else if(key == Key.DOWN_ARROW){
            scroll(Side.DOWN);
        } else if (key == Key.RIGHT_ARROW){
            scroll(Side.RIGHT);
        } else if (key == Key.LEFT_ARROW){
            scroll(Side.LEFT);
        }

        

        // Rectangle endTarget = new Rectangle(0,0,40,40);
        // endTarget.setPosition(MazeGenerator.getEndingPoint().getX()*40-3000,(MazeGenerator.getEndingPoint().getY()*40)-3000);
        // canvas.add(endTarget);
        // double deltaX = Math.abs(zelda.getGraphics().getCenter().getX()-((MazeGenerator.getEndingPoint().getX()*40)-3000+20));
        // double deltaY = Math.abs(zelda.getGraphics().getCenter().getY()-((MazeGenerator.getEndingPoint().getY()*40)-3000+20));
        // if (deltaX<=10 && deltaY<=10){
        //     canvas.removeAll();
        //     level++;
        //     resetGame();
        //     System.out.println( "Level passed ");
        // }
    }

    /**
     * Move the background and maze or move the Player directly.
     * @param side The direction to move
     */
    public void scroll(Side side){
        if (zelda.atDestination(side)) {
            cutSceneShown = true;
            canvas.removeAll();
            enemyGroup.removeAll();
            nonCollidingElements.removeAll();
            destinationGroup.removeAll();
            maze.removeAll();
            level++;
            resetGame();
            System.out.println("Win!");
        }
        Point scrollVector = side.getDirectionVector();
        for (double i = 0; i < Player.SPEED; i++) {
            if (!zelda.collision(side)){
                double newX =  -scrollVector.getX() + maze.getPosition().getX();
                double newY = -scrollVector.getY() + maze.getPosition().getY();
                double newPlayerX = zelda.getPosition().add(side.getDirectionVector()).getX();
                double newPlayerY = zelda.getPosition().add(side.getDirectionVector()).getY();

                //if moving the Player will keep it within the center of the screen, more the Player. Else, if moving the maze is valid, move the maze. Otherwise, move the Player.
                if ((newPlayerX > 550 && side == Side.LEFT) || (newPlayerX < 450 && side == Side.RIGHT) || (newPlayerY > 550 && side == Side.UP) || (newPlayerY < 450 && side == Side.DOWN)) {
                    zelda.move(side);
                } else if (newX >= CANVAS_WIDTH - maze.getWidth() && newY >= CANVAS_HEIGHT - maze.getHeight() && newX <= 0 && newY <= 0){
                    maze.setPosition(newX, newY);
                    groundBackGround.setPosition(newX,newY);
                    nonCollidingElements.setPosition(newX,newY);
                    destinationGroup.setPosition(newX,newY);
                    enemyGroup.setPosition(newX, newY);
                } else {
                    zelda.move(side);
                }
            }
        }
        minimap.setPlayerPosition(zelda.getGraphics().getCenter().getX() + Math.abs(maze.getPosition().getX()), zelda.getGraphics().getCenter().getY() + Math.abs(maze.getPosition().getY()));
        zelda.changeImage(side);
    }
}
