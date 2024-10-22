import java.util.Objects;

public class Box {
    private String label;
    private int focalLength;

    public Box(String label, int focalLength) {
        this.label = label;
        this.focalLength = focalLength;
    }

    public Box(String label) {
        this.label = label;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public void updateFocalLength(int newValue){
        this.focalLength = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(label, box.label);
    }
}
