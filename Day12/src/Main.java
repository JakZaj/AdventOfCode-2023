import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> springs = new ArrayList<>();
        ArrayList<String> backup = new ArrayList<>();

        Map<String, Long> doneCombination = new HashMap<>();

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] splitedData = data.split(" ");

                springs.add(splitedData[0]);
                backup.add(splitedData[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        long totalCount = 0;

        for (int i = 0; i < springs.size(); i++){
            totalCount += recurrentCheckSpring(doneCombination, springs.get(i), backup.get(i));
        }

        System.out.println(totalCount);

        ArrayList<String> springsPartTwo = new ArrayList<>();
        ArrayList<String> backupPartTwo = new ArrayList<>();
        totalCount = 0;

        for (int i = 0; i < springs.size(); i++){
            StringBuilder tempSpring = new StringBuilder();
            StringBuilder tempBackup = new StringBuilder();

            for(int copyCount = 0; copyCount < 4; copyCount++){
                tempSpring.append(springs.get(i)).append("?");
                tempBackup.append(backup.get(i)).append(",");
            }

            tempSpring.append(springs.get(i));
            tempBackup.append(backup.get(i));

            springsPartTwo.add(tempSpring.toString());
            backupPartTwo.add(tempBackup.toString());

            totalCount += recurrentCheckSpring(doneCombination, springsPartTwo.get(i), backupPartTwo.get(i));
        }

        System.out.println(totalCount);
    }

    public static long recurrentCheckSpring(Map<String, Long> doneCombination, String spring, String backup){

        String[] shortenString = tryShortenString(spring, backup);

        if (doneCombination.get(spring + " " + backup) != null)
            return doneCombination.get(spring + " " + backup);

        if (!canBeValid(spring, backup)){
            doneCombination.put(spring + " " + backup, 0L);
            return 0;
        }

        long totalCount = 0;

        if (spring.contains("?")){

            totalCount += recurrentCheckSpring(doneCombination, shortenString[0].replaceFirst("\\?", "."), shortenString[1]);
            totalCount += recurrentCheckSpring(doneCombination, shortenString[0].replaceFirst("\\?", "#"), shortenString[1]);

            doneCombination.put(spring + " " + backup, totalCount);
        } else {
            return isCorrectSpring(shortenString[0], shortenString[1]);
        }
        return totalCount;
    }

    public static int isCorrectSpring(String spring, String backup){

        String[] splitedBackup = backup.split(",");

        if (spring.startsWith(".")){
            spring = spring.replaceFirst("\\.+", "");
        }

        String[] splitedSpring = spring.split("\\.+", 0);

        if (splitedBackup.length != splitedSpring.length)
            return 0;

        ArrayList<Integer> groupSizes = new ArrayList<>();

        for (String s : splitedBackup){
            groupSizes.add(Integer.parseInt(s));
        }

        int i = 0;
        boolean isValid = true;

        for (String s : splitedSpring){
            if (s.length() != groupSizes.get(i)){
                isValid = false;
                break;
            }

            i++;
        }

        return isValid ? 1 : 0;
    }

    public static boolean canBeValid(String spring, String backup){

        if (!spring.contains("?"))
            return true;

        if (spring.startsWith(".")){
            spring = spring.replaceFirst("\\.+", "");
        }

        String[] splitedSpring = spring.split("\\.+", 0);
        String[] splitedBackup = backup.split(",");

        int countHashtag = 0;
        int countQuestionmark = 0;

        for (String s : splitedSpring) {
            if (s.contains("#")){
                countHashtag++;
            }
        }

        if (countHashtag > splitedBackup.length)
            return false;

        for (int i = 0; i < splitedSpring.length; i++){

            if (splitedSpring[i].contains("?")){

                for (countHashtag = 0; countHashtag < splitedSpring[i].length(); countHashtag++){
                    if (splitedSpring[i].charAt(countHashtag) == '?')
                        break;
                }

                if (countHashtag != 0 && (i >= splitedBackup.length || countHashtag > Integer.parseInt(splitedBackup[i]))){
                    return false;
                }
                
                if (i == splitedSpring.length - 1){
                    countQuestionmark = 0;
                    countHashtag = 0;
                    for (int x = 0; x < splitedSpring[i].length(); x++){
                        if (splitedSpring[i].charAt(countHashtag) == '?')
                            countQuestionmark++;
                        if (splitedSpring[i].charAt(countHashtag) == '#')
                            countHashtag++;
                    }

                    int countMinSpaceNeeded = 0;
                    for (int index = i; index < splitedBackup.length; index++){
                        countMinSpaceNeeded += Integer.parseInt(splitedBackup[index]) + 1;
                    }
                    countMinSpaceNeeded--;

                    return countHashtag + countQuestionmark >= countMinSpaceNeeded && getHowMuchGroupCanBePlaced(countHashtag, countQuestionmark) >= splitedBackup.length - i;

                }
                return true;
            }

            if (splitedSpring[i].length() != Integer.parseInt(splitedBackup[i])){
                return false;
            }
        }

        return true;
    }

    public static int getHowMuchGroupCanBePlaced(int countHashTag, int countQuestionmark){
        int value = countHashTag + countQuestionmark;

        return (value % 2 == 1) ? (value / 2 + 1) : (value / 2);
    }

    public static String[] tryShortenString(String spring, String backup){

        if (spring.startsWith(".")){
            spring = spring.replaceFirst("\\.+", "");
        }

        String[] splitedSpring = spring.split("\\.+", 0);
        String[] splitedBackup = backup.split(",");

        String[] shortenString = new String[2];

        int i = 0;

        for (; i < splitedSpring.length; i++){

            if (splitedSpring[i].contains("?")){
                break;
            }
        }

        if (i <= 1 || i == splitedSpring.length || splitedBackup.length == 1){
            shortenString[0] = spring;
            shortenString[1] = backup;
            return shortenString;
        }

        StringBuilder tempSpring = new StringBuilder();
        StringBuilder tempBackup = new StringBuilder();

        for (int index = i - 1; index < splitedSpring.length - 1; index++){
            tempSpring.append(splitedSpring[index]).append(".");
        }

        for (int index = i - 1; index < splitedBackup.length - 1; index++){
            tempBackup.append(splitedBackup[index]).append(",");
        }

        tempSpring.append(splitedSpring[splitedSpring.length - 1]);
        tempBackup.append(splitedBackup[splitedBackup.length - 1]);

        shortenString[0] = tempSpring.toString();
        shortenString[1] = tempBackup.toString();

        return shortenString;
    }
}