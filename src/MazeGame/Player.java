package MazeGame;
import MazeGame.Block.Side;
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

        graphic.setPosition(15, 490); //TODO: Set inital position


        graphic.setScale(0.3);
        position = graphic.getPosition();
        canvas.add(graphic);
    }

    public Image getGraphics() {
        return graphic;
    }

    public void move(Side side) {
        Point newPosition = position.add(MazeGame.directionVectors.get(side));
        if (!collision(side)) {
            position = newPosition;
            graphic.setPosition(newPosition);
        }
        switch (side) {
            case RIGHT:
                graphic.setImagePath(animMapRight.next());
                break;
            case LEFT:
                graphic.setImagePath(animMapLeft.next());
                break;
            case TOP:
                graphic.setImagePath(animMapBack.next());
                break;
            case BOTTOM:
                graphic.setImagePath(animMapFront.next());
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean collision(Side side) {
        Point moveVector = MazeGame.directionVectors.get(side);
        Point newPosition = position.add(moveVector);
        Point right = new Point(newPosition.getX() + graphic.getWidth() * 0.7, newPosition.getY() + graphic.getHeight() * 0.5);
        Point left = new Point(newPosition.getX() + graphic.getWidth() * 0.3, newPosition.getY() + graphic.getHeight() * 0.5);
        Point top = new Point(newPosition.getX() + graphic.getWidth() * 0.5, newPosition.getY() + graphic.getWidth() * 0.3);
        Point bottom = new Point(newPosition.getX() + graphic.getWidth() * 0.5, newPosition.getY() + graphic.getHeight() * 0.7);

        
        // line1.setStartPosition(right);
        // line1.setEndPosition(left);
        // line2.setStartPosition(top);
        // line2.setEndPosition(bottom);
        return maze.getElementAt(newPosition) != null || 
               maze.getElementAt(right) != null || 
               maze.getElementAt(left) != null || 
               maze.getElementAt(top) != null || 
               maze.getElementAt(bottom) != null ||
               right.getX() >= MazeGame.CANVAS_WIDTH ||
               left.getX() <= 0 ||
               top.getY() <= 0 ||
               bottom.getY() >= MazeGame.CANVAS_HEIGHT;
    }
}
