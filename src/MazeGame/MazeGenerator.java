package MazeGame;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;

public class MazeGenerator {
    public GraphicsGroup mazeMap;

    public MazeGenerator(){
        mazeMap = new GraphicsGroup();
        Scanner fileScanner;
        Double[][] bluePrintMatrix = new Double[100][100];
        try {
            fileScanner = new Scanner(new File("sample2.csv"));
            fileScanner.useDelimiter(",");   
            int counter = 0;
            while (fileScanner.hasNext())  
                {  
                    int row = Math.floorDiv(counter, 100);
                    int column = counter%100;
                    //System.out.println(row + " "+ column);
                    bluePrintMatrix[row][column] = Double.valueOf(fileScanner.next());
                    counter +=1;
                }    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  

        int rowIndex = 0;
        int columnIndex = 0;
        int pos = 40;
        
        
        for (Double[] row:bluePrintMatrix){
            String curImage ;
            curImage = "grass.bmp";
            for(Double i: row){

                String img1Path = "grass.jpg";
                String img2Path = "grass2.bmp";
                if (curImage==img1Path){
                    curImage = img2Path;
                }else{
                    curImage = img1Path;
                }
                if (i==0){
                    // Rectangle temp = new Rectangle(rowIndex*pos, columnIndex*pos, pos, pos);
                    Image tmp = new Image(img1Path);
                    tmp.setPosition(rowIndex*pos, columnIndex*pos);
                    // temp.setFillColor(Color.BLACK);
                    mazeMap.add(tmp);
              
                }
                
                columnIndex+=1;
            }
            rowIndex+=1;
            columnIndex=0;
        }
   
    
    }
    public GraphicsGroup getMap(){
        return mazeMap;
    }
     public static void main(String[] args) {
        MazeGenerator pb = new MazeGenerator();
        
    }

}
