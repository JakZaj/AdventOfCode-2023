import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Space {
    private final Map<Point, String> space;

    public Space(String filePath) {
        this.space = new HashMap<>();

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            int y = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                for (int x = 0; x < data.length(); x++) {
                    this.space.put(new Point(x, y), String.valueOf(data.charAt(x)));
                }

                y++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public ArrayList<Galaxy> getAllGalaxiesPositions(){
        ArrayList<Galaxy> galaxies = new ArrayList<>();

        for (Point point : this.space.keySet()){

            if (this.space.get(point).equals("#")){
                galaxies.add(new Galaxy((Point) point.clone()));
            }
        }

        return galaxies;
    }

    public ArrayList<Integer> getColumnsToExpands(){
        ArrayList<Integer> indexsToExpands = new ArrayList<>();

        int x = 0;
        boolean toExpand;
        ArrayList<Galaxy> galaxies = getAllGalaxiesPositions();

        while (this.space.get(new Point(x, 0)) != null){

            toExpand = true;

            for (Galaxy galaxy : galaxies){
                if (galaxy.getGalaxyPosition().x == x){
                    toExpand = false;
                    break;
                }
            }

            if (toExpand){
                indexsToExpands.add(x);
            }

            x++;
        }

        return indexsToExpands;
    }

    public ArrayList<Integer> getRowsToExpands(){
        ArrayList<Integer> indexsToExpands = new ArrayList<>();

        int y = 0;
        boolean toExpand;
        ArrayList<Galaxy> galaxies = getAllGalaxiesPositions();

        while (this.space.get(new Point(0, y)) != null){

            toExpand = true;

            for (Galaxy galaxy : galaxies){
                if (galaxy.getGalaxyPosition().y == y){
                    toExpand = false;
                    break;
                }
            }

            if (toExpand){
                indexsToExpands.add(y);
            }

            y++;
        }

        return indexsToExpands;
    }
}
