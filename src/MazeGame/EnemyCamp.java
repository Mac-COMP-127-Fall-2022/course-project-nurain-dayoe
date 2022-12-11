package MazeGame;

import edu.macalester.graphics.*;

/**
 * An EnemyCamp with four Enemies that can be displayed on the CanvasWindow.
 */
public class EnemyCamp {
    private GraphicsGroup graphic = new GraphicsGroup();
    public Enemy[] enemies = new Enemy[4];
    
    private Image img0_0 = new Image("resPack/00Camp.jpg");
    private Image img1_0 = new Image("resPack/10Camp.jpg");
    private Image img2_0 = new Image("resPack/20Camp.jpg");
    private Image img0_1 = new Image("resPack/01Camp.jpg");
    private Image img1_1 = new Image("resPack/11Camp.jpg");
    private Image img2_1 = new Image("resPack/21Camp.jpg");
    private Image img0_2 = new Image("resPack/02Camp.jpg");
    private Image img1_2 = new Image("resPack/12Camp.jpg");
    private Image img2_2 = new Image("resPack/22Camp.jpg");
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

    /**
     * Populate this EnemyCamp with four enemies, one at each corner of the camp.
     * @param campX The x location of the left of EnemyCamp
     * @param campY The y location of the top of EnemyCamp
     * @param campGraphicsGroup The GraphicsGroup containing the EnemyCamps
     * @param enemyGraphicsGroup The GraphicsGroup containing the Enemies
     */
    public void populateEnemies(int campX, int campY, GraphicsGroup campGraphicsGroup, GraphicsGroup enemyGraphicsGroup){
        campGraphicsGroup.add(graphic);
        graphic.setPosition(campX,campY);
        Point[] enemyPositions = {new Point(20,20), new Point(100,20), new Point(100,100), new Point(20,100)};
        for (int i = 0; i < 4; i++) {
            enemies[i] = new Enemy(canvas, maze, minimap, this, mainPlayer, enemyGraphicsGroup, enemyPositions[i].add(graphic.getPosition()));
            enemyGraphicsGroup.add(enemies[i].getGraphics());
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

    public Enemy[] getEnemies() {
        return enemies;
    }
    
}
