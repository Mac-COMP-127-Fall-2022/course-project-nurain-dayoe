package MazeGame;

import edu.macalester.graphics.Point;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

import MazeGame.MazeGame.Side;

public class algo {
    private int[][] Matrix = new int[100][100], solutionMatrix = new int[100][100];
    private ArrayList<Point> referencePoints = new ArrayList<Point>();
    private boolean firstIteration = true;
    private Random rand = new Random();

    public algo(){
        for (int x = 1; x < 50; x += 2) {
            Point point1, point2;
            if (firstIteration) {
                point1 = new Point(7,0);
                point2 = new Point(98, 100);
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

            while (point1.getX() != point2.getX() || point1.getY() != point2.getY()) {
                point1 = movePointer(point1, point2, x);
                if (rand.nextFloat() > 0.1) {
                    referencePoints.add(point1);
                }
            }
        }
        
    }

    private HashMap<Side, Boolean> generateWeights(Point p1, Point p2) {
        HashMap<Side, Boolean> weights = new HashMap<>(Map.of(Side.TOP, false, Side.BOTTOM, false, Side.RIGHT, false, Side.LEFT, false));
        if (p2.getX() > p1.getX()) {
            weights.replace(Side.RIGHT, true);
        } else if (p2.getX() < p1.getX()){
            weights.replace(Side.LEFT, true);
        }

        if (p2.getY() > p2.getY()) {
            weights.replace(Side.TOP, true);
        } else if (p2.getY() < p2.getY()){
            weights.replace(Side.BOTTOM, true);
        }

        return weights;
    }

    private Point movePointer(Point point1, Point point2, int layerLabel) {
        HashMap<Side, Boolean> weights = generateWeights(point1, point2);
        int[] currentPoint = {(int) point1.getX(), (int) point1.getY()};
        if (firstIteration) {
            solutionMatrix[currentPoint[0]][currentPoint[1]] = 1;
            firstIteration = false;
        }
        boolean repeat = true;
        do {
            switch(rand.nextInt(4)) {
                case 0:
                    if (weights.get(Side.TOP)) {
                        currentPoint[1]++;
                        repeat = false;
                    }
                    break;
                case 1:
                    if (weights.get(Side.RIGHT)) {
                        currentPoint[0]++;
                        repeat = false;
                    }
                    break;
                case 2:
                    if (weights.get(Side.BOTTOM)) {
                        currentPoint[1]--;
                        repeat = false;
                    }
                    break;
                case 3:
                    if (weights.get(Side.LEFT)) {
                        currentPoint[0]--;
                        repeat = false;
                    }
                    break;
                default: 
                    break;  
            }
        } while (repeat);

        return new Point(currentPoint[0], currentPoint[1]);
    }
    
    public static void main(String[] args) {
        algo temp = new algo();
        for (int[] row : temp.Matrix) {
            for(int cell : row){
                System.out.print(cell);
            }
            System.out.println("");
        }
    }
}


