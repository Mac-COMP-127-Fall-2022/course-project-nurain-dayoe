package MazeGame;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Minimap {
    private static  double temp = 0 ; 
    public GraphicsGroup miniMap;
    public Image mapImage;
    public Ellipse mapPointer;
    public Integer pointerSize;
    public Rectangle destinationPointer;
    public Rectangle border;
    private CanvasWindow canvas;
    public Minimap(double windowSize, CanvasWindow c){
        canvas = c;
        miniMap = new GraphicsGroup();
        border = new Rectangle(0,0,windowSize,windowSize);
        border.setFillColor(Color.DARK_GRAY);
        mapImage = new Image("test.png");
        pointerSize = 7;
        mapPointer = new Ellipse(0, 0, pointerSize, pointerSize);
        destinationPointer = new Rectangle(0, 0, pointerSize, pointerSize);
        mapPointer.setFillColor(Color.RED);
        destinationPointer.setFillColor(Color.YELLOW);
        miniMap.add(border);
        border.setPosition(0,0);
        miniMap.setPosition(0,0);
        miniMap.add(mapImage);
        mapImage.setCenter(windowSize/2,windowSize/2);
        miniMap.add(destinationPointer);
        miniMap.add(mapPointer);
        mapPointer.setCenter(10,15);
        destinationPointer.setCenter(windowSize - windowSize/10,  windowSize - windowSize/10);
    }
    public void update(double x, double y ){

        mapPointer.setCenter(x,y);
        
        
        /*code for player
         */
    }
    public void addToCanvas(CanvasWindow canvas){
        canvas.add(miniMap);
        miniMap.setPosition(canvas.getWidth()-miniMap.getWidth(), canvas.getHeight() - miniMap.getHeight());
    }
    
    public static void main(String[] args) {
        CanvasWindow c = new CanvasWindow("test", 220, 220);
        Minimap m = new Minimap(220,c);
        m.addToCanvas(c);
        c.animate(() -> {

            m.update(temp, temp);
            temp += 1;

        });
    }
}
