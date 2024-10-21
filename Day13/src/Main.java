import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Pattern> patternArrayList = new ArrayList<>();

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            Pattern newPattern = new Pattern();
            int y = 0;

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();

                if (data.equals("")){
                    patternArrayList.add(newPattern);
                    newPattern = new Pattern();
                    y = 0;
                } else {
                    newPattern.addPatterRow(data, y++);
                }
            }
            patternArrayList.add(newPattern);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        int summarize = 0;

        for (Pattern value : patternArrayList) {
            summarize += value.findReflectionHorizontal();
            summarize += value.findReflectionVertical();
        }

        System.out.println(summarize);

        summarize = 0;

        for (Pattern pattern : patternArrayList) {
            summarize += pattern.findReflectionHorizontalPart2();
            summarize += pattern.findReflectionVerticalPart2();
        }

        System.out.println(summarize);
    }
}