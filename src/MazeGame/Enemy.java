package MazeGame;
import java.util.ArrayList;
import java.util.Random;

import edu.macalester.graphics.*;

public class Enemy extends Character{
    private Player mainPlayer; 
    private double responseRadiusSquared;
    private EnemyCamp enemyCamp;
    private Random rand = new Random();
    
    public Enemy(CanvasWindow canvas, GraphicsGroup maze, EnemyCamp enemyCamp, Player mainPlayer){
        super(canvas, maze);
        this.enemyCamp = enemyCamp;
        this.mainPlayer = mainPlayer;

        animMapFront = new PlayerImage("anim2Front.bmp", "anim3Front.bmp", "anim1Front.bmp");
        animMapLeft = new PlayerImage("anim2Left.bmp", "anim3Left.bmp", "anim1Left.bmp");
        animMapRight = new PlayerImage("anim2Right.bmp", "anim3Right.bmp", "anim1Right.bmp");
        animMapBack = new PlayerImage("anim2Back.bmp", "anim3Back.bmp", "anim1Back.bmp");

        graphic = new Image(animMapFront.next());

        position = graphic.getPosition();
        WIDTH = graphic.getImageWidth();
        HEIGHT = graphic.getHeight();
        
        responseRadiusSquared  = 10000;
        graphic = new Image("anim1Front.bmp");
    }
    
    @Deprecated
    public void AI(){
        if (Math.pow(graphic.getCenter().getX()- mainPlayer.getGraphics().getCenter().getX(),2) + Math.pow(graphic.getCenter().getY()- mainPlayer.getGraphics().getCenter().getY(),2) < responseRadiusSquared){
            boolean collision = false;
            if (!collision){
                Integer [] weights = new Integer[4];// north , east, south , west 
                weights[0] = -1;
                weights[1] = -1;
                weights[2] = -1;
                weights[3] = -1;
                if(mainPlayer.getPosition().getX()> graphic.getCenter().getX())
                    weights[1] = 1;
                else if (mainPlayer.getPosition().getY()<graphic.getCenter().getY())
                    weights[3] = 3;
                
                
                if (mainPlayer.getPosition().getY()>graphic.getCenter().getY())
                    weights[2] = 2;
                else if (mainPlayer.getPosition().getY()<graphic.getCenter().getY())
                    weights[0] = 0;
                
                ArrayList<Integer> nonNegativeWeightsIndex = new ArrayList<Integer>();
                
                for (int weight: weights){
                    if (weight>-1){
                        nonNegativeWeightsIndex.add(weight);
                    }
                }
                if (nonNegativeWeightsIndex.size()>0){
                    
                    int randomIndex = rand.nextInt(nonNegativeWeightsIndex.size());
                    int randomMovementIndex = nonNegativeWeightsIndex.get(randomIndex);
                    position = graphic.getPosition();
                    switch (randomMovementIndex){
                        case 0:
                            if (!collision(MazeGame.Side.UP)){
                                move(MazeGame.Side.UP);
                                
                            }
                            
                            break;
                        case 1:
                            if (!collision(MazeGame.Side.RIGHT)){
                                move(MazeGame.Side.RIGHT);
                                System.out.println("h");
                            }
                            break;
                        case 2 : 
                            if (!collision(MazeGame.Side.DOWN)){
                                move(MazeGame.Side.DOWN);
                                System.out.println("h");
                            }
                            break;
                        case 3: 
                            if (!collision(MazeGame.Side.LEFT)){
                                move(MazeGame.Side.LEFT);
                                System.out.println("h");
                            }
                            break;
                    }
                
                }else{
                    System.out.println("hitting player.");
                }
            }
        
            
            //chase player but stay within certain range. 
        }else{

        }
        
    }

    public void addToGraphicsGroup(GraphicsGroup gr, Point initialPosition){
        gr.add(graphic);
        graphic.setCenter((initialPosition.getX()) * 40 + graphic.getWidth() * 0.25, (initialPosition.getY()) * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);
    }

    public void addToCanvas(CanvasWindow canvas, Point initialPosition){
        canvas.add(graphic);
        graphic.setCenter((initialPosition.getX()) * 40 + graphic.getWidth() * 0.25, (initialPosition.getY()) * 40 + graphic.getHeight() * 0.25);
        graphic.setScale(0.3);
    }
}
