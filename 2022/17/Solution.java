import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {

    static final int CHUNKSIZE = 30; // increase if you get a wrong answer
    public static void main(String[] args) throws IOException {
        String in = Files.readString(Path.of("input.txt"));
        solve(in.strip(), 10000); // increase if you get a wrong answer
    }

    // really bad and unreadable code...
    public static int solve(String wind, int iterations) {
        List<Figure> l = new ArrayList<>();

        int stringIndex = 0;
        int tallestPoint = 0;

        int prevPatternIndex = 0;
        int currPatternIndex = 0;
        int prevHeight = 0;
        int patternSize = 0;
        int heightDiff = 0;
        for (int i = 0; i < iterations; i++) {
            if (l.size() == CHUNKSIZE)
                l = l.subList(CHUNKSIZE/2, l.size());

            Figure f = new Figure(i % 5, tallestPoint);
            l.add(f);
            boolean grounded = false;
            while (!grounded) {
                if (stringIndex % wind.length() == 0 && i > 0) {
                    if (prevPatternIndex > 0) { 
                        prevPatternIndex = currPatternIndex;
                        currPatternIndex = i;
                        patternSize = i - prevPatternIndex;
                        heightDiff = tallestPoint - prevHeight;
                        prevHeight = tallestPoint;
                    } else{
                        prevPatternIndex = i;
                        prevHeight = tallestPoint;
                    }
                }
                int offset = (wind.charAt(stringIndex++ % wind.length()) == '>') ? 1 : -1;
                f.moveHorizontal(offset);
                for (Figure figure : l)
                    if (f.collides(figure))
                        f.moveHorizontal(-offset);

                f.moveVertical(-1);
                for (Figure figure : l) {
                    if (f.collides(figure)) {
                        f.moveVertical(+1);
                        grounded = true;
                        tallestPoint = (f.tallestPoint() > tallestPoint) ? f.tallestPoint() : tallestPoint;
                        break;
                    }
                }
            }
            if (i == 2021)
                System.out.println("Part1 : " + tallestPoint);
        }
        if (iterations < 2022)
            return tallestPoint;
        
        long dividend = 1_000_000_000_000L;
        long res = Long.divideUnsigned(dividend, patternSize) * heightDiff;

        System.out.println("Part2: " + (res + solve(wind, (int) (dividend % patternSize))));
        return 0;
    }
}