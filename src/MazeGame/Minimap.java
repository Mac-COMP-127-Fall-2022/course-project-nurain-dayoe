package MazeGame;

import java.awt.Color;
import java.io.File;

import edu.macalester.graphics.*;

/**
* A class representing a Minimap with the Player's real-time position and destination marked.
*/
public class Minimap {
    public final static double windowSize = 220;
    private GraphicsGroup miniMap;
    private Image mapImage;
    private Ellipse mapPointer;
    private int pointerSize;
    private Rectangle destinationPointer;

    private Rectangle border;
    
    /**
    * Create a new Minimap in the top-right of the CanvasWindow.
    */
    public Minimap(CanvasWindow canvas){
        miniMap = new GraphicsGroup();
        boolean success = false;
        do {
            canvas.pause(1000);
            File mapImageFile = new File("res/mazeminimap.jpg");
            if (mapImageFile.exists()) {
                mapImage = new Image("mazeminimap.jpg");
                if (Math.abs(mapImage.getWidth() - 64) > 1) {
                    success = true;
                }
            }
            //System.out.println(success);
        } while (!success);
        
        mapImage.setScale(0.1,0.1);
       
        pointerSize = 7;
        mapPointer = new Ellipse(0, 0, pointerSize, pointerSize);
        destinationPointer = new Rectangle(0, 0, pointerSize, pointerSize);
        border = new Rectangle(0, 0, Minimap.windowSize, Minimap.windowSize);
        
        mapPointer.setFillColor(Color.RED);
        destinationPointer.setFillColor(Color.YELLOW);
        border.setFillColor(Color.BLACK);

        miniMap.add(border);
        miniMap.setPosition(0,0);
        miniMap.add(mapImage);
        mapImage.setCenter(Minimap.windowSize/2, Minimap.windowSize/2);

        miniMap.add(destinationPointer);
        miniMap.add(mapPointer);

        miniMap.setPosition(canvas.getWidth()-miniMap.getWidth() -20 ,  20);
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
        System.out.println();
        double sizeRatio = 0.05;
        double xPos = (x*sizeRatio) + 10;
        double yPos = (y*sizeRatio) + 10;
        destinationPointer.setCenter(xPos,yPos);
    }

    public GraphicsGroup getGraphics() {
        return miniMap;
    }
}
