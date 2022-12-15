import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {

    public static record Point(int x, int y) {}

    // basically the same as Point, defined for clarity
    public static record Range(int start, int stop) {}

    static int min_X_coord = 0;
    static int max_X_coord = 0;

    public static void main(String[] args) throws IOException {
        String in = Files.readString(Path.of("input.txt")).replaceAll("[=|,|:]+", " ");
        Scanner s = new Scanner(in);
        Map<Point, Point> sensors = new HashMap<>();
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split("[ |  ]+");
            Point sensor = new Point(Integer.parseInt(line[3]), Integer.parseInt(line[5]));
            Point beacon = new Point(Integer.parseInt(line[11]), Integer.parseInt(line[13]));
            sensors.put(sensor, beacon);
        }
        s.close();

        System.out.println("part1 : " + solve(2000000, sensors));

        int height = 0;
        long x = 0;
        while ((x = solve2(height, sensors)) < 0)
            height++;
        System.out.println("part2 : " + ((x * 4000000) + height));

    }

    static int solve(int height, Map<Point, Point> sensors) {
        List<Range> atHeight = rangesAtHeight(height, sensors);
        int count = 0;

        for (int i = min_X_coord; i < max_X_coord; i++) {
            boolean hasSignal = false;
            for (Range rn : atHeight) {
                if (i >= rn.start && i <= rn.stop && !hasSignal) {
                    count++;
                    hasSignal = true;
                }
            }
        }
        return count;
    }

    static int solve2(int height, Map<Point, Point> sensors) {
        List<Range> atHeight = rangesAtHeight(height, sensors);

        for (int i = 0; i < 4000000; i++) {
            boolean hasSignal = false;
            for (Range rn : atHeight) {
                if (i >= rn.start && i <= rn.stop && !hasSignal) {
                    hasSignal = true;
                    i = rn.stop;
                }
            }
            if (!hasSignal)
                return i;
        }
        return -1;
    }

    // list of signal range for each sensor at a given height
    public static List<Range> rangesAtHeight(int height, Map<Point, Point> sensors) {
        List<Range> atHeight = new ArrayList<>();
        for (Point p : sensors.keySet()) {
            Range r = rangeAt(p, sensors.get(p), height);
            if (r != null)
                atHeight.add(r);
        }
        return atHeight;
    }

    // given the height calculate the range of occupied x coordinates by sensor's
    // signal
    public static Range rangeAt(Point sensor, Point beacon, int line) {
        int max_extension = ManhattanDistance(sensor, beacon);
        int lineDist = Math.abs(line - sensor.y);
        if (lineDist > max_extension)
            return null;
        int occupation = max_extension - lineDist;
        Range res = new Range(sensor.x - occupation, sensor.x + occupation);
        max_X_coord = (res.stop > max_X_coord) ? res.stop : max_X_coord;
        min_X_coord = (res.start < min_X_coord) ? res.start : min_X_coord;
        return res;
    }

    public static int ManhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}