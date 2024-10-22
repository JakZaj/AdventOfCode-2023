import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Platform {
    private Map<Point, String> platform;

    private Point size;

    public Platform(String filepath) {
        platform = new HashMap<>();
        size = new Point(0,0);

        try {
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            int y = 0;

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();

                if (size.x == 0)
                    size.x = data.length();

                for (int x = 0; x < data.length(); x++) {

                    if (data.charAt(x) != '.')
                        this.platform.put(new Point(x, y), String.valueOf(data.charAt(x)));
                }

                y++;
            }

            size.y = y;

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void doSpinCycle(int numberOfCycles){
        Map<Map<Point, String>, Map<Point, String>> doneCycles = new HashMap<>();

        Map<Point, String> platformCopy;

        ArrayList<Map<Point, String>> repeatedLoop = new ArrayList<>();

        int i = 0;

        for (; i < numberOfCycles; i++){
            if (doneCycles.get(this.platform) != null) {
                if (!repeatedLoop.isEmpty() && repeatedLoop.getFirst().equals(this.platform)){
                    break;
                }

                repeatedLoop.addLast(new HashMap<Point, String>(this.platform));
                this.platform = new HashMap<Point, String>(doneCycles.get(this.platform));

                continue;
            }

            platformCopy = new HashMap<Point, String>(this.platform);

            tiltNorth();
            tiltWest();
            tiltSouth();
            tiltEast();

            doneCycles.put(platformCopy, new HashMap<Point, String>(this.platform));

        }

        this.platform = repeatedLoop.get((numberOfCycles - i) % repeatedLoop.size());
    }

    public void tiltNorth(){
        int moveRockValue = 0;

        for (int x = 0; x < this.size.x; x++){
            for (int y = 0; y < this.size.y; y++){

                if (this.platform.get(new Point(x, y)) != null && this.platform.get(new Point(x, y)).equals("O")){

                    moveRockValue = getValueHowFarMoveRockNorth(new Point(x, y - 1));

                    if (moveRockValue != 0){
                        this.platform.remove(new Point(x, y));
                        this.platform.put(new Point(x, y - moveRockValue), "O");
                    }
                }
            }
        }
    }

    public void tiltWest(){
        int moveRockValue = 0;

        for (int y = 0; y < this.size.y; y++){
            for (int x = 0; x < this.size.x; x++){

                if (this.platform.get(new Point(x, y)) != null && this.platform.get(new Point(x, y)).equals("O")){

                    moveRockValue = getValueHowFarMoveRockWest(new Point(x - 1, y));

                    if (moveRockValue != 0){
                        this.platform.remove(new Point(x, y));
                        this.platform.put(new Point(x - moveRockValue, y), "O");
                    }
                }
            }
        }
    }

    public void tiltSouth(){
        int moveRockValue = 0;

        for (int x = 0; x < this.size.x; x++){
            for (int y = this.size.y - 1; y >= 0; y--){

                if (this.platform.get(new Point(x, y)) != null && this.platform.get(new Point(x, y)).equals("O")){

                    moveRockValue = getValueHowFarMoveRockSouth(new Point(x, y + 1));

                    if (moveRockValue != 0){
                        this.platform.remove(new Point(x, y));
                        this.platform.put(new Point(x, y + moveRockValue), "O");
                    }
                }
            }
        }
    }

    public void tiltEast(){
        int moveRockValue = 0;

        for (int y = 0; y < this.size.y; y++){
            for (int x = this.size.x - 1; x >= 0; x--){

                if (this.platform.get(new Point(x, y)) != null && this.platform.get(new Point(x, y)).equals("O")){

                    moveRockValue = getValueHowFarMoveRockEast(new Point(x + 1, y));

                    if (moveRockValue != 0){
                        this.platform.remove(new Point(x, y));
                        this.platform.put(new Point(x + moveRockValue, y), "O");
                    }
                }
            }
        }
    }

    private int getValueHowFarMoveRockNorth(Point point){
        if (point.y < 0 || this.platform.get(point) != null)
            return 0;

        return 1 + getValueHowFarMoveRockNorth(new Point(point.x, point.y - 1));
    }

    private int getValueHowFarMoveRockWest(Point point){
        if (point.x < 0 || this.platform.get(point) != null)
            return 0;

        return 1 + getValueHowFarMoveRockWest(new Point(point.x - 1, point.y));
    }

    private int getValueHowFarMoveRockSouth(Point point){
        if (point.y >= size.y || this.platform.get(point) != null)
            return 0;

        return 1 + getValueHowFarMoveRockSouth(new Point(point.x, point.y + 1));
    }

    private int getValueHowFarMoveRockEast(Point point){
        if (point.x >= size.x || this.platform.get(point) != null)
            return 0;

        return 1 + getValueHowFarMoveRockEast(new Point(point.x + 1, point.y));
    }

    public int getTotalLoad(){
        int totalLoadValue = 0;
        for (Point point : this.platform.keySet()){
            if (this.platform.get(point).equals("O")){
                totalLoadValue += size.y - point.y;
            }
        }

        return totalLoadValue;
    }
}


