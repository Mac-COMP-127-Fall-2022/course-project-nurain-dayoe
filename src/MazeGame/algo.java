package MazeGame;

import edu.macalester.graphics.*;
import java.util.*;

import MazeGame.MazeGame.Side;

public class algo {
    public static final int MAZE_SIZE = 100;
    private int[][] matrix = new int[MAZE_SIZE][MAZE_SIZE];
    private ArrayList<Point> referencePoints = new ArrayList<Point>();
    private ArrayList<Side> sides = new ArrayList<>(List.of(Side.RIGHT, Side.LEFT, Side.DOWN, Side.UP));
    private boolean firstIteration = true;
    private Random rand = new Random();
    public int counter = 0;
    private Point beginningPoint;
    private Point destinationPoint;

    public algo(){
        beginningPoint = new Point(rand.nextInt(1, 11), rand.nextInt(1, 11));
        System.out.println(beginningPoint);
        destinationPoint = new Point(rand.nextInt(90, 100),rand.nextInt(90, 100));
        createPath(beginningPoint, destinationPoint);
        createPath(new Point (89, 12), new Point(2, 95));
        Block.buildMaze(matrix);
    }

    public GraphicsGroup getMaze() {
        GraphicsGroup group = new GraphicsGroup();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++){
                if (matrix[x][y] == 0) {
                    group.add(Block.getImage(x, y));
                }
            }
        }
        return group;
    }

    public Point getBeginningPoint() {
        return beginningPoint;
    }

    public Point getEndingPoint() {
        return destinationPoint;
    }

    private void createPath(Point initial, Point last) {
        firstIteration = true;
        for (int x = 1; x < 25; x += 1) {
            Point point1, point2;
            if (firstIteration) {
                point1 = initial;
                point2 = last;
            } else {
                if (!referencePoints.isEmpty()) {
                    int randomIndex = rand.nextInt(referencePoints.size());
                    point1 = referencePoints.get(randomIndex);
                    referencePoints.remove(randomIndex);
                } else {
                    point1 = new Point(rand.nextInt(MAZE_SIZE), rand.nextInt(MAZE_SIZE));
                }
                point2 = new Point(rand.nextInt(MAZE_SIZE), rand.nextInt(MAZE_SIZE));
            }

            while ((point1.getX() != point2.getX() || point1.getY() != point2.getY())) {
                counter++;
                point1 = movePointer(point1, point2, x);
                if (rand.nextFloat() > 0.01) {
                    referencePoints.add(point1);
                }
            }
        }
    }

    private ArrayList<Side> generateWeights(Point p1, Point p2) {
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

    private Point movePointer(Point point1, Point point2, int layerLabel) {
        ArrayList<Side> weights = generateWeights(point1, point2);
        int[] currentPoint = {(int) point1.getX(), (int) point1.getY()};
        if (firstIteration) {
            firstIteration = false;
        }
        if (matrix[currentPoint[0]][currentPoint[1]] == 0) {
            matrix[currentPoint[0]][currentPoint[1]] = 1;
        }
        Side moveDirection = weights.get(rand.nextInt(weights.size()));

        if (rand.nextFloat() < 0.2) { //allow a 20% chance that the direction is randomly picked, allowing for the option of a "wrong" direction
            moveDirection = sides.get(rand.nextInt(4));
        }

        switch (moveDirection) {
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


