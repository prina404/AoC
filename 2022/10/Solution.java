import java.io.File;
import java.io.IOException;
import java.util.*;

public class Solution {
    static int clock = 0;
    static int sum = 0;

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        Deque<String> queue = new LinkedList<>();
        int register = 1;

        while (s.hasNextLine()) {
            queue.addLast(s.nextLine());
            String op = queue.removeFirst();

            clock(register);

            if (op.startsWith("addx")) {
                clock(register);
                register += Integer.parseInt(op.substring(5));
            }
        }
        System.out.println(sum);
    }

    private static void clock(int register) {
        clock++;
        // part1
        if ((clock - 20) % 40 == 0)
            sum += clock * register;
        // part2
        drawPixel(register + 1, clock);
        if ((clock) % 40 == 0)
            System.out.println();
    }

    private static void drawPixel(int register, int column) {
        column = (clock) % 40;
        if (register == column || register - 1 == column || register + 1 == column)
            System.out.print("# ");
        else
            System.out.print(". ");
    }
}
