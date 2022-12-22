import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {
    static List<List<Character>> board = new ArrayList<>();
    static boolean part2 = false;

    public static void main(String[] args) throws FileNotFoundException {
        int inputSize = 150;
        Scanner s = new Scanner(new File("input.txt"));
        String line;
        while (!(line = s.nextLine()).isEmpty()) {
            board.add(new ArrayList<>());
            for (int i = 0; i < inputSize; i++)
                board.get(board.size() - 1).add((i >= line.length()) ? ' ' : line.charAt(i));
        }

        var cmd = parseCommands(s.nextLine());

        System.out.println("Part1: " + solve(cmd));
        part2 = true;
        System.out.println("Part2: " + solve(cmd));
    }

    public static int solve(List<String> cmd) {
        int[] pos = new int[] { 50, 0, 0 };

        for (int i = 0; i < cmd.size(); i++) {
            if (i % 2 == 0)
                for (int j = 0; j < Integer.parseInt(cmd.get(i)); j++)
                    pos = moveOne(pos);
            else
                pos[2] = (cmd.get(i).equals("R")) ? (pos[2] + 1) % 4 : Math.floorMod(pos[2] - 1, 4);
        }
        return 1000 * (pos[1] + 1) + 4 * (pos[0] + 1) + pos[2];
    }

    public static int[] moveOne(int[] pos) {
        int[] old = Arrays.copyOf(pos, 3);
        switch (pos[2]) {
            case 0 -> pos[0] += 1;
            case 1 -> pos[1] += 1;
            case 2 -> pos[0] -= 1;
            case 3 -> pos[1] -= 1;
        }
        if (pos[1] >= board.size() || pos[1] < 0)
            pos = wrapVertical(pos, part2);
        if (pos[0] >= board.get(pos[1]).size() || pos[0] < 0)
            pos = wrapHorizontal(pos, part2);
        if (board.get(pos[1]).get(pos[0]) == ' ')
            if (pos[2] == 0 || pos[2] == 2)
                pos = wrapHorizontal(pos, part2);
            else
                pos = wrapVertical(pos, part2);
        if (board.get(pos[1]).get(pos[0]) == '#')
            return old;
        return pos;
    }

    // bad, unreadable, hardcoded solution :/
    private static int[] wrapHorizontal(int[] pos, boolean shift) {
        int i = 0;
        if (pos[2] == 0) {
            if (shift) {
                if (pos[1] < 50)
                    return wrapHorizontal(new int[] { pos[0], 149 - pos[1], 2 }, false);
                else if (pos[1] < 100)
                    return wrapVertical(new int[] { 100 + (pos[1] - 50), pos[1], 3 }, false);
                else if (pos[1] < 150)
                    return wrapHorizontal(new int[] { pos[0], (149 - pos[1]), 2 }, false);
                else if (pos[1] < 200)
                    return wrapVertical(new int[] { 50 + (pos[1] - 150), pos[1], 3 }, false);
            }
            while (board.get(pos[1]).get(i) != '.' && board.get(pos[1]).get(i) != '#')
                i++;
        } else {
            if (shift) {
                if (pos[1] < 50)
                    return wrapHorizontal(new int[] { pos[0], 149 - pos[1], 0 }, false);
                else if (pos[1] < 100)
                    return wrapVertical(new int[] { 0 + pos[1] - 50, pos[1], 1 }, false);
                else if (pos[1] < 150)
                    return wrapHorizontal(new int[] { pos[0], 149 - pos[1], 0 }, false);
                else if (pos[1] < 200)
                    return wrapVertical(new int[] { 50 + (pos[1] - 150), pos[1], 1 }, false);
            }
            i = board.get(0).size() - 1;
            while (board.get(pos[1]).get(i) != '.' && board.get(pos[1]).get(i) != '#')
                i--;
        }
        return new int[] { i, pos[1], pos[2] };
    }

    public static int[] wrapVertical(int[] pos, boolean shift) {
        int i = 0;
        if (pos[2] == 1) {
            if (shift) {
                if (pos[0] < 50)
                    return wrapVertical(new int[] { 100 + pos[0], pos[1], 1 }, false);
                else if (pos[0] < 100)
                    return wrapHorizontal(new int[] { pos[0], 150 + (pos[0] - 50), 2 }, false);
                else if (pos[0] < 150)
                    return wrapHorizontal(new int[] { pos[0], 50 + (pos[0] - 100), 2 }, false);
            }
            while (board.get(i).get(pos[0]) != '.' && board.get(i).get(pos[0]) != '#')
                i++;
        } else {
            if (shift) {
                if (pos[0] < 50)
                    return wrapHorizontal(new int[] { pos[0], 50 + pos[0], 0 }, false);
                else if (pos[0] < 100)
                    return wrapHorizontal(new int[] { pos[0], 150 + (pos[0] - 50), 0 }, false);
                else if (pos[0] < 150)
                    return wrapVertical(new int[] { pos[0] - 100, pos[1], 3 }, false);
            }
            i = board.size() - 1;
            while (board.get(i).get(pos[0]) != '.' && board.get(i).get(pos[0]) != '#')
                i--;
        }
        return new int[] { pos[0], i, pos[2] };
    }

    public static List<String> parseCommands(String commands) {
        List<String> cmd = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < commands.length(); i++) {
            if (Character.isDigit(commands.charAt(i))) {
                temp += commands.charAt(i);
                continue;
            }
            if (!temp.isEmpty()) {
                cmd.add(temp);
                temp = "";
            }
            cmd.add(String.valueOf(commands.charAt(i)));
        }
        cmd.add(temp);
        return cmd;
    }
}
