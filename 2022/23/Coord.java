import java.util.List;

public record Coord(int x, int y) {

    Coord sumOffset(Coord c) {
        return new Coord(x + c.x, y + c.y);
    }

    public static final List<List<Coord>> offsets = List.of(
            List.of(new Coord(1, -1), new Coord(0, -1), new Coord(-1, -1)), // N
            List.of(new Coord(-1, 1), new Coord(0, 1), new Coord(1, 1)), // S
            List.of(new Coord(-1, -1), new Coord(-1, 0), new Coord(-1, 1)), // W
            List.of(new Coord(1, -1), new Coord(1, 0), new Coord(1, 1)) // E
    );

}
