package MazeGame;

import java.util.*;
import edu.macalester.graphics.Image;

public class Block {
    public enum Environments{FERN, SMALL_ROCK, GRASS, BIG_ROCK, TREE, HOUSE1, HOUSE2};

    public static final String[] FERN = {"resPack/11.jpg"};
    public static final String[] SMALL_ROCK = {"resPack/12.jpg"};
    public static final String[] GRASS = {"resPack/13.jpg"};
    public static final String[] BIG_ROCK = {"resPack/141.jpg", "resPack/142.jpg", "resPack/143.jpg", "resPack/144.jpg"};
    public static final String[] TREE = {"resPack/151.jpg", "resPack/152.jpg", "resPack/153.jpg", "resPack/154.jpg"};
    public static final String[] HOUSE1 = {"resPack/161.jpg", "resPack/162.jpg", "resPack/163.jpg", "resPack/164.jpg"};
    public static final String[] HOUSE2 = {"resPack/171.jpg", "resPack/172.jpg", "resPack/173.jpg", "resPack/174.jpg"};

    public static final HashMap<Environments, String[]> allEnvironments = new HashMap<>(Map.of(Environments.FERN, FERN, Environments.SMALL_ROCK, SMALL_ROCK, Environments.GRASS, GRASS, Environments.BIG_ROCK, BIG_ROCK, Environments.TREE, TREE, Environments.HOUSE1, HOUSE1, Environments.HOUSE2, HOUSE2));
    public static final HashMap<Environments, String[]> oneBlockEnvironments = new HashMap<>(Map.of(Environments.FERN, FERN, Environments.SMALL_ROCK, SMALL_ROCK, Environments.GRASS, GRASS));
    public static final HashMap<Environments, String[]> fourBlockEnvironments = new HashMap<>(Map.of(Environments.BIG_ROCK, BIG_ROCK, Environments.TREE, TREE, Environments.HOUSE1, HOUSE1, Environments.HOUSE2, HOUSE2));

    private static final Random rand = new Random();
    private static int[][] matrix;
    private static Block[][] blocks = new Block[algo.MAZE_SIZE][algo.MAZE_SIZE];

    private boolean road, environmentSet = false;
    private Image graphic;
    private Environments environment;
    private int x, y, environmentSubNumber;

    public static void setMatrix(int[][] matrix) {
        Block.matrix = matrix;

        for (int x = 0; x < algo.MAZE_SIZE; x++) {
            for (int y = 0; y < algo.MAZE_SIZE; y++) {
                if (matrix[x][y] == 0) {
                    blocks[x][y] = new Block(false, x, y);
                } else {
                    blocks[x][y] = new Block(true, x, y);
                }
            }
        }

        for (int x = 0; x < algo.MAZE_SIZE; x++) {
            for (int y = 0; y < algo.MAZE_SIZE; y++) {
                blocks[x][y].setEnvironment();
            }
        }
    }

    private Block (boolean road, int x, int y) {
        if (Block.matrix == null) {
            throw new IllegalStateException();
        }
        if (x < 0 || y < 0 || x > algo.MAZE_SIZE || y > algo.MAZE_SIZE) {
            throw new IllegalArgumentException();
        }
        if (road) {
            graphic = null;
            environmentSet = true;
        }
        this.x = x;
        this.y = y;
        this.road = road;
    }

    public boolean isRoad() {
        return road;
    }

    public boolean isSet() {
        return environmentSet;
    }

    public void setEnvironment(Environments environment, int environmentSubNumber) {
        if (!environmentSet && !road) {
            this.environment = environment;
            this.environmentSubNumber = environmentSubNumber;
            graphic = new Image(allEnvironments.get(environment)[environmentSubNumber]);
            graphic.setPosition(x * 40, y * 40);
            environmentSet = true;
        }
    }

    public void setEnvironment() {
        if (!environmentSet && !road) {
            if (isSpaceForLargeBlock()) {
                Environments randomEnvironment = (Environments) allEnvironments.keySet().toArray()[Block.rand.nextInt(allEnvironments.size())];
                setEnvironment(randomEnvironment, 0);
                if (fourBlockEnvironments.containsKey(randomEnvironment)) {
                    blocks[x][y+1].setEnvironment(randomEnvironment, 1);
                    blocks[x+1][y].setEnvironment(randomEnvironment, 2);
                    blocks[x+1][y+1].setEnvironment(randomEnvironment, 3);
                }
            } else {
                Environments randomEnvironment = (Environments) oneBlockEnvironments.keySet().toArray()[Block.rand.nextInt(oneBlockEnvironments.size())];
                setEnvironment(randomEnvironment, 0);
            }
        }
    }

    private boolean isSpaceForLargeBlock() {
        if (x < algo.MAZE_SIZE - 2 && y < algo.MAZE_SIZE - 2) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (blocks[i][j].isRoad() || blocks[i][j].isSet()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Image getImage() {
        return graphic;
    }

    public static Image getImage (int x, int y) {
        return blocks[x][y].getImage();
    }
}
