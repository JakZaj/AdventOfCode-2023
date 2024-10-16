import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Space space = new Space("input.txt");

        ArrayList<Galaxy> galaxies = expandSpace(space.getAllGalaxiesPositions(), space.getRowsToExpands(), space.getColumnsToExpands(), 1);
        long totalSteps = 0;

        for (Galaxy gal : galaxies){
            for (int index = galaxies.indexOf(gal) + 1; index < galaxies.size(); index++){
                totalSteps += gal.getShortestPath(galaxies.get(index));
            }
        }

        System.out.println(totalSteps);

        ArrayList<Galaxy> galaxiesOlder = expandSpace(space.getAllGalaxiesPositions(), space.getRowsToExpands(), space.getColumnsToExpands(), 999999);
        totalSteps = 0;

        for (Galaxy gal : galaxiesOlder){
            for (int index = galaxiesOlder.indexOf(gal) + 1; index < galaxiesOlder.size(); index++){
                totalSteps += gal.getShortestPath(galaxiesOlder.get(index));
            }
        }

        System.out.println(totalSteps);

    }


    public static ArrayList<Galaxy> expandSpace(ArrayList<Galaxy> galaxies, ArrayList<Integer> rowToExpand, ArrayList<Integer> columnToExpand, int expandMultiply){
        ArrayList<Galaxy> expandedGalaxies = (ArrayList<Galaxy>) galaxies.clone();

        int offset = 0;

        for (int index : rowToExpand){
            for (Galaxy gal : expandedGalaxies){
                if (gal.getGalaxyPosition().y > index + offset){
                    gal.rowExpands(expandMultiply);
                }
            }
            offset += expandMultiply;
        }

        offset = 0;

        for (int index : columnToExpand){
            for (Galaxy gal : expandedGalaxies){
                if (gal.getGalaxyPosition().x > index + offset){
                    gal.columnsExpands(expandMultiply);
                }
            }
            offset += expandMultiply;
        }

        return expandedGalaxies;
    }
}