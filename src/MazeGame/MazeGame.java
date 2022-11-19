package MazeGame;

import MazeGame.Block.Side;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;


public class MazeGame {
    
    private CanvasWindow canvas ;
    private Player zelda;
    private GraphicsGroup maze = new GraphicsGroup();
    private Block[][] mazeBlocks = new Block[30][30];
    public final static int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 1000;
    public final static double SPEED = 2;

    public MazeGame() { 
        resetGame();
        canvas.onKeyDown((key)->{
            move(key.getKey());
        });
        Block.setMaze(maze);
        canvas.add(maze);

        double centerX = Block.BLOCK_SIZE * 0.5 + Block.BORDER_SIZE, centerY = centerX;
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                mazeBlocks[x][y] = new Block(new Point(centerX, centerY));
                centerX += Block.BLOCK_SIZE + Block.BORDER_SIZE;
            }
            centerX = Block.BLOCK_SIZE * 0.5 + Block.BORDER_SIZE;
            centerY += Block.BLOCK_SIZE + Block.BORDER_SIZE;
        }
    }
    public static void main(String[] args){
        MazeGame game = new MazeGame();
        game.testGrid();
    }
    protected void resetGame(){
        canvas = new CanvasWindow("Breath of the Maze", CANVAS_WIDTH, CANVAS_HEIGHT);
        this.zelda = new Player(canvas, maze);
        maze.setPosition(0,0);
    }

    public void move(Key key) {
        if (key == Key.UP_ARROW){
            scroll(new Point (0,-SPEED));
        }else if(key == Key.DOWN_ARROW){
            scroll(new Point (0, SPEED));
        }else if (key == Key.RIGHT_ARROW){
            scroll(new Point (SPEED,0));
        }else if (key == Key.LEFT_ARROW){
            scroll(new Point (-SPEED,0));
        }
    }

    public void scroll(Point scrollVector){
        if (!zelda.collision(scrollVector)){
            double newX =  -scrollVector.getX() + maze.getPosition().getX();
            double newY = -scrollVector.getY() + maze.getPosition().getY();
            if (newX >= CANVAS_WIDTH - maze.getWidth() && newY >= CANVAS_HEIGHT - maze.getHeight() && newX <= 0 && newY <= 0) {
                maze.setPosition(newX, newY);
            }
            zelda.move(scrollVector);
        }
    }

    private void testGrid() {
        for (Block[] blockRow : mazeBlocks) {
            for (Block block : blockRow) {
                if(block == mazeBlocks[0][0]) {
                block.addBorder(Side.RIGHT);
                block.addBorder(Side.TOP);
                block.addBorder(Side.LEFT);
                block.addBorder(Side.BOTTOM);}
            }
        }
    }
}
