package MazeGame;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class Hearts extends GraphicsGroup {


    public Hearts(Integer heartCount,CanvasWindow canvas){
        for(int c = 0 ; c<heartCount; c++){
            double centerY =  canvas.getHeight()/25 ;
            double centerX =  canvas.getWidth()/20 + (c*canvas.getWidth()/27);
            Image tempHeart = new Image("Heart.bmp");
            tempHeart.setScale(0.5);
            this.add(tempHeart);
            tempHeart.setCenter(centerX, centerY);
        }
    }
    public void addToCanvas(CanvasWindow canvas){
        canvas.add(this);
    }
    public void update(Integer heartCount,CanvasWindow canvas){
        this.removeAll();
        for(int c = 0 ; c<heartCount; c++){

            double centerY =  canvas.getHeight()/25 ;
            double centerX = (canvas.getWidth() - canvas.getWidth()/20 )- (c*canvas.getWidth()/14);
            Image tempHeart = new Image("Heart.bmp");
            this.add(tempHeart);
            tempHeart.setCenter(centerX, centerY);

        }

    }



}
