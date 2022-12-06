package MazeGame;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.util.Map;
import java.util.HashMap;

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
    private Hearts hearts;
    private Player zelda;
    private Enemy enemy;
    
    private Image startImageButton;
    private Image cutSceneBG;
    private Image groundBackGround; 

    public static int level; 
    private boolean cutScene1shown = true;

    /**
     * Create a new MazeGame instance starting at level 1. 
     */
    public MazeGame() {
        canvas = new CanvasWindow("Breath of the Maze", CANVAS_WIDTH, CANVAS_HEIGHT);
        level = 0;

        canvas.onClick((i)->{
            if (cutScene1shown){
                canvas.remove(cutSceneBG);
                canvas.remove(startImageButton);
                cutScene1shown = false;
            }
        });
        resetGame();

        canvas.onKeyDown((key)->{
            if(!cutScene1shown) {
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
        MazeGenerator.generateMaze();
        maze = MazeGenerator.getMaze();
        
        groundBackGround = new Image("ground.jpg");
        canvas.add(groundBackGround);
        
        canvas.add(maze);
        
        maze.setScale(0.25, 0.25);
        maze.setCenter(canvas.getCenter());
        canvas.screenShot("res/mazeminimap.jpg");
        maze.setScale(1,1);

        canvas.pause(1000);

        //Start the cutscene by showing the image and button
        cutSceneBG = new Image(0,0,"cutscene1.jpg");
        startImageButton = new Image("start.jpg");
        startImageButton.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/2 + CANVAS_HEIGHT/4);

        minimap = new Minimap(220,canvas);
        minimap.addToCanvas(canvas);
        minimap.setTargetPosition(MazeGenerator.getEndingPoint().getX() * 40 + 20, MazeGenerator.getEndingPoint().getY() * 40 + 20);
        
        this.zelda = new Player(canvas, maze, minimap, MazeGenerator.getBeginningPoint());

        maze.setPosition(0, 0);
        hearts = new Hearts(zelda.getHealth(), canvas);
        hearts.addToCanvas(canvas);

        canvas.add(cutSceneBG);
        canvas.add(startImageButton);

        EnemyCamp enemyCamp = new EnemyCamp(4);
        Enemy enemy = new Enemy(canvas, maze, enemyCamp, zelda);
        
        canvas.animate((i)->{
            if (!cutScene1shown){
                enemy.moveTowardPlayer();
            }
        });
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
        Rectangle temp = new Rectangle(0,0,40,40);
        temp.setPosition(MazeGenerator.getEndingPoint().getX()*40-3000,(MazeGenerator.getEndingPoint().getY()*40)-3000);
        canvas.add(temp);
        double deltaX = Math.abs(zelda.getGraphics().getCenter().getX()-((MazeGenerator.getEndingPoint().getX()*40)-3000+20));
        double deltaY = Math.abs(zelda.getGraphics().getCenter().getY()-((MazeGenerator.getEndingPoint().getY()*40)-3000+20));
        if (deltaX<=10 && deltaY<=10){
            canvas.removeAll();
            resetGame();
            System.out.println( "Level passed ");
        }
    }

    /**
     * Move the background and maze or move the Player directly.
     * @param side The direction to move
     */
    public void scroll(Side side){
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
                } else {
                    zelda.move(side);
                }
            }
        }

        minimap.setPlayerPosition(zelda.getGraphics().getCenter().getX() + Math.abs(maze.getPosition().getX()), zelda.getGraphics().getCenter().getY() + Math.abs(maze.getPosition().getY()));
        zelda.changeImage(side);
    }
}
