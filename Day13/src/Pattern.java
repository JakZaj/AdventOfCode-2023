import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pattern {
    Map<Point, String> pattern = new HashMap<>();

    private int patternSizeX = 0;
    private int patternSizeY = 0;

    private int oldReflectionX = -1;
    private int oldReflectionY = -1;

    public void setPatternSizeX(int patternSizeX) {
        this.patternSizeX = patternSizeX;
    }

    public void setPatternSizeY(int patternSizeY) {
        this.patternSizeY = patternSizeY;
    }

    public void addPatterRow(String patternRow, int y){

        if (this.patternSizeX == 0)
            setPatternSizeX(patternRow.length());
        if (this.patternSizeY < y)
            setPatternSizeY(y);

        for (int x = 0; x < patternSizeX; x++) {
            if (patternRow.charAt(x) == '#');
            this.pattern.put(new Point(x, y), String.valueOf(patternRow.charAt(x)));
        }
    }

    public int findReflectionVertical(){
        int summarize = 0;
        for (int x = 0; x < patternSizeX - 1; x++){

            if (checkVertical(x)){
                this.oldReflectionX = x;
                summarize += x + 1;
            }
        }

        return summarize;
    }

    public int findReflectionVerticalPart2(){
        int summarize = 0;
        for (int x = 0; x < patternSizeX - 1; x++){

            if (checkVerticalPart2(x)){
                if (this.oldReflectionX == -1 || this.oldReflectionX != x){
                    summarize += x + 1;
                }

            }
        }

        return summarize;
    }

    public int findReflectionHorizontal(){
        int summarize = 0;
        for (int y = 0; y < patternSizeY; y++){

            if (checkHorizontal(y)){
                this.oldReflectionY = y;
                summarize += (y + 1) * 100;
            }
        }

        return summarize;
    }

    public int findReflectionHorizontalPart2(){
        int summarize = 0;
        for (int y = 0; y < patternSizeY; y++){

            if (checkHorizontalPart2(y)){
                if (this.oldReflectionY == -1 || this.oldReflectionY != y){
                    summarize += (y + 1) * 100;
                }
            }
        }

        return summarize;
    }


    private boolean checkVertical(int columnIndex){
        if (patternSizeX / 2 > columnIndex){
            for (int x = 0; columnIndex + x >= 0; x--){
                for(int y = 0; y <= patternSizeY; y++){

                    if (!Objects.equals(pattern.get(new Point(columnIndex + x, y)), pattern.get(new Point(columnIndex - x + 1, y)))){
                        return false;
                    }
                }
            }
        } else {
            for (int x = 0; columnIndex + x + 1 < patternSizeX; x++){
                for(int y = 0; y <= patternSizeY; y++){

                    if (!Objects.equals(pattern.get(new Point(columnIndex - x, y)), pattern.get(new Point(columnIndex + x + 1, y)))){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean checkVerticalPart2(int columnIndex){
        int smudgeCount = 0;

        if (patternSizeX / 2 > columnIndex){
            for (int x = 0; columnIndex + x >= 0; x--){
                for(int y = 0; y <= patternSizeY; y++){

                    if (!Objects.equals(pattern.get(new Point(columnIndex + x, y)), pattern.get(new Point(columnIndex - x + 1, y)))){
                        smudgeCount++;
                        if (smudgeCount > 1){
                            return false;
                        }
                    }
                }
            }
        } else {
            for (int x = 0; columnIndex + x + 1 < patternSizeX; x++){
                for(int y = 0; y <= patternSizeY; y++){

                    if (!Objects.equals(pattern.get(new Point(columnIndex - x, y)), pattern.get(new Point(columnIndex + x + 1, y)))){
                        smudgeCount++;
                        if (smudgeCount > 1) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean checkHorizontal(int rowIndex){
        if (patternSizeY / 2 > rowIndex){
            for (int y = 0; rowIndex + y >= 0; y--){
                for(int x = 0; x < patternSizeX; x++){

                    if (!Objects.equals(pattern.get(new Point(x, rowIndex + y)), pattern.get(new Point(x, rowIndex - y + 1)))){
                        return false;
                    }
                }
            }
        } else {
            for (int y = 0; rowIndex + y + 1 <= patternSizeY; y++){
                for(int x = 0; x < patternSizeX; x++){

                    if (!Objects.equals(pattern.get(new Point(x, rowIndex - y)), pattern.get(new Point(x, rowIndex + y + 1)))){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean checkHorizontalPart2(int rowIndex){
        int smudgeCount = 0;

        if (patternSizeY / 2 > rowIndex){
            for (int y = 0; rowIndex + y >= 0; y--){
                for(int x = 0; x < patternSizeX; x++){

                    if (!Objects.equals(pattern.get(new Point(x, rowIndex + y)), pattern.get(new Point(x, rowIndex - y + 1)))){
                        smudgeCount++;

                        if (smudgeCount > 1) {
                            return false;
                        }
                    }
                }
            }
        } else {
            for (int y = 0; rowIndex + y + 1 <= patternSizeY; y++){
                for(int x = 0; x < patternSizeX; x++){

                    if (!Objects.equals(pattern.get(new Point(x, rowIndex - y)), pattern.get(new Point(x, rowIndex + y + 1)))) {
                        smudgeCount++;

                        if (smudgeCount > 1) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
