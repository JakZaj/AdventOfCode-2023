import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Hash hash = new Hash();

        String sequence = "";

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            sequence = myReader.nextLine();

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        int sumOfTheResults = 0;

        for (String string : sequence.split(",")){
            sumOfTheResults += hash.runHashAlgorithm(string);
        }

        System.out.println(sumOfTheResults);

        Map<Integer, ArrayList<Box>> boxes = new HashMap<>();


        for (String string : sequence.split(",")){
            hash.runHashAlgorithmPartTwo(string, boxes);
        }

        sumOfTheResults = 0;

        for (Integer i : boxes.keySet()){
            for (Box box : boxes.get(i)){
                sumOfTheResults += (i + 1) * (boxes.get(i).indexOf(box) + 1)  * box.getFocalLength();
            }
        }

        System.out.println(sumOfTheResults);
    }
}