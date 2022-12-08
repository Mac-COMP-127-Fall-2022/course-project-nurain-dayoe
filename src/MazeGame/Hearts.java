package MazeGame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class Hearts {

    GraphicsGroup heartsGroup = new GraphicsGroup();

    public Hearts(Integer heartCount,CanvasWindow canvas){
        for(int c = 0 ; c<heartCount; c++){
            double centerY =  canvas.getHeight()/25 ;
            double centerX =  canvas.getWidth()/20 + (c*canvas.getWidth()/27);
            Image tempHeart = new Image("Heart.bmp");
            tempHeart.setScale(0.5);
            heartsGroup.add(tempHeart);
            tempHeart.setCenter(centerX, centerY);
        }
    }
    public GraphicsGroup getGraphics(){
        return heartsGroup;
    }
    public void update(Integer heartCount,CanvasWindow canvas){
        heartsGroup.removeAll();
        for(int c = 0 ; c<heartCount; c++){

            double centerY =  canvas.getHeight()/25 ;
            double centerX = (canvas.getWidth() - canvas.getWidth()/20 )- (c*canvas.getWidth()/14);
            Image tempHeart = new Image("Heart.bmp");
            heartsGroup.add(tempHeart);
            tempHeart.setCenter(centerX, centerY);

        }

    }

}
