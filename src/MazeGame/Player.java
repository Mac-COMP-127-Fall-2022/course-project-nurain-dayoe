package MazeGame;
import edu.macalester.graphics.*;

public class Player {
    private Point position;
    private PlayerImage animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
    private PlayerImage animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
    private PlayerImage animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
    private PlayerImage animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");
    private GraphicsGroup maze;
    private Image graphic = new Image(animMapFront.next());

    // private Line line1 = new Line(0, 0, 0, 0);
    // private Line line2 = new Line(0, 0, 0, 0);
    
    public Player(CanvasWindow canvas, GraphicsGroup maze){
        this.maze = maze;
        canvas.add(graphic);

        // canvas.add(line1);
        // canvas.add(line2);

        graphic.setPosition(-5, -5); //TODO: Set inital position
        graphic.setScale(0.5);
        position = graphic.getPosition();
        canvas.add(graphic);
    }

    public Image getGraphics() {
        return graphic;
    }

    public void move(Point moveVector) {
        Point newPosition = position.add(moveVector);
        if (!collision(moveVector)) {
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

    public boolean collision(Point moveVector) {
        boolean collision = false;
        Point newPosition = position, right, left, top, bottom;
        for (double i = 0; i < MazeGame.SPEED; i++) {
            newPosition = newPosition.add(moveVector.scale(1/MazeGame.SPEED));
            right = new Point(newPosition.getX() + graphic.getWidth() * 0.7, newPosition.getY() + graphic.getHeight() * 0.5);
            left = new Point(newPosition.getX() + graphic.getWidth() * 0.3, newPosition.getY() + graphic.getHeight() * 0.5);
            top = new Point(newPosition.getX() + graphic.getWidth() * 0.5, newPosition.getY() + graphic.getWidth() * 0.3);
            bottom = new Point(newPosition.getX() + graphic.getWidth() * 0.5, newPosition.getY() + graphic.getHeight() * 0.7);
            
            collision = maze.getElementAt(right) != null || 
                maze.getElementAt(left) != null || 
                maze.getElementAt(top) != null || 
                maze.getElementAt(bottom) != null ||
                right.getX() >= MazeGame.CANVAS_WIDTH ||
                left.getX() <= 0 ||
                top.getY() <= 0 ||
                bottom.getY() >= MazeGame.CANVAS_HEIGHT;
            if (collision) {
                return true;
            }
            // line1.setStartPosition(right);
            // line1.setEndPosition(left);
            // line2.setStartPosition(top);
            // line2.setEndPosition(bottom);
        }
        
        return false;
    }
}
