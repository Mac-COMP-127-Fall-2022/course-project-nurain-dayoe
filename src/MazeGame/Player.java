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
        System.out.println(graphic.getHeight());
        System.out.println(graphic.getWidth());
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
        Point right = new Point(newPosition.getX() + graphic.getWidth() / 2, newPosition.getY());
        Point left = new Point(newPosition.getX() - graphic.getWidth() / 2, newPosition.getY());
        Point top = new Point(newPosition.getX(), newPosition.getY() - graphic.getHeight() / 2);
        Point bottom = new Point(newPosition.getX(), newPosition.getY() + graphic.getHeight() / 2);
        return maze.getElementAt(newPosition) != null && maze.getElementAt(right) != null && maze.getElementAt(left) != null && maze.getElementAt(top) != null && maze.getElementAt(bottom) != null;
    }
}
