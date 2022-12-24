package MazeGame;

import java.util.*;
import edu.macalester.graphics.Image;

/**
 * A Block on the screen which can be an environment or road. Block cannot be instantiated by outside classes (it only has a private constructor).
 */
public class Block {
    public enum Environments{FERN, SMALL_ROCK, GRASS, BIG_ROCK, TREE, HOUSE1, HOUSE2};

    //Associate the image file locations wih the different environments
    public static final String[] FERN = {"resPack/11.jpg"};
    public static final String[] SMALL_ROCK = {"resPack/12.jpg"};
    public static final String[] GRASS = {"resPack/13.jpg"};
    public static final String[] BIG_ROCK = {"resPack/141.jpg", "resPack/142.jpg", "resPack/143.jpg", "resPack/144.jpg"};
    public static final String[] TREE = {"resPack/151.jpg", "resPack/152.jpg", "resPack/153.jpg", "resPack/154.jpg"};
    public static final String[] HOUSE1 = {"resPack/161.jpg", "resPack/162.jpg", "resPack/163.jpg", "resPack/164.jpg"};
    public static final String[] HOUSE2 = {"resPack/171.jpg", "resPack/172.jpg", "resPack/173.jpg", "resPack/174.jpg"};

    //Create HashMaps relating the Environment objects with their file paths. Environments are brokoen up by their size.
    public static final HashMap<Environments, String[]> allEnvironments = new HashMap<>(Map.of(Environments.FERN, FERN, Environments.SMALL_ROCK, SMALL_ROCK, Environments.GRASS, GRASS, Environments.BIG_ROCK, BIG_ROCK, Environments.TREE, TREE, Environments.HOUSE1, HOUSE1, Environments.HOUSE2, HOUSE2));
    public static final HashMap<Environments, String[]> oneBlockEnvironments = new HashMap<>(Map.of(Environments.FERN, FERN, Environments.SMALL_ROCK, SMALL_ROCK, Environments.GRASS, GRASS));
    public static final HashMap<Environments, String[]> fourBlockEnvironments = new HashMap<>(Map.of(Environments.BIG_ROCK, BIG_ROCK, Environments.TREE, TREE, Environments.HOUSE1, HOUSE1, Environments.HOUSE2, HOUSE2));

    private static final Random rand = new Random();
    private static boolean[][] roadMatrix; //The 2D array of 1s and 0s which represents the maze
    private static Block[][] blocks = new Block[MazeGenerator.MAZE_SIZE][MazeGenerator.MAZE_SIZE]; //The 2D array storing the block objects

    private boolean road, environmentSet = false;
    private Image graphic;
    private int x, y;

    /**
     * A builder method to initalize the matrix with Block objects that are either roads or not roads.
     * @param matrix A 2D array of 1s and 0s representing the maze assumed to be square of size MAZE_SIZE.
     */
    public static void buildMaze(boolean[][] roadMatrix) {
        Block.roadMatrix = roadMatrix;

        for (int x = 0; x < MazeGenerator.MAZE_SIZE; x++) {
            for (int y = 0; y < MazeGenerator.MAZE_SIZE; y++) {
                if (!roadMatrix[x][y]) {
                    blocks[x][y] = new Block(false, x, y);
                } else {
                    blocks[x][y] = new Block(true, x, y);
                }
            }
        }

        for (int x = 0; x < MazeGenerator.MAZE_SIZE; x++) {
            for (int y = 0; y < MazeGenerator.MAZE_SIZE; y++) {
                blocks[x][y].setEnvironment();
            }
        }
    }

    public static Image getImage (int x, int y) {
        return blocks[x][y].getImage();
    }

    public Image getImage() {
        return graphic;
    }


    
    public boolean isRoad() {
        return road;
    }

    public boolean isRoad(int x, int y) {
        return blocks[x][y].isRoad();
    }
    
    public boolean isSet() {
        return environmentSet;
    }
    
    /**
     * Create a block at the specified location in block coordinates.
     * @param road Whether this block is a road or not
     */
    private Block (boolean road, int x, int y) {
        if (Block.roadMatrix == null) { //in the theoretically impossible case this constructor is called without first calling buildMaze(), an error is thrown.
            throw new IllegalStateException();
        }
        if (x < 0 || y < 0 || x > MazeGenerator.MAZE_SIZE || y > MazeGenerator.MAZE_SIZE) { //x and y must be within
            throw new IllegalArgumentException();
        }
        if (road) { //if the block is a road, its graphic remains null and its environment does not need to be set
            graphic = null;
            environmentSet = true;
        }
        this.x = x;
        this.y = y;
        this.road = road;
    }

    /**
     *  Set this Block's environment to the given environment and sub-number.
     */
    private void setEnvironment(Environments environment, int environmentSubNumber) {
        if (!environmentSet && !road) {
            graphic = new Image(allEnvironments.get(environment)[environmentSubNumber]);
            graphic.setPosition(x * 40, y * 40);
            environmentSet = true;
        }
    }

    /**
     * Set this Block's environment to a random yet appropriate environment. If a 4 by 4 environment is choosen, set the adjacent Block environments as well.
     */
    private void setEnvironment() {
        if (!environmentSet && !road) {
            if (isSpaceForLargeBlock()) {
                Environments randomEnvironment = (Environments) allEnvironments.keySet().toArray()[Block.rand.nextInt(allEnvironments.size())]; //Choose a random environment out of allEnvironments
                setEnvironment(randomEnvironment, 0);
                if (fourBlockEnvironments.containsKey(randomEnvironment)) { //Set the adjacent environments, if appropriate
                    blocks[x][y+1].setEnvironment(randomEnvironment, 1);
                    blocks[x+1][y].setEnvironment(randomEnvironment, 2);
                    blocks[x+1][y+1].setEnvironment(randomEnvironment, 3);
                }
            } else { //if there is not space for a 4 by 4 environment, set this Block to a 1 by 1
                Environments randomEnvironment = (Environments) oneBlockEnvironments.keySet().toArray()[Block.rand.nextInt(oneBlockEnvironments.size())];
                setEnvironment(randomEnvironment, 0);
            }
        }
    }

    /**
     * Check if there is space for a 4 by 4 environment with this Block as the top left corner.
     * @return true if the Blocks to the right, bottom, and bottom right are not set and not roads.
     */
    private boolean isSpaceForLargeBlock() {
        if (x < MazeGenerator.MAZE_SIZE - 2 && y < MazeGenerator.MAZE_SIZE - 2) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (blocks[i][j].isRoad() || blocks[i][j].isSet()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false; //if adding a 4 by 4 would result in going off the screen, return false
    }

    
}
