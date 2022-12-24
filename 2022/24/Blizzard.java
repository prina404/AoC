
public class Blizzard {

    private int[] offsets;
    private int[] pos;

    public Blizzard(Character direction, int x, int y) {
        pos = new int[] { x, y };
        switch (direction) {
            case '>' -> offsets = new int[] { 1, 0 };
            case '<' -> offsets = new int[] { -1, 0 };
            case '^' -> offsets = new int[] { 0, -1 };
            case 'v' -> offsets = new int[] { 0, 1 };
        }
    }

    public Solution.Coord move(int height, int width) {
        pos[0] = Math.floorMod(pos[0] + offsets[0], width);
        pos[1] = Math.floorMod(pos[1] + offsets[1], height);
        return new Solution.Coord(pos[0], pos[1]);
    }
}
