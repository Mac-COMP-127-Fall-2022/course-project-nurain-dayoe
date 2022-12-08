package MazeGame;

import java.awt.Color;

import edu.macalester.graphics.*;

/**
* A class representing a Minimap with the Player's real-time position and destination marked.
*/
public class Minimap {
    public static int test;
    private GraphicsGroup miniMap;
    private Image mapImage;
    private Ellipse mapPointer;
    private Integer pointerSize;
    private Rectangle destinationPointer;

    private Rectangle border;
    
    /**
    * Create a new Minimap in the top-right of the CanvasWindow.
    */
    public Minimap(double windowSize, CanvasWindow c){
        miniMap = new GraphicsGroup();
        mapImage = new Image("mazeminimap.jpg");
        mapImage.setScale(0.1,0.1);
       
        pointerSize = 7;
        mapPointer = new Ellipse(0, 0, pointerSize, pointerSize);
        destinationPointer = new Rectangle(0, 0, pointerSize, pointerSize);
        border = new Rectangle(0,0,windowSize, windowSize);
        
        mapPointer.setFillColor(Color.RED);
        destinationPointer.setFillColor(Color.YELLOW);
        border.setFillColor(Color.BLACK);

        miniMap.add(border);
        miniMap.setPosition(0,0);
        miniMap.add(mapImage);
        mapImage.setCenter(windowSize/2, windowSize/2);

        miniMap.add(destinationPointer);
        miniMap.add(mapPointer);
    }

    /**
    * Set the Player's position to the specified x and y in Block coordinates.
    */
    public void setPlayerPosition(double x, double y ){
        double sizeRatio = 0.05;
        double xPos = (x*sizeRatio) + 10;
        double yPos = (y*sizeRatio) + 10;
        mapPointer.setCenter(xPos,yPos);
    }

    /**
    * Set the end target position to the specified x and y in Block coordinates.
    */
    public void setTargetPosition(double x, double y){
        double sizeRatio = 0.05;
        double xPos = (x*sizeRatio) + 10;
        double yPos = (y*sizeRatio) + 10;
        mapPointer.setCenter(xPos,yPos);
    }
    
    /**
    * Add the miniMap to the canvas in the correct position
    */
    public void addToCanvas(CanvasWindow canvas){
        canvas.add(miniMap);
        miniMap.setPosition(canvas.getWidth()-miniMap.getWidth() -20 ,  20);
    }

    public GraphicsGroup getGraphics() {
        return miniMap;
    }
}
