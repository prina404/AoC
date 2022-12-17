import java.util.ArrayList;
import java.util.List;

public class Figure {
    public record Coord(int x, int y) {
    }

    private List<Coord> lst = new ArrayList<>();

    public Figure(int type, int height) {
        switch (type) {
            case 0:
                for (int i = 0; i < 4; i++)
                    lst.add(new Coord(i + 3, height + 3));
                break;
            case 1:
                for (int i = 0; i < 3; i++)
                    lst.add(new Coord(i + 3, height + 4));
                lst.add(new Coord(4, height + 3));
                lst.add(new Coord(4, height + 5));
                break;
            case 2:
                for (int i = 0; i < 3; i++)
                    lst.add(new Coord(i + 3, height + 3));
                lst.add(new Coord(5, height + 4));
                lst.add(new Coord(5, height + 5));
                break;
            case 3:
                for (int i = 0; i < 4; i++)
                    lst.add(new Coord(3, height + 3 + i));
                break;
            case 4:
                for (int i = 0; i < 2; i++) {
                    lst.add(new Coord(i + 3, height + 3));
                    lst.add(new Coord(i + 3, height + 4));
                }

        }
    }

    public void moveHorizontal(int offset) {
        for (int i = lst.size() - 1; i >= 0; i--) {
            Coord c = lst.remove((int) i);
            lst.add(new Coord(c.x + offset, c.y));
        }
    }

    public void moveVertical(int offset) {
        for (int i = lst.size() - 1; i >= 0; i--) {
            Coord c = lst.remove((int) i);
            lst.add(new Coord(c.x, c.y + offset));
        }
    }

    public boolean collides(Figure f) {
        for (Coord c : lst) {
            if (c.x == 8 || c.x == 0 || c.y == -1)
                return true;
            if (f.lst.contains(c) && f != this)
                return true;
        }

        return false;
    }

    public int tallestPoint() {
        int max = 0;
        for (Coord coord : lst) {
            if (coord.y > max)
                max = coord.y;
        }
        return max + 1;
    }
}
