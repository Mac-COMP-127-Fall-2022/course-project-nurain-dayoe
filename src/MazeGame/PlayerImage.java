package MazeGame;

public class PlayerImage {
    private String images[] = new String[3];
    private byte index = -1;

    public PlayerImage(String image1, String image2, String image3) {
        images[0] = image1;
        images[1] = image2;
        images[2] = image3;
    }

    public String next() {
        index++;
        if(index >= 2) {
            index = 0;
        }
        return images[index];
    }
    
}
