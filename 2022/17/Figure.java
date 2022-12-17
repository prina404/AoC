import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Figure {
    public record Coord(long x, long y) {
    }

    private List<Coord> lst = new ArrayList<>();
    public final int type;
    public int dropDist;

    public Figure(int type, long height) {
        switch (type) {
            case 0:
                for (long i = 0; i < 4; i++)
                    lst.add(new Coord(i + 3, height + 3));
                break;
            case 1:
                for (long i = 0; i < 3; i++)
                    lst.add(new Coord(i + 3, height + 4));
                lst.add(new Coord(4, height + 3));
                lst.add(new Coord(4, height + 5));
                break;
            case 2:
                for (long i = 0; i < 3; i++)
                    lst.add(new Coord(i + 3, height + 3));
                lst.add(new Coord(5, height + 4));
                lst.add(new Coord(5, height + 5));
                break;
            case 3:
                for (long i = 0; i < 4; i++)
                    lst.add(new Coord(3, height + 3 + i));
                break;
            case 4:
                for (long i = 0; i < 2; i++) {
                    lst.add(new Coord(i + 3, height + 3));
                    lst.add(new Coord(i + 3, height + 4));
                }
 
        }
        this.type = type;
    }

    public Figure getCopy(){
        Figure res = new Figure(0, 0);
        res.lst = new ArrayList<>(lst);
        return res;
    }

    public void moveHorizontal(long offset) {
        for (long i = lst.size() - 1; i >= 0; i--) {
            Coord c = lst.remove((int)i);
            lst.add(new Coord(c.x + offset, c.y));
        }
    }

    public void moveVertical(long offset) {
        for (long i = lst.size() - 1; i >= 0; i--) {
            Coord c = lst.remove((int)i);
            lst.add(new Coord(c.x, c.y + offset));
            dropDist += offset;
        }
    }

    public boolean collides(Figure f) {
        for (Coord c : lst){
            if (c.x == 8 || c.x == 0 || c.y == -1)
                return true;
            if (f.lst.contains(c) && f!= this)
                return true;
            }

        return false;
    }

    public boolean occupies(long x, long y) {
        for (Coord coord : lst)
            if (coord.x == x && coord.y == y)
                return true;
        return false;
    }

    public long tallestPoint(){
        long max = 0;
        for (Coord coord : lst) {
            if (coord.y > max)
                max = coord.y;
        }
        return max+1;
    }

    @Override
    public int hashCode() {
        return lst.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Figure){
            Figure f = (Figure) obj;
            Set<Coord> s = new HashSet<>(f.lst);
            Set<Coord> k = new HashSet< >(lst);
            return s.size() == k.size();
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.valueOf(type)+String.valueOf(dropDist);
    }
}
