package MazeGame;

import edu.macalester.graphics.*;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class MazeGenerator {
    public GraphicsGroup mazeMap;
    private byte[][] mazePattern = new byte[100][100];
    private Random rand = new Random();

    public MazeGenerator(){
        mazeMap = new GraphicsGroup();
        Scanner fileScanner;
        Double[][] imageBluePrintMatrix = new Double[100][100];
        try {
            fileScanner = new Scanner(new File("bgDataSet.csv"));
            fileScanner.useDelimiter(",");   
            int counter = 0;
            while (fileScanner.hasNext())  
                {  
                    int row = Math.floorDiv(counter, 100);
                    int column = counter%100;
                    //System.out.println(row + " "+ column);
                    imageBluePrintMatrix[row][column] = Double.valueOf(fileScanner.next());
                    counter +=1;
                }    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  
        Double[][] bluePrintMatrix = new Double[100][100];
        try {
            fileScanner = new Scanner(new File("sample3.csv"));
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
            
            for(Double i: row){

                String img1Path = "resPack/"+ imageBluePrintMatrix[rowIndex][columnIndex].intValue() + ".jpg";
                
                if (i==0){
                    Image tmp = new Image(img1Path);
                    tmp.setPosition(rowIndex*pos, columnIndex*pos);
                    mazeMap.add(tmp);
              
                }
                
                columnIndex+=1;
            }
            rowIndex+=1;
            columnIndex=0;
        }
    }

        public GraphicsGroup getMap() {
            return mazeMap;
        }
    }



        // for (int x = 0; x < 100; x++) {
        //     for (int y = 0; y < 100; y++) {
        //         if (x == 0 || y == 0 || x == 99 || y == 99) {
        //             mazePattern[x][y] = 1;
        //         } else {
        //             mazePattern[x][y] = 0;
        //         }
        //     }
        // }

        // generateMaze(1,98,1,98);

        // int row = 0, column = 0, width = 40;
        // for (byte[] cells : mazePattern) {
        //     for (byte cell : cells) {
        //         if (cell == 1) {
        //             Image temp = new Image("grass.jpg");
        //             temp.setPosition(row * width, column * width);
        //             mazeMap.add(temp);
        //         }
        //         column++;
        //     }
        //     column = 0;
        //     row++;
        // }
    

    // private void generateMaze(int minX, int maxX, int minY, int maxY) {
    //     if (minX + 1 >= maxX || minY + 1 >= maxY) {
    //         return;
    //     }

    //     int column = rand.nextInt(minX + 1, maxX);
    //     for (int i = minY; i <= maxY; i++) {
    //         mazePattern[column][i] = 1;
    //     }

    //     int row = rand.nextInt(minY + 1, maxY);
    //     for (int i = minX; i <= maxX; i++) {
    //         mazePattern[i][row] = 1;
    //     }

    //     int skipNumber = rand.nextInt(0,4);
    //     if (skipNumber != 0 && row > minY) {
    //         mazePattern[column][rand.nextInt(minY, row)] = 0;
    //     }
    //     if (skipNumber != 1 && maxY > row + 1) {
    //         mazePattern[column][rand.nextInt(row + 1, maxY)] = 0;
    //     }
    //     if (skipNumber != 2 && column > minX) {
    //         mazePattern[rand.nextInt(minX, column)][row] = 0;
    //     }
    //     if (skipNumber != 3 && maxX > column + 1) {
    //         mazePattern[rand.nextInt(column + 1, maxX )][row] = 0;
    //     }
    //     generateMaze(minX, column - 2, minY, row - 2);
    //     generateMaze(column + 2, maxX, minY, row - 2);
    //     generateMaze(column + 2, maxX, row + 2, maxY);
    //     generateMaze(minX, column - 2, row + 2, maxY);
    // }
