import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    // should produce both results in ~20 seconds
    public static void main(String[] args) throws IOException {
        String in = Files.readString(Path.of("input.txt")).replaceAll("[=|,|:]+", " ");
        Scanner s = new Scanner(in);
        Map<Valve, List<String>> valves = new HashMap<>();
        List<Valve> lst = new ArrayList<>();
        Valve start = null;

        while (s.hasNextLine()) {
            String[] line = s.nextLine().split("[=|,| |;]+");
            List<String> successors = new ArrayList<>();
            for (int i = 10; i < line.length; i++)
                successors.add(line[i]);
            Valve v = new Valve(line[1], Integer.parseInt(line[5]));

            if (v.flowRate != 0) //filter out useless valves
                lst.add(v);
            valves.put(v, successors);
            if (v.equals("AA"))
                start = v;
        }
        // populate each valve's internal list of adjacencies
        for (Valve v : valves.keySet()) {
            for (String next : valves.get(v))
                for (Valve toAdd : valves.keySet())
                    if (toAdd.equals(next))
                        v.addSuccessor(toAdd);
        }
        s.close();
        solve(lst, start);
    }

    public static void solve(List<Valve> valves, Valve start) {
        System.out.println("part1: " + DFS(start, valves, 0, 30));

        int max = 0;
        for (List<Valve> l : combinations(valves)) {
            List<Valve> compl = complementary(l, valves);
            int dfsA = DFS(start, l, 0, 26);
            int dfsB = DFS(start, compl, 0, 26);
            max = (dfsA + dfsB > max) ? dfsA + dfsB : max;
        }
        System.out.println("part2: " + max);
    }

    public static int DFS(Valve start, List<Valve> toVisit, int stepsToArrive, int maxDepth) {
        // this reduces the maximum depth explored, saving ~15 seconds.
        // if on another input you get the wrong answer just delete the '-2' 
        if (stepsToArrive > maxDepth - 2) 
            return 0;
        if (toVisit.isEmpty())
            return start.flowRate * (maxDepth - stepsToArrive);
        int max = 0;
        for (int i = 0; i < toVisit.size(); i++) {
            List<Valve> copy = new ArrayList<>(toVisit);
            Valve current = copy.remove(i);
            int currentCost = stepsToArrive + start.distFrom(current);
            int currentValue = DFS(current, copy, currentCost + 1, maxDepth);
            max = (currentValue > max) ? currentValue : max;
        }
        return (start.flowRate * (maxDepth - stepsToArrive)) + max;
    }

    public static List<Valve> complementary(List<Valve> first, List<Valve> global) {
        return global.stream()
                .filter(e -> !first.contains(e))
                .collect(Collectors.toList());
    }

    public static List<List<Valve>> combinations(List<Valve> arr) {
        List<List<Valve>> res = new ArrayList<>();
        int n = arr.size();
        int N = (int) Math.pow(2d, Double.valueOf(n));
        for (int i = 1; i < N; i++) {
            String code = Integer.toBinaryString(N | i).substring(1);
            List<Valve> internal = new ArrayList<>();
            for (int j = 0; j < n; j++)
                if (code.charAt(j) == '1')
                    internal.add(arr.get(j));
            res.add(internal);
        }
        return res;
    }
}