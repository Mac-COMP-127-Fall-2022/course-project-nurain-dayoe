package MazeGame;

import edu.macalester.graphics.*;

public class Hearts {

    private static GraphicsGroup heartsGroup = new GraphicsGroup();
    private static Image[] hearts = new Image[5];

    //Private constructor left empty to enforce non-instantiability
    private Hearts() {

    }

    public static void generateHearts(Integer heartCount,CanvasWindow canvas){
        for(int c = 0 ; c<heartCount; c++){
            double centerY =  canvas.getHeight()/25 ;
            double centerX =  canvas.getWidth()/20 + (c*canvas.getWidth()/27);
            hearts[c] = new Image("Heart.bmp");
            hearts[c].setScale(0.5);
            heartsGroup.add(hearts[c]);
            hearts[c].setCenter(centerX, centerY);
        }
    }

    public static GraphicsGroup getGraphics(){
        return heartsGroup;
    }
    
    public static boolean update(Integer heartCount){
        try {
            heartsGroup.remove(hearts[heartCount]);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
