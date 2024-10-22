public class Main {
    public static void main(String[] args) {

        Platform platform = new Platform("input.txt");

        platform.tiltNorth();

        System.out.println(platform.getTotalLoad());

        Platform platformPartTwo = new Platform("input.txt");

        platformPartTwo.doSpinCycle(1000000000);

        System.out.println(platformPartTwo.getTotalLoad());

    }
}