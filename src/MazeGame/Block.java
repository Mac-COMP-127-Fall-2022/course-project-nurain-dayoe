package MazeGame;

import edu.macalester.graphics.*;
import java.awt.Color;

public class Block {
    public static final double BLOCK_SIZE = 15, BORDER_SIZE = 2;
    private Rectangle right, left, top, bottom;
    private CanvasWindow canvas;


    public Block(CanvasWindow canvas, Point centerPoint) {
        this.canvas = canvas;
        right = new Rectangle(centerPoint.add(new Point(BLOCK_SIZE * 0.5, BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BORDER_SIZE, BLOCK_SIZE));
        left = new Rectangle(centerPoint.add(new Point(-BLOCK_SIZE * 0.5 - BORDER_SIZE, -BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BORDER_SIZE, BLOCK_SIZE));
        top = new Rectangle(centerPoint.add(new Point(-BLOCK_SIZE * 0.5 - BORDER_SIZE, -BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BLOCK_SIZE, BORDER_SIZE));
        bottom = new Rectangle(centerPoint.add(new Point(BLOCK_SIZE * 0.5, BLOCK_SIZE * 0.5 - BORDER_SIZE)), new Point(BLOCK_SIZE, BORDER_SIZE));
        
        right.setFillColor(Color.BLACK);
        left.setFillColor(Color.BLACK);
        top.setFillColor(Color.BLACK);
        bottom.setFillColor(Color.BLACK);
    }

    public void addRightBorder() {
        canvas.add(right);
    }

    public void addLeftBorder() {
        canvas.add(left);
    }

    public void addTopBorder() {
        canvas.add(top);
    }

    public void addBottomBorder() {
        canvas.add(bottom);
    }

    public void removeRightBorder() {
        canvas.remove(right);
    }

    public void removeLeftBorder() {
        canvas.remove(left);
    }

    public void removeTopBorder() {
        canvas.remove(top);
    }

    public void removeBottomBorder() {
        canvas.remove(bottom);
    }
}
