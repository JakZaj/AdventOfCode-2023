import java.util.ArrayList;
import java.util.Map;

public class Hash {

    public int runHashAlgorithm(String input){
        int value = 0;

        for (int index = 0; index < input.length(); index++){
            value += input.charAt(index);
            value *= 17;
            value %= 256;
        }

        return value;
    }

    public void runHashAlgorithmPartTwo(String input, Map<Integer, ArrayList<Box>> boxes){
        int value = 0;

        if (input.contains("="))
        {
            String[] inputSplited = input.split("=");

            value = runHashAlgorithm(inputSplited[0]);

            if (boxes.get(value) == null){
                boxes.put(value, new ArrayList<>());
            }

            ArrayList<Box> box = boxes.get(value);

            for (Box b : box){
                if (b.equals(new Box(inputSplited[0]))){
                    b.updateFocalLength(Integer.parseInt(inputSplited[1]));
                    return;
                }
            }

            box.addLast(new Box(inputSplited[0], Integer.parseInt(inputSplited[1])));

            return;
        }

        String[] inputSplited = input.split("-");

        value = runHashAlgorithm(inputSplited[0]);

        if (boxes.get(value) == null){
            return;
        }

        ArrayList<Box> box = boxes.get(value);

        for (Box b : box){
            if (b.equals(new Box(inputSplited[0]))){
                box.remove(b);
                return;
            }
        }
    }
}
