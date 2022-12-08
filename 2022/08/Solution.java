import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public record Point(int value, int i, int j) {
    }

    public enum DIR {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        List<List<Integer>> matrix = new ArrayList<>();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            matrix.add(makeLine(line));
        }

        // already counting edge trees
        int sumOfVisible = 2 * matrix.size() + 2 * (matrix.get(0).size() - 2);
        int maxScore = 0;

        for (int i = 1; i < matrix.size() - 1; i++) {
            for (int j = 1; j < matrix.get(i).size() - 1; j++) {
                Point currentPoint = new Point(matrix.get(i).get(j), i, j);

                if (isVisible(matrix, currentPoint))
                    sumOfVisible++;

                int scenicScore = scenicScore(matrix, currentPoint);
                maxScore = (scenicScore > maxScore) ? scenicScore : maxScore;
            }
        }
        System.out.println("part 1 -> " + sumOfVisible);
        System.out.println("part 2 -> " + maxScore);
    }

    private static boolean isVisible(List<List<Integer>> matrix, Point current) {
        for (DIR d : DIR.values()) {
            int[] path = pathToTheEdge(matrix, current, d);

            if (scanDirection(matrix, current, path[0], path[1], d) == Math.abs(path[1] - path[0]))
                return true;
        }
        return false;
    }

    private static int scenicScore(List<List<Integer>> matrix, Point current) {
        int total = 1;
        for (DIR d : DIR.values()) {
            int[] path = pathToTheEdge(matrix, current, d);
            List<Integer> range = range(path[0], path[1]);
            int partial = 0;

            if (d == DIR.UP || d == DIR.LEFT) // flipping the range
                Collections.reverse(range);

            for (Integer index : range) {
                partial++;

                if ((d == DIR.UP || d == DIR.DOWN) && // vertical
                        (matrix.get(index).get(current.j) >= current.value))
                    break;

                else if ((matrix.get(current.i).get(index) >= current.value)) // horizontal
                    break;
            }
            total *= partial;
        }
        return total;
    }

    // given a direction and a point, returns the start-stop tuple of indexes to
    // traverse
    private static int[] pathToTheEdge(List<List<Integer>> matrix, Point current, DIR d) {
        int[] res;
        switch (d) {
            case RIGHT ->
                res = new int[] { current.j + 1, matrix.get(0).size() };
            case LEFT ->
                res = new int[] { 0, current.j };
            case UP ->
                res = new int[] { 0, current.i };
            case DOWN ->
                res = new int[] { current.i + 1, matrix.size() };
            default -> throw new IllegalArgumentException("You should never get this :)");
        }
        return res;
    }

    // returns the amount of CONTIGUOUS smaller trees between the point and the
    // chosen edge
    private static int scanDirection(List<List<Integer>> matrix, Point current, int start, int end, DIR d) {
        int smallerTrees = 0;
        for (Integer i : range(start, end)) {
            if (d == DIR.UP || d == DIR.DOWN) {
                if (matrix.get(i).get(current.j) < current.value) {
                    smallerTrees++;
                    continue;
                }
            } else {
                if (matrix.get(current.i).get(i) < current.value) {
                    smallerTrees++;
                    continue;
                }
            }
            smallerTrees = 0;
        }
        return smallerTrees;
    }

    private static List<Integer> range(int start, int end) {
        List<Integer> res = new ArrayList<>();
        if (start > end) {
            int aux = end;
            end = start;
            start = aux;
        }
        for (int i = start; i < end; i++)
            res.add(i);
        return res;
    }

    private static List<Integer> makeLine(String s) {
        List<Integer> res = new ArrayList<>();
        for (Character c : s.toCharArray())
            res.add(c - '0');
        return res;
    }
}