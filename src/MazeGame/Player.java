package MazeGame;
import edu.macalester.graphics.*;

public class Player {
    private Point position;
    private PlayerImage animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
    private PlayerImage animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
    private PlayerImage animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
    private PlayerImage animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");
    private CanvasWindow canvas;
    private GraphicsGroup maze;
    private Image graphic = new Image(animMapFront.next());
    
    public Player(CanvasWindow canvas, GraphicsGroup maze){
        this.canvas = canvas;
        this.maze = maze;
        canvas.add(graphic);
        graphic.setPosition(100,100);
        graphic.setScale(0.5);
        position = graphic.getPosition();
        canvas.add(graphic);
    }

    public Image getGraphics() {
        return graphic;
    }

    public void move(Point moveVector) {
        Point newPosition = position.add(moveVector);
        if (!collision(newPosition)) {
            position = newPosition;
            graphic.setPosition(newPosition);
        }
        if (moveVector.getX() > 0) {
            graphic.setImagePath(animMapRight.next());
        } else if (moveVector.getX() < 0) {
            graphic.setImagePath(animMapLeft.next());
        } else if (moveVector.getY() > 0) {
            graphic.setImagePath(animMapFront.next());
        } else if (moveVector.getY() < 0) {
            graphic.setImagePath(animMapBack.next());
        } else {
            return;
        }
    }

    private boolean collision(Point newPosition) {
        //TODO Figure out logic
        Point right = new Point(newPosition.getX() + graphic.getWidth(), newPosition.getY() + graphic.getHeight() / 4);
        Point left = new Point(newPosition.getX(), newPosition.getY() + graphic.getHeight() / 4);
        Point top = new Point(newPosition.getX() + graphic.getWidth() / 4, newPosition.getY());
        Point bottom = new Point(newPosition.getX() + graphic.getWidth() / 4, newPosition.getY() + graphic.getHeight());
        System.out.println(newPosition);
        return maze.getElementAt(newPosition) != null || 
               maze.getElementAt(right) != null || 
               maze.getElementAt(left) != null || 
               maze.getElementAt(top) != null || 
               maze.getElementAt(bottom) != null ||
               right.getX() > MazeGame.CANVAS_WIDTH ||
               left.getX() < 0 ||
               top.getY() < 0 ||
               bottom.getY() > MazeGame.CANVAS_HEIGHT;
    }
}
