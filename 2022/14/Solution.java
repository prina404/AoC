import java.io.File;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static record Point(int x, int y) {
    }

    static final int width = 400;

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));

        List<List<Character>> matrix = new ArrayList<>();
        for (int i = 0; i < width / 2; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < width; j++)
                matrix.get(i).add('.');
        }

        int lowestPoint = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] coords = line.split(" -> ");
            List<Point> points = new ArrayList<>();
            for (String cord : coords)
                points.add(new Point(
                        Integer.parseInt(cord.split(",")[0]) - (500 - width / 2),
                        Integer.parseInt(cord.split(",")[1])));

            for (int i = 0; i < coords.length - 1; i++) {
                Point from = points.get(i);
                Point to = points.get(i + 1);
                for (Integer j : range(from.y, to.y)) {
                    for (Integer j2 : range(from.x, to.x)) {
                        matrix.get(j).set(j2, '#');
                        if (j > lowestPoint)
                            lowestPoint = j;
                    }
                }
            }
        }

        Point sandSource = new Point(width / 2, 0);
        System.out.println("Part1: " + part1(sandSource, matrix, lowestPoint));

        for (int i = 0; i < width; i++)
            matrix.get(lowestPoint + 2).set(i, '#');

        System.out.println("Part2: " + part2(sandSource, matrix));

    }

    public static int part1(Point drop, List<List<Character>> matrix, int lowestPoint) {
        while (true) {
            Point fall = new Point(drop.x, drop.y);
            while (hasMove(fall, matrix)) {
                fall = move(fall, matrix);
                if (fall.y > lowestPoint)
                    return countZeroes(matrix) - 1;
            }
        }
    }

    public static int part2(Point drop, List<List<Character>> matrix) {
        while (true) {
            Point fall = new Point(drop.x, drop.y);
            if (!hasMove(fall, matrix))
                return countZeroes(matrix) + 1;
            while (hasMove(fall, matrix)) {
                fall = move(fall, matrix);
            }
        }
    }

    public static int countZeroes(List<List<Character>> matrix) {
        int count = 0;
        for (int i = 0; i < matrix.size(); i++)
            for (int j = 0; j < matrix.get(i).size(); j++)
                if (matrix.get(i).get(j).equals('o'))
                    count++;
        return count;
    }

    public static Point move(Point p, List<List<Character>> matrix) {
        matrix.get(p.y).set(p.x, '.');
        if (matrix.get(p.y + 1).get(p.x) == '.') {
            matrix.get(p.y + 1).set(p.x, 'o');
            return new Point(p.x, p.y + 1);
        }

        if (matrix.get(p.y + 1).get(p.x - 1) == '.') {
            matrix.get(p.y + 1).set(p.x - 1, 'o');
            return new Point(p.x - 1, p.y + 1);
        }

        if (matrix.get(p.y + 1).get(p.x + 1) == '.') {
            matrix.get(p.y + 1).set(p.x + 1, 'o');
            return new Point(p.x + 1, p.y + 1);
        }
        return null;
    }

    public static boolean hasMove(Point p, List<List<Character>> matrix) {
        if (matrix.get(p.y + 1).get(p.x) == '.')
            return true;
        if (matrix.get(p.y + 1).get(p.x - 1) == '.')
            return true;
        if (matrix.get(p.y + 1).get(p.x + 1) == '.')
            return true;
        return false;
    }

    private static List<Integer> range(int start, int end) {
        List<Integer> res = new ArrayList<>();
        if (start > end) {
            int aux = end;
            end = start;
            start = aux;
        }
        for (int i = start; i <= end; i++)
            res.add(i);
        return res;
    }
}