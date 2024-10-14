import java.awt.*;

public class Node {
    private Point point;
    private int numberOfSteps = 0;

    public Node(Point point) {
        this.point = point;
    }

    public Node(Point point, int steps) {
        this.point = point;
        this.numberOfSteps = steps + 1;
    }

    public Point getPoint() {
        return point;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        return this.point.equals(other.point);
    }
}
