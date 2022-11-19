package MazeGame;

import edu.macalester.graphics.*;
import java.awt.Color;

public class Block {
    public static final double BLOCK_SIZE = 46, BORDER_SIZE = 2;
    private Rectangle right, left, top, bottom;
    private static GraphicsGroup maze;
    public static enum Side {RIGHT, LEFT, TOP, BOTTOM}


    public Block(Point centerPoint) {
        if (maze == null) {
            throw new ExceptionInInitializerError("Set Maze before creating new Block objects.");
        }
        right = new Rectangle(centerPoint.add(new Point(BLOCK_SIZE * 0.5, -BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BORDER_SIZE, BLOCK_SIZE + BORDER_SIZE * 2));
        left = new Rectangle(centerPoint.add(new Point(-BLOCK_SIZE * 0.5 - BORDER_SIZE, -BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BORDER_SIZE, BLOCK_SIZE + BORDER_SIZE * 2));
        top = new Rectangle(centerPoint.add(new Point(-BLOCK_SIZE * 0.5 - BORDER_SIZE, -BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BLOCK_SIZE + BORDER_SIZE * 2, BORDER_SIZE));
        bottom = new Rectangle(centerPoint.add(new Point(-BLOCK_SIZE * 0.5, BLOCK_SIZE * 0.5)), new Point(BLOCK_SIZE + BORDER_SIZE * 2, BORDER_SIZE));
        
        right.setStroked(false);
        left.setStroked(false);
        top.setStroked(false);
        bottom.setStroked(false);

        right.setFillColor(Color.BLACK);
        left.setFillColor(Color.BLACK);
        top.setFillColor(Color.BLACK);
        bottom.setFillColor(Color.BLACK);
    }

    public void addBorder(Side side) {
        switch (side) {
            case RIGHT:
                maze.add(right);
                break;
            case LEFT: 
                maze.add(left);
                break;
            case TOP: 
                maze.add(top);
                break;
            case BOTTOM:
                maze.add(bottom);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void removeBorder(Side side) {
        switch (side) {
            case RIGHT:
                maze.remove(right);
                break;
            case LEFT: 
                maze.remove(left);
                break;
            case TOP: 
                maze.remove(top);
                break;
            case BOTTOM:
                maze.remove(bottom);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static void setMaze(GraphicsGroup maze) {
        Block.maze = maze;
    }
}
