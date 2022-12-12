import java.util.Objects;

public class Point {
    int x;
    int y;
    char value;
    Point parent;
    boolean isGoal = false;
    boolean isStart = false;

    public Point(int x, int y, char value, Point parent) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.parent = parent;
        if (value == 'S') {
            this.value = 'a';
            isStart = true;
        }
        if (value == 'E') {
            this.value = 'z';
            isGoal = true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point))
            return false;
        var p = (Point) obj;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
