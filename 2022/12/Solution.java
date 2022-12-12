import java.io.File;
import java.io.IOException;
import java.util.*;

public class Solution {
    static boolean part1 = true;

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        List<List<Character>> matrix = new ArrayList<>();
        while (s.hasNextLine()) {
            matrix.add(new ArrayList<>());
            String line = s.nextLine();
            for (Character c : line.toCharArray())
                matrix.get(matrix.size() - 1).add(c);
        }

        System.out.println("part 1:  " + part1(matrix));

        part1 = false;
        System.out.println("part 2: " + part2(matrix));
    }

    public static int part1(List<List<Character>> matrix) {
        Point[] p = fromOffset(matrix, 0);
        List<Point> path = BFS(p[0], p[1], matrix);
        if (path != null)
            return path.size();
        return 0;
    }

    // for the second part I execute a BFS for each new 'a' encountered: extremely
    // inefficient
    public static int part2(List<List<Character>> matrix) {
        int min = Integer.MAX_VALUE;
        int res = 0;
        for (int i = 0; i < 1000; i++) { // hardcoded value, should refactor
            Point[] p = fromOffset(matrix, i);
            List<Point> path = BFS(p[0], p[1], matrix);
            if (path != null)
                res = path.size();
            if (res < min)
                min = res;
        }
        return min;
    }

    // returns start and goal points
    public static Point[] fromOffset(List<List<Character>> matrix, int startOffset) {
        int aCount = 0;
        Point start = null;
        Point end = null;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                if (part1 && matrix.get(i).get(j) == 'S')
                    start = new Point(j, i, 'a', null);

                else if (matrix.get(i).get(j) == 'a')
                    if (aCount++ == startOffset)
                        start = new Point(j, i, 'a', null);

                if (matrix.get(i).get(j) == 'E')
                    end = new Point(j, i, 'E', null);
            }
        }
        return new Point[] { start, end };
    }

    public static List<Point> BFS(Point start, Point end, List<List<Character>> matrix) {
        Set<Point> EXL = new HashSet<>();
        EXL.add(start);

        Deque<Point> frontier = new LinkedList<>();
        frontier.addLast(start);

        while (!frontier.isEmpty()) {
            Point current = frontier.removeFirst();
            if (current.equals(end))
                return path(current, EXL, start);
            List<Point> neighbours = getNeighbours(current, matrix);
            for (var p : neighbours) {
                if (!EXL.contains(p)) {
                    EXL.add(p);
                    frontier.addLast(p);
                }
            }
        }
        return null;
    }

    // construct the path_to_goal starting from the goal node
    public static List<Point> path(Point p, Set<Point> exl, Point start) {
        List<Point> res = new ArrayList<>();
        while (!p.equals(start)) {
            res.add(p);
            p = p.parent;
        }
        return res;
    }

    public static boolean isLegal(int x, int y, List<List<Character>> m) {
        return x >= 0 && y >= 0 && x < m.get(0).size() && y < m.size();
    }

    // this works but could be done better
    public static List<Point> getNeighbours(Point p, List<List<Character>> matrix) {
        List<Point> res = new ArrayList<>();
        if (isLegal(p.x + 1, p.y, matrix))
            res.add(new Point(p.x + 1, p.y, matrix.get(p.y).get(p.x + 1), p));
        if (isLegal(p.x - 1, p.y, matrix))
            res.add(new Point(p.x - 1, p.y, matrix.get(p.y).get(p.x - 1), p));
        if (isLegal(p.x, p.y + 1, matrix))
            res.add(new Point(p.x, p.y + 1, matrix.get(p.y + 1).get(p.x), p));
        if (isLegal(p.x, p.y - 1, matrix))
            res.add(new Point(p.x, p.y - 1, matrix.get(p.y - 1).get(p.x), p));
        for (Point point : List.copyOf(res))
            if (point.value > p.value + 1)
                res.remove(point);
        return res;
    }
}
