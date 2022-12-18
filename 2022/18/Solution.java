import java.io.File;
import java.io.IOException;
import java.util.*;

public class Solution {

    public record Transform(int x, int y, int z) {
    }

    public record Position(int x, int y, int z) {
        int maxCoord() {
            return Math.max(x, Math.max(y, z));
        }

        Position move(Transform t) {
            return new Position(x + t.x, y + t.y, z + t.z);
        }
    }

    static final Transform[] transf = new Transform[] {
            new Transform(1, 0, 0),
            new Transform(0, 1, 0),
            new Transform(0, 0, 1),
            new Transform(-1, 0, 0),
            new Transform(0, -1, 0),
            new Transform(0, 0, -1) };

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        int maxCoordinate = 0;
        Set<Position> found = new HashSet<>();
        while (s.hasNextLine()) {
            String[] pos = s.nextLine().split(",");
            Position p = new Position(ints(pos[0]), ints(pos[1]), ints(pos[2]));
            maxCoordinate = (maxCoordinate > p.maxCoord()) ? maxCoordinate : p.maxCoord();
            found.add(p);
        }
        s.close();

        System.out.println("part1: " + part1(found));

        System.out.println("part2: " + part2(found, maxCoordinate));

    }

    public static int part1(Set<Position> found) {
        int maxSurface = 6 * found.size();
        for (Position position : found)
            for (Position other : found)
                if (other != position && isAdjacent(other, position))
                    maxSurface--;
        return maxSurface;
    }

    public static int part2(Set<Position> found, int maxCoordinate) {
        Set<Position> perms = permutations(maxCoordinate, found);
        BFS(new Position(0, 0, 0), perms);
        return part1(found) - part1(perms);
    }

    public static void BFS(Position start, Set<Position> stateSpace) {
        Deque<Position> frontier = new LinkedList<>();
        Set<Position> EXL = new HashSet<>();
        frontier.addLast(start);
        while (!frontier.isEmpty()) {
            Position current = frontier.removeFirst();
            stateSpace.remove(current);
            for (Position next : getNeighbours(current, stateSpace)) {
                if (!EXL.contains(next)) {
                    EXL.add(next);
                    frontier.add(next);
                }
            }
        }
    }

    public static List<Position> getNeighbours(Position p, Set<Position> stateSpace) {
        List<Position> res = new ArrayList<>();
        for (Transform tr : transf)
            if (stateSpace.contains(p.move(tr)))
                res.add(p.move(tr));
        return res;
    }

    // it could be more readable, but this way should be more efficient
    public static boolean isAdjacent(Position a, Position b) {
        return ((Math.abs(a.x - b.x) == 1 && a.y == b.y && a.z == b.z) ||
                (a.x == b.x && Math.abs(a.y - b.y) == 1 && a.z == b.z) ||
                ((a.x == b.x && a.y == b.y && Math.abs(a.z - b.z) == 1)));
    }

    public static Set<Position> permutations(int maxNum, Set<Position> found) {
        Set<Position> res = new HashSet<>();
        Position p;
        for (int i = 0; i <= maxNum; i++)
            for (int j = 0; j <= maxNum; j++)
                for (int j2 = 0; j2 <= maxNum; j2++)
                    if (!found.contains(p = new Position(i, j, j2)))
                        res.add(p);
        return res;
    }

    public static int ints(String s) {
        return Integer.parseInt(s);
    }
}