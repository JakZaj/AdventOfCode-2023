import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Maze {
    private Map<Point, String> maze;

    private Point startPoint;

    enum Direction {
        NONE,
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public Maze(String filePath) {
        this.maze = new HashMap<>();

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            int y = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                for (int x = 0; x < data.length(); x++) {
                    this.maze.put(new Point(x, y), String.valueOf(data.charAt(x)));

                    if (this.maze.get(new Point(x, y)).equals("S"))
                        this.startPoint = new Point(x, y);
                }

                y++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public Point getStartPoint() {
        return this.startPoint;
    }

    public ArrayList<Point> getConnectedPoints(Point point) {
        ArrayList<Point> connectedPoints = new ArrayList<>();

        switch (this.maze.get(point)) {
            case "S":
                if (isConnected(new Point(point.x, point.y - 1), Direction.NORTH))
                    connectedPoints.add(new Point(point.x, point.y - 1));
                if (isConnected(new Point(point.x, point.y + 1), Direction.SOUTH))
                    connectedPoints.add(new Point(point.x, point.y + 1));
                if (isConnected(new Point(point.x - 1, point.y), Direction.WEST))
                    connectedPoints.add(new Point(point.x - 1, point.y));
                if (isConnected(new Point(point.x + 1, point.y), Direction.EAST))
                    connectedPoints.add(new Point(point.x + 1, point.y));
                break;

            case "|":
                if (isConnected(new Point(point.x, point.y - 1), Direction.NORTH))
                    connectedPoints.add(new Point(point.x, point.y - 1));
                if (isConnected(new Point(point.x, point.y + 1), Direction.SOUTH))
                    connectedPoints.add(new Point(point.x, point.y + 1));
                break;

            case "-":
                if (isConnected(new Point(point.x - 1, point.y), Direction.WEST))
                    connectedPoints.add(new Point(point.x - 1, point.y));
                if (isConnected(new Point(point.x + 1, point.y), Direction.EAST))
                    connectedPoints.add(new Point(point.x + 1, point.y));
                break;
            case "L":
                if (isConnected(new Point(point.x, point.y - 1), Direction.NORTH))
                    connectedPoints.add(new Point(point.x, point.y - 1));
                if (isConnected(new Point(point.x + 1, point.y), Direction.EAST))
                    connectedPoints.add(new Point(point.x + 1, point.y));
                break;
            case "J":
                if (isConnected(new Point(point.x, point.y - 1), Direction.NORTH))
                    connectedPoints.add(new Point(point.x, point.y - 1));
                if (isConnected(new Point(point.x - 1, point.y), Direction.WEST))
                    connectedPoints.add(new Point(point.x - 1, point.y));
                break;
            case "7":
                if (isConnected(new Point(point.x, point.y + 1), Direction.SOUTH))
                    connectedPoints.add(new Point(point.x, point.y + 1));
                if (isConnected(new Point(point.x - 1, point.y), Direction.WEST))
                    connectedPoints.add(new Point(point.x - 1, point.y));
                break;
            case "F":
                if (isConnected(new Point(point.x, point.y + 1), Direction.SOUTH))
                    connectedPoints.add(new Point(point.x, point.y + 1));
                if (isConnected(new Point(point.x + 1, point.y), Direction.EAST))
                    connectedPoints.add(new Point(point.x + 1, point.y));
                break;

        }
        return connectedPoints;
    }

    private Boolean isConnected(Point point, Direction connectedDirection) {

        try {
            switch (this.maze.get(point)) {
                case "|":
                    return connectedDirection == Direction.NORTH || connectedDirection == Direction.SOUTH;
                case "-":
                    return connectedDirection == Direction.WEST || connectedDirection == Direction.EAST;
                case "L":
                    return connectedDirection == Direction.SOUTH || connectedDirection == Direction.WEST;
                case "J":
                    return connectedDirection == Direction.SOUTH || connectedDirection == Direction.EAST;
                case "7":
                    return connectedDirection == Direction.NORTH || connectedDirection == Direction.EAST;
                case "F":
                    return connectedDirection == Direction.NORTH || connectedDirection == Direction.WEST;
                default:
                    return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    public int getNumberTilesEnclosedByTheLoop(Map<Point, String> loopPoints, int minX, int maxX, int minY, int maxY) {

        int tilesEnclosedCounter = 0;

        for (int y = minY + 1; y < maxY; y++) {
            for (int x = minX + 1; x < maxX; x++) {
                if (loopPoints.get(new Point(x, y)) != null) {
                    continue;
                }

                if (isItLoopedHorizontally(loopPoints, new Point(x, y), minX, maxX) && isItLoopedVertically(loopPoints, new Point(x, y), minY, maxY)) {
                    tilesEnclosedCounter++;
                }
            }
        }

        return tilesEnclosedCounter;
    }

    private boolean isItLoopedVertically(Map<Point, String> loopPoints, Point point, int minY, int maxY) {
        int hittedLoop = 0;
        Direction lastDirectionHorizontally = Direction.NONE;
        Tuple2<Integer, Direction> tuple2;

        if (Math.abs(minY - point.y) < Math.abs(maxY - point.y)) {
            for (int y = point.y - 1; y >= minY; y--) {
                if (loopPoints.containsKey(new Point(point.x, y))) {
                    tuple2 = isHittedLoop(lastDirectionHorizontally, Direction.EAST, Direction.WEST, hittedLoop, point.x, y, new HorizontalDirection(), "|");
                    hittedLoop = tuple2.getFirst();
                    lastDirectionHorizontally = tuple2.getSecond();
                }
            }
        } else {
            for (int y = point.y + 1; y <= maxY; y++) {
                if (loopPoints.containsKey(new Point(point.x, y))) {
                    tuple2 = isHittedLoop(lastDirectionHorizontally, Direction.EAST, Direction.WEST, hittedLoop, point.x, y, new HorizontalDirection(), "|");
                    hittedLoop = tuple2.getFirst();
                    lastDirectionHorizontally = tuple2.getSecond();
                }
            }
        }

        return hittedLoop % 2 == 1;
    }

    private boolean isItLoopedHorizontally(Map<Point, String> loopPoints, Point point, int minX, int maxX) {
        int hittedLoop = 0;
        Direction lastDirectionVertically = Direction.NONE;
        Tuple2<Integer, Direction> tuple2;

        if (Math.abs(minX - point.x) < Math.abs(maxX - point.x)) {
            for (int x = point.x - 1; x >= minX; x--) {
                if (loopPoints.containsKey(new Point(x, point.y))) {
                    tuple2 = isHittedLoop(lastDirectionVertically, Direction.NORTH, Direction.SOUTH, hittedLoop, x, point.y, new VerticalDirection(), "-");
                    hittedLoop = tuple2.getFirst();
                    lastDirectionVertically = tuple2.getSecond();
                }
            }
        } else {
            for (int x = point.x + 1; x <= maxX; x++) {
                if (loopPoints.containsKey(new Point(x, point.y))) {
                    tuple2 = isHittedLoop(lastDirectionVertically, Direction.NORTH, Direction.SOUTH, hittedLoop, x, point.y, new VerticalDirection(), "-");
                    hittedLoop = tuple2.getFirst();
                    lastDirectionVertically = tuple2.getSecond();
                }
            }
        }

        return hittedLoop % 2 == 1;
    }

    private Tuple2<Integer, Direction> isHittedLoop(Direction lastDirection, Direction firstDirection, Direction secondDirection,int hittedLoop, int x, int y, DirectionSelect directionSelect, String skipCharacter) {

        if (lastDirection == Direction.NONE) {
            lastDirection = directionSelect.returnDirection(this.maze, new Point(x, y));
            hittedLoop++;
        } else if ((lastDirection == firstDirection && directionSelect.returnDirection(this.maze, new Point(x, y)) == secondDirection) || (lastDirection == secondDirection && directionSelect.returnDirection(this.maze, new Point(x, y)) == firstDirection)) {
            lastDirection = Direction.NONE;
        } else if ((lastDirection == firstDirection && directionSelect.returnDirection(this.maze, new Point(x, y)) == firstDirection) || (lastDirection == secondDirection && directionSelect.returnDirection(this.maze, new Point(x, y)) == secondDirection)) {
            hittedLoop++;
            lastDirection = Direction.NONE;
        }

        return new Tuple2<Integer, Direction>(hittedLoop, lastDirection);
    }

    private interface DirectionSelect {
        Direction returnDirection(Map<Point, String> maze, Point point);
    }

    private static class HorizontalDirection implements DirectionSelect{
        public Direction returnDirection(Map<Point, String> maze, Point point) {
            switch (maze.get(point)) {
                case "L":
                    return Direction.WEST;
                case "J":
                    return Direction.EAST;
                case "F":
                    return Direction.WEST;
                case "7":
                    return Direction.EAST;
                default:
                    return Direction.NONE;
            }
        }
    }

    private static class VerticalDirection implements DirectionSelect{
        public Direction returnDirection(Map<Point, String> maze, Point point) {
            switch (maze.get(point)) {
                case "L":
                    return Direction.SOUTH;
                case "J":
                    return Direction.SOUTH;
                case "F":
                    return Direction.NORTH;
                case "7":
                    return Direction.NORTH;
                default:
                    return Direction.NONE;
            }
        }
    }
}


