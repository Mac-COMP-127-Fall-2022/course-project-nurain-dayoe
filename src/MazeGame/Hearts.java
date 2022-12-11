package MazeGame;

import edu.macalester.graphics.*;

/**
 * A non-instantiable row of Hearts representing the Player's health.
 */
public class Hearts {

    private static GraphicsGroup heartsGroup = new GraphicsGroup();
    private static Image[] hearts = new Image[5];
    private static int heartCount;

    //Private constructor left empty to enforce non-instantiability
    private Hearts() {

    }

    /**
     * Add the required number of heart images to a GraphicsGroup available with Hearts.getGraphics()
     * @param heartCount The number of hearts to add (usually 5)
     */
    public static void generateHearts(Integer heartCount){
        for(int c = 0 ; c < heartCount; c++){
            double centerY =  MazeGame.CANVAS_HEIGHT/25 ;
            double centerX =  MazeGame.CANVAS_WIDTH/20 + (c*MazeGame.CANVAS_WIDTH/27);
            hearts[c] = new Image("Heart.bmp");
            hearts[c].setScale(0.5);
            heartsGroup.add(hearts[c]);
            hearts[c].setCenter(centerX, centerY);
        }
        Hearts.heartCount = heartCount;
    }

    public static GraphicsGroup getGraphics(){
        return heartsGroup;
    }

    /**
     * Remove one heart from the canvas.
     */
    public static boolean removeHeart(){
        try {
            heartsGroup.remove(hearts[heartCount - 1]);
            heartCount--;
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
