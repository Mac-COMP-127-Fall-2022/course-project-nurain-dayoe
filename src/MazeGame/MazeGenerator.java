package MazeGame;

import java.util.Random;

import MazeGame.Block.Side;
import edu.macalester.graphics.*;


public class MazeGenerator {
    private static final int DIM = 30;
    private Block[][] mazeBlocks = new Block[DIM][DIM];
    private static GraphicsGroup maze;
    private static CanvasWindow canvas;
    private Random rand = new Random();

    public MazeGenerator() {
        if (maze == null || canvas == null) {
            throw new ExceptionInInitializerError("Set maze and canvas before generating maze.");
        }
        double centerX = Block.BLOCK_SIZE * 0.5 + Block.BORDER_SIZE, centerY = centerX;
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                mazeBlocks[x][y] = new Block(new Point(centerX, centerY));
                centerX += Block.BLOCK_SIZE + Block.BORDER_SIZE;
            }
            centerX = Block.BLOCK_SIZE * 0.5 + Block.BORDER_SIZE;
            centerY += Block.BLOCK_SIZE + Block.BORDER_SIZE;
        }
        for (int x = 0; x < DIM; x ++) {
            mazeBlocks[0][x].addBorder(Side.LEFT);
            mazeBlocks[DIM - 1][x].addBorder(Side.RIGHT);
            mazeBlocks[x][0].addBorder(Side.TOP);
            mazeBlocks[x][DIM - 1].addBorder(Side.BOTTOM);
        }
        generateMaze(0, DIM - 1, 0, DIM - 1);
    }

    public static void setMazeCanvas(GraphicsGroup maze, CanvasWindow canvas) {
        MazeGenerator.maze = maze;
        MazeGenerator.canvas = canvas;
    }

    private void generateMaze(int minX, int maxX, int minY, int maxY) {
        if (minX == maxX || minY == maxY) {
            return;
        }

        int column = rand.nextInt(minX, maxX);
        for (int i = minY; i <= maxY; i++) {
            mazeBlocks[column][i].addBorder(Side.RIGHT);
        }

        int row = rand.nextInt(minY, maxY);
        for (int i = minX; i <= maxX; i++) {
            mazeBlocks[i][row].addBorder(Side.BOTTOM);
        }

        int skipNumber = rand.nextInt(0,4);
        if (skipNumber != 0) {
            mazeBlocks[column][rand.nextInt(minY, row + 1)].removeBorder(Side.RIGHT);
        }
        if (skipNumber != 1) {
            mazeBlocks[column][rand.nextInt(row + 1, maxY + 1)].removeBorder(Side.RIGHT);
        }
        if (skipNumber != 2) {
            mazeBlocks[rand.nextInt(minX, column + 1)][row].removeBorder(Side.BOTTOM);
        }
        if (skipNumber != 3) {
            mazeBlocks[rand.nextInt(column + 1, maxX + 1)][row].removeBorder(Side.BOTTOM);
        }
        generateMaze(minX, column, minY, row);
        generateMaze(column + 1, maxX, minY, row);
        generateMaze(column + 1, maxX, row + 1, maxY);
        generateMaze(minX, column, row + 1, maxY);
    }
}
