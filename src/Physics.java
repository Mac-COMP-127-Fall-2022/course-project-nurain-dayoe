package breakout;
import java.util.HashMap;
import java.util.Map;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;
public class Physics {

    public static final Key rightArrow = Key.RIGHT_ARROW;
    public static final  Key leftArrow = Key.LEFT_ARROW;
    private static final Key upArrow = Key.UP_ARROW;
    private static final Key downArrow = Key.DOWN_ARROW;
    private String curAnimFront = "anim1Front.bmp";
    private String curAnimLeft = "anim1Left.bmp";
    private String curAnimRight = "anim1Right.bmp";
    private String curAnimBack = "anim1Back.bmp";

    private Map animMapFront = new HashMap<>();
    private Map animMapLeft = new HashMap<>();
    private Map animMapRight = new HashMap<>();
    private Map animMapBack = new HashMap<>();
    
    public Physics(){
        animMapFront.put("anim1Front.bmp", "anim2Front.bmp");
        animMapFront.put("anim2Front.bmp","anim3Front.bmp");
        animMapFront.put("anim3Front.bmp","anim1Front.bmp");

        animMapLeft.put("anim1Left.bmp", "anim2Left.bmp");
        animMapLeft.put("anim2Left.bmp","anim3Left.bmp");
        animMapLeft.put("anim3Left.bmp","anim1Left.bmp");
        
        animMapRight.put("anim1Right.bmp", "anim2Right.bmp");
        animMapRight.put("anim2Right.bmp","anim3Right.bmp");
        animMapRight.put("anim3Right.bmp","anim1Right.bmp");

        animMapBack.put("anim1Back.bmp", "anim2Back.bmp");
        animMapBack.put("anim2Back.bmp","anim3Back.bmp");
        animMapBack.put("anim3Back.bmp","anim1Back.bmp");
    }
public void move(CanvasWindow canvas, Image background, Image sprite,Key key,GraphicsGroup layer){
    if (key == upArrow){
        yScroll(background, 1, canvas,sprite,layer);
    }else if(key==downArrow){
        yScroll(background,-1, canvas,sprite,layer);
    }else if (key == rightArrow){
        xScroll(background, -1, canvas,sprite,layer);
    }else if (key == leftArrow){
        xScroll(background, 1, canvas,sprite,layer);
    }
    

   } 
public void yScroll(Image background, double dy,CanvasWindow canvas,Image zelda,GraphicsGroup layer){
    double newX =  background.getPosition().getX();
    double newY =5*dy + background.getPosition().getY();

    Point zeldaWest= new Point(zelda.getCenter().getX() - 4, zelda.getCenter().getY());
  
    try {
        System.out.println(layer.getElementAt(zeldaWest).toString());
    } catch (Exception e) {
        //TODO: handle exception
    }
    System.out.println("_____________________-");
    if(dy<0){
        zelda.setImagePath(animMapFront.get(curAnimFront).toString());
        curAnimFront = animMapFront.get(curAnimFront).toString();
        
    }else{
        zelda.setImagePath(animMapBack.get(curAnimBack).toString());
        curAnimBack = animMapBack.get(curAnimBack).toString();
    }
    if (newX<=0 &&newY<=0 && newX >= -1*background.getWidth() + canvas.getWidth()&&newY >= -1*background.getHeight()+canvas.getHeight() &&zelda.getCenter().getY() == canvas.getHeight()/2 ){
        background.setPosition(newX, newY);
        layer.setPosition(newX,newY);

    }else{
        double zeldaNewX =  zelda.getCenter().getX();
        double zeldaNewY = -5*dy +zelda.getCenter().getY();
        Point newZeldaNorth =  new Point(zeldaNewX,zeldaNewY -5);
        Point newZeldaSouth = new Point(zeldaNewX,zeldaNewY +5);
        Point newZeldaEast = new Point(zeldaNewX+ 5,zeldaNewY); 
        Point newZeldaWest= new Point(zeldaNewX - 5, zeldaNewY);
        if (zeldaNewX<canvas.getWidth() && zeldaNewX>0 && zeldaNewY>0 &&zeldaNewY<canvas.getWidth()&&layer.getElementAt(newZeldaNorth)==null &&layer.getElementAt(newZeldaSouth)==null && layer.getElementAt(newZeldaEast)==null && layer.getElementAt(newZeldaWest)==null){
            zelda.setCenter(zeldaNewX,zeldaNewY);
            
        }
    }

}
public  void xScroll(Image background, double dx, CanvasWindow canvas,Image zelda,GraphicsGroup layer){
    double newX = 5*dx+ background.getPosition().getX();
    double newY = background.getPosition().getY();

    Point zeldaNorth =  new Point(zelda.getCenter().getX(),zelda.getCenter().getY() -5);
    Point zeldaSouth = new Point(zelda.getCenter().getX(),zelda.getCenter().getY() +5);
    Point zeldaEast = new Point(zelda.getCenter().getX() + 5,zelda.getCenter().getY()); 
    Point zeldaWest= new Point(zelda.getCenter().getX() - 5, zelda.getCenter().getY());
    System.out.println(layer.getElementAt(zeldaNorth)==null);
    System.out.println(layer.getElementAt(zeldaSouth)==null);
    System.out.println(layer.getElementAt(zeldaEast)==null);
    System.out.println(layer.getElementAt(zeldaWest)==null);
    try {
        System.out.println(layer.getElementAt(zeldaWest).toString());
        System.out.println(zelda.getCenter());
    } catch (Exception e) {
        //TODO: handle exception
    }
    
    System.out.println("___________________________________");


    
    if(dx<0){
        zelda.setImagePath(animMapRight.get(curAnimRight).toString());
        curAnimRight = animMapRight.get(curAnimRight).toString();
    }else{
        zelda.setImagePath(animMapLeft.get(curAnimLeft).toString());
        curAnimLeft= animMapLeft.get(curAnimLeft).toString();
    }
    if (newX<=0 &&newY<=0 && newX >= -1*background.getWidth() + canvas.getWidth()&&newY >= -1*background.getHeight()+canvas.getHeight() &&zelda.getCenter().getX()==canvas.getWidth()/2){
        background.setPosition(newX, newY);
        layer.setPosition(newX,newY);
    }else{
        System.out.println("doppler");
        double zeldaNewX = -5*dx + zelda.getCenter().getX();
        double zeldaNewY = zelda.getCenter().getY();
        Point newZeldaNorth =  new Point(zeldaNewX,zeldaNewY -5);
        Point newZeldaSouth = new Point(zeldaNewX,zeldaNewY +5);
        Point newZeldaEast = new Point(zeldaNewX+ 5,zeldaNewY); 
        Point newZeldaWest= new Point(zeldaNewX - 5, zeldaNewY);
        if (zeldaNewX<canvas.getWidth() && zeldaNewX>0 && zeldaNewY>0 &&zeldaNewY<canvas.getWidth()&&layer.getElementAt(newZeldaNorth)==null &&layer.getElementAt(newZeldaSouth)==null && layer.getElementAt(newZeldaEast)==null && layer.getElementAt(newZeldaWest)==null){
            zelda.setCenter(zeldaNewX,zeldaNewY);
        }
    
}
    
    
}


public void scrollBackground(Image background, MouseMotionEvent m,CanvasWindow canvas,Image zelda,GraphicsGroup layer){
    double newX = m.getDelta().getX() + background.getPosition().getX();
    double newY = m.getDelta().getY() + background.getPosition().getY();
    double newXZelda  =m.getDelta().getX() + zelda.getCenter().getX();
    double newYZelda = m.getDelta().getY() + zelda.getCenter().getY();

    if (newX<=0 &&newY<=0 && newX >= -1*background.getWidth() + canvas.getWidth()&&newY >= -1*background.getHeight()+canvas.getHeight()){
        background.setPosition(newX, newY);
        layer.setPosition(newX, newY);
        zelda.setCenter(newXZelda,newYZelda);
        
    }

}
}
