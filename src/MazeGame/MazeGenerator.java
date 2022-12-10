package MazeGame;

import edu.macalester.graphics.*;
import java.util.*;

import MazeGame.MazeGame.Side;

/**
 * A non-instantiable class to generate a random 100 unit by 100 unit maze.
 */
public class MazeGenerator {
    public static final int MAZE_SIZE = 100;

    private static boolean[][] roadMatrix = new boolean[MAZE_SIZE][MAZE_SIZE];
    private static ArrayList<Point> referencePoints = new ArrayList<Point>();
    private static boolean firstIteration = true;
    private static Random rand = new Random();
    private static Point beginningPoint;
    private static Point destinationPoint;

    //constructor is intentionally empty and private
    private MazeGenerator() {

    }

    /**
    * Create a randomized 100 by 100 maze. This method must be called for the game to function.
    */
    public static void generateMaze(){
        roadMatrix = new boolean[MAZE_SIZE][MAZE_SIZE];
        referencePoints = new ArrayList<Point>();
        firstIteration = true;
        beginningPoint = new Point(rand.nextInt(1, 11), rand.nextInt(1, 11));
        destinationPoint = new Point(rand.nextInt(90, 100),rand.nextInt(90, 100));
        createPath(beginningPoint, destinationPoint);
        createPath(new Point (89, 12), new Point(2, 95));
        roadMatrix[(int)destinationPoint.getX()] [ (int)destinationPoint.getY()] = true;
        Block.buildMaze(roadMatrix);
        
    }

    /**
    * Return a GraphicsGroup containing the block objects of the maze.
    */
    public static GraphicsGroup getMaze() {
        
        GraphicsGroup group = new GraphicsGroup();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++){
                if (!roadMatrix[x][y]) {
                    group.add(Block.getImage(x, y));
                }
            }
        }
        return group;
    }

    /**
    * Check if the inputted x and y coordinates mark the top-left corner of a 3 by 3 square of empty space on the map.
    */
    public static boolean checkForSpace(int x, int y){
        if (x <= 97 && y <= 97){
            if (roadMatrix[x][y] && roadMatrix[x+1][y] && roadMatrix[x+2][y]){
                if (roadMatrix[x][y+1] && roadMatrix[x+1][y+1] && roadMatrix[x+2][y+1]){
                    if (roadMatrix[x][y+2] && roadMatrix[x+1][y+2] && roadMatrix[x+2][y+2]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static HashSet<ArrayList<Integer>> enemyCampLocation(){
        HashSet<ArrayList<Integer>> domain = new HashSet<>();

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++){
                if (checkForSpace(x, y)) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(x);
                    temp.add(y);
                    domain.add(temp);
                    
                }
            }
        }
        
        return domain;
    }

    public static boolean[][] getRoadMatrix() {
        return roadMatrix.clone();
    }

    public static Point getBeginningPoint() {
        return beginningPoint;
    }

    public static Point getEndingPoint() {
        return destinationPoint;
    }
    
    /**
    * Create a path with false paths branching off it between the given points.
    */
    private static void createPath(Point initial, Point last) {
        firstIteration = true;
        for (int x = 1; x < 25; x += 1) {
            Point point1, point2;
            if (firstIteration) {
                point1 = initial;
                point2 = last;
            } else {
                if (!referencePoints.isEmpty()) { //when this method is first run, make the inital point the reference
                    int randomIndex = rand.nextInt(referencePoints.size());
                    point1 = referencePoints.get(randomIndex);
                    referencePoints.remove(randomIndex);
                } else { //otherwise, choose a random reference point
                    point1 = new Point(rand.nextInt(MAZE_SIZE), rand.nextInt(MAZE_SIZE));
                } //make the second point random as well
                point2 = new Point(rand.nextInt(MAZE_SIZE), rand.nextInt(MAZE_SIZE));
            }

            while ((point1.getX() != point2.getX() || point1.getY() != point2.getY())) { //as long as the points are not the same, build the road between them
                point1 = movePointer(point1, point2);
                if (rand.nextFloat() > 0.01) { //In most cases, make point 1 a reference point
                    referencePoints.add(point1);
                }
            }
        }
    }

    /**
    * An internal methods to generate the directions the path should travel.
    */
    private static ArrayList<Side> generateWeights(Point p1, Point p2) {
        ArrayList<Side> weights = new ArrayList<MazeGame.Side>(4);
        if (p2.getX() > p1.getX()) {
            weights.add(Side.RIGHT);
        } else if (p2.getX() < p1.getX()){
            weights.add(Side.LEFT);
        }

        if (p2.getY() > p1.getY()) {
            weights.add(Side.UP);
        } else if (p2.getY() < p1.getY()){
            weights.add(Side.DOWN);
        }

        return weights;
    }

    /**
    * An internal method to move the maze paths along by one unit.
    */
    private static Point movePointer(Point point1, Point point2) {
        ArrayList<Side> weights = generateWeights(point1, point2);
        int[] currentPoint = {(int) point1.getX(), (int) point1.getY()};
        if (firstIteration) {
            firstIteration = false;
        }
        if (!roadMatrix[currentPoint[0]][currentPoint[1]]) {
            roadMatrix[currentPoint[0]][currentPoint[1]] = true;
        }
        Side moveDirection = weights.get(rand.nextInt(weights.size()));

        if (rand.nextFloat() < 0.2) { //allow a 20% chance that the direction is randomly picked, allowing for the option of a "wrong" direction
            moveDirection = Side.values()[rand.nextInt(4)];
        }

        switch (moveDirection) { //move in the randomly generated direction
            case RIGHT:
                if (currentPoint[0] < MAZE_SIZE - 1) {
                    currentPoint[0]++;
                }
                    break;
            case UP:
                if (currentPoint[1] < MAZE_SIZE - 1) {
                    currentPoint[1]++;
                }
                break;
            case LEFT:
                if (currentPoint[0] > 0) {
                    currentPoint[0]--;
                }
                break;
            case DOWN:
            if (currentPoint[1] > 0) {
                currentPoint[1]--;
            }
                break;
            default:
                break;
        }

        return new Point(currentPoint[0], currentPoint[1]);
    }
}


