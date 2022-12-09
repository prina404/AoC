import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Solution {
    static List<Point> knots;

    static Set<Point> visited;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));
        int part1 = solve(2, input);
        int part2 = solve(10, input);
        System.out.printf("part1: %d\npart2 :%d\n", part1, part2);
    }

    public static int solve(int howManyKnots, String in) {
        Scanner s = new Scanner(in);
        knots = new ArrayList<>();
        visited = new HashSet<>();

        for (int i = 0; i < howManyKnots; i++)
            knots.add(new Point(0, 0));

        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(" ");
            String cmd = line[0];
            int steps = Integer.parseInt(line[1]);

            for (int i = 0; i < steps; i++) {
                move(cmd, knots.get(0));
                // update trailing nodes' position.
                for (int j = 0; j < knots.size() - 1; j++)
                    adjust(knots.get(j), knots.get(j + 1));

                int x = knots.get(howManyKnots - 1).x;
                int y = knots.get(howManyKnots - 1).y;
                visited.add(new Point(x, y));
                // print(20)
            }
        }
        s.close();
        return visited.size();
    }

    // manhattan distance
    public static int dist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static void move(String cmd, Point a) {
        switch (cmd) {
            case ("D") -> a.y--;
            case ("U") -> a.y++;
            case ("R") -> a.x++;
            case ("L") -> a.x--;
        }
    }

    public static void adjust(Point a, Point b) {
        if (dist(a, b) == 2)
            correction(a, b, 2);
        else if (dist(a, b) > 2) {
            correction(a, b, 1);
            correction(a, b, 2);
        }
    }

    /*
     * this is a hacky solution due to the choice of using manhattan distance:
     * if a knot is on a diagonal square its distance is two, 
     * and I need to avoid moving only in this specific case
     * 
     * A better solution would be to use chebyshev distance, will I ever refactor this?
     */

    public static void correction(Point a, Point b, int delta) {
        if (a.x - b.x == delta)
            b.x++;
        else if (a.x - b.x == -delta)
            b.x--;
        if (a.y - b.y == delta)
            b.y++;
        else if (a.y - b.y == -delta)
            b.y--;
    }

    public static void print(int boardSize) {
        for (int i = boardSize - 1; i >= 0; i--) {
            for (int j = 0; j < boardSize; j++) {
                Point p = new Point(j, i);
                if (knots.contains(p))
                    System.out.print(knots.indexOf(p));
                else
                    System.out.print("*");
            }
            System.out.println();
        }
    }
}