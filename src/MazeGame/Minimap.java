package MazeGame;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Minimap {
    public static int test;
    private GraphicsGroup miniMap;
    private Image mapImage;
    private Ellipse mapPointer;
    private Integer pointerSize;
    private Rectangle destinationPointer;
    private CanvasWindow canvas;
    
    public Minimap(double windowSize, CanvasWindow c){
        canvas = c;
        miniMap = new GraphicsGroup();
        
        mapImage = new Image("maze1minimap.jpg");
        pointerSize = 7;
        mapPointer = new Ellipse(0, 0, pointerSize, pointerSize);
        destinationPointer = new Rectangle(0, 0, pointerSize, pointerSize);
        mapPointer.setFillColor(Color.RED);
        destinationPointer.setFillColor(Color.YELLOW);
        mapImage.setScale(0.1,0.1);
        miniMap.setPosition(0,0);
        miniMap.add(mapImage);
        mapImage.setCenter(windowSize/2,windowSize/2);
        miniMap.add(destinationPointer);
        miniMap.add(mapPointer);
        mapPointer.setCenter(100,100);
        destinationPointer.setCenter(windowSize - windowSize/10,  windowSize - windowSize/10);
    }
    public void update(double x, double y ){
        double sizeRatio = 0.1;
        double xPos = (x*sizeRatio);
        double yPos = (y*sizeRatio);
        mapPointer.setCenter(xPos,yPos);
        // System.out.println(xPos);
        // System.out.println(yPos);
    }
    

    public void addToCanvas(CanvasWindow canvas){
        canvas.add(miniMap);
        miniMap.setPosition(canvas.getWidth()-miniMap.getWidth() - 20, canvas.getHeight() - miniMap.getHeight() - 20);
    }
}
