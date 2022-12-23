import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Elf {
    public Coord pos;
    public Coord next; 
    private List<Integer> order = new ArrayList<>(List.of(0, 1, 2, 3));

    public Elf(int x, int y) {
        pos = new Coord(x, y);
        next = pos;
    }

    public Coord makeProposal(Set<Coord> occupied) {
        boolean isolated = true;
        for (var move : Coord.offsets)
            for (var step : move)
                if (occupied.contains(pos.sumOffset(step)))
                    isolated = false;
        if (isolated)
            return pos;
        for (var move : order) {
            isolated = true;
            for (var step : Coord.offsets.get(move))
                if (occupied.contains(pos.sumOffset(step)))
                    isolated = false;
            if (isolated) {
                next = pos.sumOffset(Coord.offsets.get(move).get(1));
                return next;
            }
        }
        return pos;
    }

    public boolean makeMove() {
        boolean hasMoved = pos != next;
        pos = next;
        order.add(order.remove(0));
        return hasMoved;
    }

    public void dontMove() {
        next = pos;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Elf))
            return false;
        Elf e = (Elf) obj;
        return pos.equals(e.pos);
    }
}
