import java.awt.*;

public class Galaxy {
    private final Point galaxyPosition;

    public Galaxy(Point galaxyPosition) {
        this.galaxyPosition = galaxyPosition;
    }

    public Point getGalaxyPosition() {
        return (Point) galaxyPosition.clone();
    }

    public void  columnsExpands(int expandsValue){
        this.galaxyPosition.x += expandsValue;
    }

    public void  rowExpands(int expandsValue){
        this.galaxyPosition.y += expandsValue;
    }

    public long getShortestPath(Galaxy otherGalaxy){
        return Math.abs(this.galaxyPosition.x - otherGalaxy.galaxyPosition.x) + Math.abs(this.galaxyPosition.y - otherGalaxy.galaxyPosition.y);
    }
}
