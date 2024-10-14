import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze("input.txt");

        System.out.println(bfs(maze));

        System.out.println(getTilesInLoop(maze));

    }

    public static int bfs(Maze maze) {

        ArrayList<Node> visited = new ArrayList<>();
        ArrayList<Node> queue = new ArrayList<>();
        boolean skipThisPoint = false;

        queue.add(new Node(maze.getStartPoint()));

        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();
            ArrayList<Point> connectedPoints = maze.getConnectedPoints(node.getPoint());
            visited.add(node);
            for (Point point : connectedPoints) {
                skipThisPoint = false;

                for (Node visitedNode : visited) {
                    if (visitedNode.equals(new Node(point))) {
                        skipThisPoint = true;
                        break;
                    }
                }

                if (skipThisPoint)
                    continue;

                queue.add(new Node(point, node.getNumberOfSteps()));

            }
        }
        int maxSteps = 0;
        for (Node node : visited) {
            if (maxSteps < node.getNumberOfSteps()) {
                maxSteps = node.getNumberOfSteps();
            }
        }
        return maxSteps;
    }

    public static int getTilesInLoop(Maze maze) {

        Map<Point, String> visited = new HashMap<>();
        ArrayList<Point> queue = new ArrayList<>();

        queue.add(maze.getStartPoint());

        while (!queue.isEmpty()) {

            Point point = queue.removeFirst();
            ArrayList<Point> connectedPoints = maze.getConnectedPoints(point);
            visited.put(point, "");

            for (Point poi : connectedPoints) {

                if (visited.get(poi) != null)
                    continue;

                queue.add(poi);
            }
        }

        int minX = 10000, minY = 10000;
        int maxX = 0, maxY = 0;

        for (Point poi : visited.keySet()) {
            if (poi.x < minX)
                minX = poi.x;
            if (poi.x > maxX)
                maxX = poi.x;

            if (poi.y < minY)
                minY = poi.y;
            if (poi.y > maxY)
                maxY = poi.y;
        }

        return maze.getNumberTilesEnclosedByTheLoop(visited, minX, maxX, minY, maxY);
    }
}