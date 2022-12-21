import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {
    static Map<String, Long> knownMonkeys;
    static Map<String, String[]> composite;

    public static void main(String[] args) throws IOException {
        String[] in = Files.readString(Path.of("input.txt")).split("\n");
        knownMonkeys = new HashMap<>();
        composite = new HashMap<>();

        for (String line : in) {
            String[] token = line.replaceAll(":", "").split(" ");
            if (token.length == 2)
                knownMonkeys.put(token[0], Long.parseLong(token[1]));
            else
                composite.put(token[0], new String[] { token[1], token[2], token[3] });
        }
        System.out.println("Part1 : " + solve("root"));

        String[] root = composite.get("root");
        long oldValue = solve(root[0]);
        knownMonkeys.put("humn", knownMonkeys.get("humn") + 10);
        if (solve(root[0]) != oldValue)
            binarySearch(root[0], root[2]);
        else
            binarySearch(root[2], root[0]);
        System.out.println("Part2: " + knownMonkeys.get("humn"));
    }

    //quick and dirty binary search, may get an off-by-one result on different input
    public static void binarySearch(String toEqualize, String otherMonkey) {
        long fixed = solve(otherMonkey);
        long toFind = solve(toEqualize);
        long delta = Math.abs(fixed - toFind);
        long step = delta / 10;
        while (toFind != fixed) {
            knownMonkeys.put("humn", knownMonkeys.get("humn") + step);
            long partial = solve(toEqualize);
            if (Math.abs(fixed - partial) > delta) {
                knownMonkeys.put("humn", knownMonkeys.get("humn") - step -1);
                step = -step / 2;
            } else {
                delta = Math.abs(fixed - partial);
                toFind = partial;
            }
        }
    }

    public static long solve(String name) {
        Map<String, Long> known = new HashMap<>(knownMonkeys);
        Map<String, String[]> unknown = new HashMap<>(composite);
        while (!unknown.isEmpty()) {
            for (String key : List.copyOf(unknown.keySet())) {
                String[] tokens = unknown.get(key);
                if (!(known.containsKey(tokens[0]) && known.containsKey(tokens[2])))
                    continue;
                long fst = known.get(tokens[0]);
                long snd = known.get(tokens[2]);
                long res = 0;
                switch (tokens[1]) {
                    case "+" -> res = fst + snd;
                    case "-" -> res = fst - snd;
                    case "*" -> res = fst * snd;
                    case "/" -> res = fst / snd;
                }
                known.put(key, res);
                unknown.remove(key);
            }
        }
        return known.get(name);
    }
}
