package MazeGame;

import edu.macalester.graphics.Image;

public class EnemyCamp {
    private Image graphic ;
    private int campSize; 

    public EnemyCamp(Integer campsize){
        this.campSize = campsize;
    }
    public Image getGraphics(){
        return graphic;
    }
    
    
}
