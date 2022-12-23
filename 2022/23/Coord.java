public record Coord(int x, int y) {
    Coord sumOffset(Coord c) {
        return new Coord(x + c.x, y + c.y);
    }

    public static final Coord[][] offsets = new Coord[][] {
            new Coord[] { new Coord(1, -1), new Coord(0, -1), new Coord(-1, -1) }, // N
            new Coord[] { new Coord(-1, 1), new Coord(0, 1), new Coord(1, 1) }, // S
            new Coord[] { new Coord(-1, -1), new Coord(-1, 0), new Coord(-1, 1) }, // W
            new Coord[] { new Coord(1, -1), new Coord(1, 0), new Coord(1, 1) } // E
    };

}
