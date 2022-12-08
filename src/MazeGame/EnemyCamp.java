package MazeGame;

import edu.macalester.graphics.*;

public class EnemyCamp {
    private GraphicsGroup graphic = new GraphicsGroup();
    private Enemy[] enemies = new Enemy[4];
    
    // private Image img0_0 = new Image("enemyCamp0_0.jpg");
    // private Image img1_0 = new Image("enemyCamp1_0.jpg");
    // private Image img2_0 = new Image("enemyCamp2_0.jpg");
    // private Image img0_1 = new Image("enemyCamp0_1.jpg");
    // private Image img1_1 = new Image("enemyCamp1_1.jpg");
    // private Image img2_1 = new Image("enemyCamp2_1.jpg");
    // private Image img0_2 = new Image("enemyCamp0_2.jpg");
    // private Image img1_2 = new Image("enemyCamp1_2.jpg");
    // private Image img2_2 = new Image("enemyCamp2_2.jpg");
    
    private Image img0_0 = new Image("grass.jpg");
    private Image img1_0 = new Image("grass.jpg");
    private Image img2_0 = new Image("grass.jpg");
    private Image img0_1 = new Image("grass.jpg");
    private Image img1_1 = new Image("grass.jpg");
    private Image img2_1 = new Image("grass.jpg");
    private Image img0_2 = new Image("grass.jpg");
    private Image img1_2 = new Image("grass.jpg");
    private Image img2_2 = new Image("grass.jpg");
    private CanvasWindow canvas;
    private GraphicsGroup maze;
    private GraphicsGroup minimap;
    private Player mainPlayer;
    
    public EnemyCamp(CanvasWindow canvas, GraphicsGroup maze, GraphicsGroup minimap, Player mainPlayer){
        this.canvas = canvas;
        this.maze = maze;
        this.minimap = minimap;
        this.mainPlayer = mainPlayer;
        createGraphic();
        
    }
    public GraphicsGroup getGraphics(){
        return graphic;
    }
    public void addToGraphicsGroup(GraphicsGroup gr,int x, int y,GraphicsGroup enemyGroup){
        gr.add(graphic);
        graphic.setPosition(x,y);
        Point[] enemyPositions = {new Point(20,20), new Point(100,20), new Point(100,100), new Point(20,100)};
        for (int i = 0; i < 4; i++) {
            enemies[i] = new Enemy(canvas, maze, minimap, this, mainPlayer);
            enemyGroup.add(enemies[i].getGraphics());
            enemies[i].getGraphics().setCenter(enemyPositions[i].add(graphic.getPosition()));
            System.out.println(enemies[i].getGraphics().getCenter());
        }
    }
    private void createGraphic(){
        graphic.add(img0_0);
        img0_0.setPosition(0,0);
        graphic.add(img1_0);
        img1_0.setPosition(40,0);
        graphic.add(img2_0);
        img2_0.setPosition(80,0);
        graphic.add(img0_1);
        img0_1.setPosition(0,40);
        graphic.add(img1_1);
        img1_1.setPosition(40,40);
        graphic.add(img2_1);
        img2_1.setPosition(80,40);
        graphic.add(img0_2);
        img0_2.setPosition(0,80);
        graphic.add(img1_2);
        img1_2.setPosition(40,80);
        graphic.add(img2_2);
        img2_2.setPosition(80,80);
    }
    
}
