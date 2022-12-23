import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Elf {
    public Coord pos;
    public Coord next; 
    private static List<Integer> order = new ArrayList<>(List.of(0, 1, 2, 3));

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
        for (Integer move : order) {
            isolated = true;
            for (var step : Coord.offsets[move])
                if (occupied.contains(pos.sumOffset(step)))
                    isolated = false;
            if (isolated) {
                next = pos.sumOffset(Coord.offsets[move][1]);
                return next;
            }
        }
        return pos;
    }

    public boolean makeMove() {
        boolean hasMoved = pos != next;
        pos = next;
        return hasMoved;
    }

    public static void updateOrder(){
        order.add(order.remove(0));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Elf))
            return false;
        Elf e = (Elf) obj;
        return pos.equals(e.pos);
    }
}
