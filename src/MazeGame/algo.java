package MazeGame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;
import java.util.*;

import MazeGame.MazeGame.Side;

public class algo {
    public int[][] matrix = new int[100][100];//, solutionMatrix = new int[100][100];
    private ArrayList<Point> referencePoints = new ArrayList<Point>();
    private ArrayList<Side> sides = new ArrayList<>(List.of(Side.RIGHT, Side.LEFT, Side.BOTTOM, Side.TOP));
    private boolean firstIteration = true;
    private Random rand = new Random();
    public int counter = 0;

    public algo(){
        createPath(new Point(0,0), new Point (99, 99));
        createPath(new Point (89, 12), new Point(2, 95));
        
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
                    point1 = new Point(rand.nextInt(100), rand.nextInt(100));
                }
                point2 = new Point(rand.nextInt(100), rand.nextInt(100));
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
            weights.add(Side.TOP);
        } else if (p2.getY() < p1.getY()){
            weights.add(Side.BOTTOM);
        }

        return weights;
    }

    private Point movePointer(Point point1, Point point2, int layerLabel) {
        ArrayList<Side> weights = generateWeights(point1, point2);
        int[] currentPoint = {(int) point1.getX(), (int) point1.getY()};
        if (firstIteration) {
            //solutionMatrix[currentPoint[0]][currentPoint[1]] = 1;
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
                if (currentPoint[0] < 99) {
                    currentPoint[0]++;
                }
                    break;
            case TOP:
                if (currentPoint[1] < 99) {
                    currentPoint[1]++;
                }
                break;
            case LEFT:
                if (currentPoint[0] > 0) {
                    currentPoint[0]--;
                }
                break;
            case BOTTOM:
            if (currentPoint[1] > 0) {
                currentPoint[1]--;
            }
                break;
            default:
                break;
        }

        return new Point(currentPoint[0], currentPoint[1]);
    }
    
    public static void main(String[] args) {
        algo temp = new algo();
        System.out.println("Running");
        CanvasWindow canvas = new CanvasWindow("Test", 1000,1000);
        GraphicsGroup group = new GraphicsGroup();
        for (int x = 0; x < 100; x++) {
            for(int y = 0; y < 100; y++){
                if (temp.matrix[x][y] == 0) {
                    Image image = new Image("grass.jpg");
                    group.add(image);
                    image.setPosition(x * 40, y * 40);
                } else if (temp.matrix[x][y] == 1) {
                    Image image = new Image("respack/12.jpg");
                    group.add(image);
                    image.setPosition(x * 40, y * 40);
                } else {
                    Image image = new Image("respack/11.jpg");
                    group.add(image);
                    image.setPosition(x * 40, y * 40);
                }
            }
            group.setScale(0.2);
            canvas.add(group);
            group.setCenter(canvas.getCenter());
            System.out.println("");
        }
    }
}


