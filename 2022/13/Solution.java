import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        List<Entry> total = new ArrayList<>();

        while (s.hasNextLine()) {
            total.add(parseLine(toCharList(s.nextLine())).get(0));
            total.add(parseLine(toCharList(s.nextLine())).get(0));
            s.nextLine();
        }
        System.out.println("Part1: " + part1(total));
        System.out.println("Parts: " + part2(total));
    }

    public static int part1(List<Entry> total) {
        int sum = 0;
        for (int i = 0; i < total.size() - 1; i += 2)
            if (total.get(i).compareTo(total.get(i + 1)) <= 0)
                sum += (i / 2) + 1;
        return sum;
    }

    public static int part2(List<Entry> total) {
        Entry sep1 = parseLine(toCharList("[[2]]")).get(0);
        Entry sep2 = parseLine(toCharList("[[6]]")).get(0);
        total.add(sep1);
        total.add(sep2);
        total.sort((x, y) -> x.compareTo(y));
        int mul = 1;
        for (Entry e : total)
            if (e.equals(sep1) || e.equals(sep2))
                mul *= total.indexOf(e) + 1;
        return mul;
    }

    public static List<Character> toCharList(String s) {
        List<Character> ln = new ArrayList<>();
        for (Character c : s.toCharArray())
            ln.add(c);
        return ln;
    }

    public static List<Entry> parseLine(List<Character> line) {
        List<Entry> res = new ArrayList<>();
        List<Integer> acc = new ArrayList<>();

        while (line.size() > 0) {
            char SUT = line.remove(0);
            switch (SUT) {
                case '[':
                    if (!acc.isEmpty())
                        res.add(new AtomicEntry(lstToInt(acc)));
                    acc.clear();
                    var x = parseLine(line.subList(0, line.size()));
                    ListEntry l = new ListEntry(x);
                    res.add(l);
                    break;
                case ']':
                    if (!acc.isEmpty())
                        res.add(new AtomicEntry(lstToInt(acc)));
                    return res;
                case ',':
                    if (!acc.isEmpty())
                        res.add(new AtomicEntry(lstToInt(acc)));
                    acc.clear();
                    break;
                default:
                    acc.add(Integer.parseInt(Character.toString(SUT)));
            }
        }
        return res;
    }

    // needed in case of multi-digit numbers
    public static int lstToInt(List<Integer> lst) {
        int total = 0;
        for (Integer i : lst)
            total = 10 * total + i;
        return total;
    }
}