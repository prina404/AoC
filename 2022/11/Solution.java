import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class Solution {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));

        List<Monkey> monkeyList = monkeyMaker(input);
        playTurns(monkeyList, 20);
        System.out.println("Part1: " + top2Activity(monkeyList));

        monkeyList = monkeyMaker(input);
        for (Monkey m : monkeyList)
            m.firstPart = false;
        playTurns(monkeyList, 10000);
        System.out.println("Part2: " + top2Activity(monkeyList));

    }

    private static void playTurns(List<Monkey> monkeyList, int rounds) {
        for (int i = 0; i < rounds; i++) {
            for (Monkey m : monkeyList) {
                for (var l : m) {
                    long[] turn = m.turn();
                    monkeyList.get((int) turn[1]).addItem(turn[0]);
                }
            }
        }
    }

    private static long top2Activity(List<Monkey> l) {
        l.sort((Comparator<Monkey>) (x, y) -> (int) (y.getActivityCount() - x.getActivityCount()));
        return l.get(0).getActivityCount() * l.get(1).getActivityCount();
    }

    // parsing input is no fun...
    public static List<Monkey> monkeyMaker(String input) {
        Scanner s = new Scanner(input);
        List<Monkey> monkeyList = new ArrayList<>();
        int count = 0;
        while (s.hasNextLine()) {
            if (s.nextLine().isEmpty())
                continue;

            List<Long> elems = new ArrayList<>();
            String elemLine = s.nextLine().replaceAll(",", "");
            Scanner itStr = new Scanner(elemLine.substring(17));
            while (itStr.hasNextLong())
                elems.add(itStr.nextLong());

            String[] opStr = s.nextLine().stripLeading().split(" ");

            Function<Long, Long> f;
            if (opStr[4].equals("+")) {
                if (opStr[5].equals("old"))
                    f = x -> x + x;
                else
                    f = x -> x + Long.parseLong(opStr[5]);
            } else {
                if (opStr[5].equals("old"))
                    f = x -> x * x;
                else
                    f = x -> x * Long.parseLong(opStr[5]);
            }

            int test = Integer.parseInt(s.nextLine().split(" ")[5]);

            int[] next = new int[] {
                    Integer.parseInt(s.nextLine().split(" ")[9]),
                    Integer.parseInt(s.nextLine().split(" ")[9])
            };

            monkeyList.add(new Monkey(count++, elems, f, test, next));
        }
        return monkeyList;
    }
}