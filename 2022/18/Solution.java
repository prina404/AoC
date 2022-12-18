import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

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
        BufferedReader b = new BufferedReader(new FileReader("input.txt"));
        int maxCoordinate = 0;
        Set<Position> found = new HashSet<>();
        Function<String, Integer> f = (x) -> Integer.parseInt(x);
        while (b.ready()) {
            String[] pos = b.readLine().split(",");
            Position p = new Position(f.apply(pos[0]), f.apply(pos[1]), f.apply(pos[2]));
            maxCoordinate = (maxCoordinate > p.maxCoord()) ? maxCoordinate : p.maxCoord();
            found.add(p);
        }
        b.close();

        int part1 = part1(found);
        System.out.println("part1: " + part1);
        System.out.println("part2: " + (part1 - part2(found, maxCoordinate)));
    }

    public static int part1(Set<Position> found) {
        int maxSurface = 6 * found.size();
        for (Position position : found)
            for (Transform tr : transf) 
                if (found.contains(position.move(tr)))
                    maxSurface--;
        return maxSurface;
    }

    public static int part2(Set<Position> found, int maxCoordinate) {
        Set<Position> perms = permutations(maxCoordinate, found);
        DFS(new Position(0, 0, 0), perms);
        return part1(perms);
    }

    public static void DFS(Position start, Set<Position> stateSpace) {
        Deque<Position> frontier = new LinkedList<>();
        Set<Position> EXL = new HashSet<>();
        frontier.push(start);
        while (!frontier.isEmpty()) {
            Position current = frontier.pop();
            stateSpace.remove(current);
            for (Transform tr : transf){
                Position next = current.move(tr);
                if (!EXL.contains(next) && stateSpace.contains(next)) {
                    EXL.add(next);
                    frontier.push(next);
                }
            }
        }
    }

    public static Set<Position> permutations(int maxNum, Set<Position> found) {
        Set<Position> res = new HashSet<>();
        Position p;
        for (int i = 0; i <= maxNum; i++)
            for (int j = 0; j <= maxNum; j++)
                for (int k = 0; k <= maxNum; k++)
                    if (!found.contains(p = new Position(i, j, k)))
                        res.add(p);
        return res;
    }
}