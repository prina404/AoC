import java.util.Objects;

public class Point {
    int x;
    int y;
    char value;
    Point parent;

    public Point(int x, int y, char value, Point parent) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.parent = parent;
        if (value == 'S')
            this.value = 'a';
        if (value == 'E')
            this.value = 'z';
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
