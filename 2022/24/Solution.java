import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {

    public record State(int x, int y, int depth) {
    }

    public record Coord(int x, int y) {
    }

    static int width;
    static int height;
    static List<Set<Coord>> states = new ArrayList<>();
    static List<Blizzard> blizzards = new ArrayList<>();
    static Coord start;
    static Coord end;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        int y = 0;

        width = s.nextLine().length() - 2;
        while (s.hasNextLine()) {
            String line = s.nextLine().substring(1);
            if (line.startsWith("#"))
                break;
            for (int x = 0; x < line.length() - 1; x++)
                if (line.charAt(x) != '.')
                    blizzards.add(new Blizzard(line.charAt(x), x, y));
            height = ++y;
        }

        for (int i = 0; i < 1000; i++) // pre-calculating blizzard moves
            nextMinute();

        start = new Coord(0, -1);
        end = new Coord(width - 1, height);
        int first = BFS(start, end, 0);
        System.out.println("Part1: " + (first));

        int second = BFS(end, start, first);
        int third = BFS(start, end, second);
        System.out.println("Part2: " + third);
    }

    static int BFS(Coord start, Coord end, int depth) {
        Deque<State> frontier = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        frontier.addFirst(new State(start.x, start.y, depth));
        while (!frontier.isEmpty()) {
            State current = frontier.removeLast();
            if (current.x == end.x && current.y == end.y)
                return current.depth;
            for (State next : getSuccessors(current)) {
                if (visited.contains(next))
                    continue;
                frontier.addFirst(next);
                visited.add(next);
            }
        }
        throw new IllegalStateException("Wrong input?");
    }

    private static List<State> getSuccessors(State s) {
        List<State> res = new ArrayList<>(List.of(
                new State(s.x + 1, s.y, s.depth + 1),
                new State(s.x - 1, s.y, s.depth + 1),
                new State(s.x, s.y + 1, s.depth + 1),
                new State(s.x, s.y - 1, s.depth + 1),
                new State(s.x, s.y, s.depth + 1)));

        for (State candidate : List.copyOf(res))
            if (!isLegal(candidate))
                res.remove(candidate);
        return res;
    }

    static boolean isLegal(State s) {
        if ((s.x == start.x && s.y == start.y) || (s.x == end.x && s.y == end.y))
            return true;
        if (s.x < 0 || s.x >= width ||
                s.y < 0 || s.y >= height)
            return false;
        if (states.get(s.depth - 1).contains(new Coord(s.x, s.y)))
            return false;
        return true;
    }

    static void nextMinute() {
        Set<Coord> state = new HashSet<>();
        for (Blizzard blizzard : blizzards)
            state.add(blizzard.move(height, width));
        states.add(state);
    }
}