package MazeGame;
import MazeGame.Block.Side;
import edu.macalester.graphics.*;

public class Player {
    public final static double SPEED = 11;
    private final double WIDTH, HEIGHT;

    private Point position;
    private PlayerImage animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
    private PlayerImage animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
    private PlayerImage animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
    private PlayerImage animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");
    private GraphicsGroup maze;
    private Image graphic = new Image(animMapFront.next());

    private Line line1 = new Line(0, 0, 0, 0);
    private Line line2 = new Line(0, 0, 0, 0);
    
    public Player(CanvasWindow canvas, GraphicsGroup maze){
        this.maze = maze;
        canvas.add(graphic);

        canvas.add(line1);
        canvas.add(line2);

        graphic.setPosition(500, 500); //TODO: Set inital position


        graphic.setScale(0.3);
        position = graphic.getPosition();
        WIDTH = graphic.getWidth();
        HEIGHT = graphic.getHeight();
        canvas.add(graphic);
    }

    public Image getGraphics() {
        return graphic;
    }

    public void move(Side side, boolean move) {
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
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (!collision(side) && move) {
            Point newPosition = position.add(MazeGame.directionVectors.get(side));
            position = newPosition;
            graphic.setPosition(newPosition);
        }
    }

    public boolean collision(Side side) {
        Point moveVector = MazeGame.directionVectors.get(side);
        Point point1, point2, point3;
        Point newPosition = position.add(moveVector);
        switch (side) {
            case RIGHT:
                point1 = new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.35);
                point2 = new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.5);
                point3 = new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.65);
                break;
            case LEFT:
                point1 = new Point(newPosition.getX() + WIDTH * 0.35, newPosition.getY() + HEIGHT * 0.35);
                point2 = new Point(newPosition.getX() + WIDTH * 0.35, newPosition.getY() + HEIGHT * 0.5);
                point3 = new Point(newPosition.getX() + WIDTH * 0.35, newPosition.getY() + HEIGHT * 0.65);
                break;
            case TOP:
                point1 = new Point(newPosition.getX() + WIDTH * 0.35, newPosition.getY() + HEIGHT * 0.35);
                point2 = new Point(newPosition.getX() + WIDTH * 0.5, newPosition.getY() + HEIGHT * 0.35);
                point3 = new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.35);
                break;
            case BOTTOM: 
                point1 = new Point(newPosition.getX() + WIDTH * 0.35, newPosition.getY() + HEIGHT * 0.65);
                point2 = new Point(newPosition.getX() + WIDTH * 0.5, newPosition.getY() + HEIGHT * 0.65);
                point3 = new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.65);
                break;
            default:
                throw new IllegalArgumentException();
        }
        line1.setStartPosition(new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.35));
        line1.setEndPosition(new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.65));
        line2.setStartPosition(new Point(newPosition.getX() + WIDTH * 0.35, newPosition.getY() + HEIGHT * 0.35));
        line2.setEndPosition(new Point(newPosition.getX() + WIDTH * 0.65, newPosition.getY() + HEIGHT * 0.35));
        if (maze.getElementAt(point1) != null || maze.getElementAt(point2) != null || maze.getElementAt(point3) != null) {
            return true;
        }
        Point right = new Point(newPosition.getX() + WIDTH * 0.7, newPosition.getY() + HEIGHT * 0.5);
        Point left = new Point(newPosition.getX() + WIDTH * 0.3, newPosition.getY() + HEIGHT * 0.5);
        Point top = new Point(newPosition.getX() + WIDTH * 0.5, newPosition.getY() + WIDTH * 0.3);
        Point bottom = new Point(newPosition.getX() + WIDTH * 0.5, newPosition.getY() + HEIGHT * 0.7);
        if (right.getX() >= MazeGame.CANVAS_WIDTH || left.getX() <= 0 || top.getY() <= 0 || bottom.getY() >= MazeGame.CANVAS_HEIGHT) {
            return true;
        }
        
        return false;
    }
}
