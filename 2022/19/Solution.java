import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

public class Solution {
    // code is bad, unefficient and unreadable
    // needs some heavy refactoring
    public record Cost(int ore, int clay, int obs, int geo) {}
    public record Blueprint(Cost oreBot, Cost clayBot, Cost obsBot, Cost geoBot) {}
    public record State(Cost bots, Cost resources, int depth) {}

    static List<Blueprint> bp = new ArrayList<>();
    static Set<State> EXL = new HashSet<>();
    static int bestBranch = 0;
    static int maxDepth = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("input.txt"));
        Function<String, Integer> f = x -> Integer.parseInt(x);
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(" ");
            bp.add(new Blueprint(
                    new Cost(f.apply(line[6]), 0, 0, 0),
                    new Cost(f.apply(line[12]), 0, 0, 0),
                    new Cost(f.apply(line[18]), f.apply(line[21]), 0, 0),
                    new Cost(f.apply(line[27]), 0, f.apply(line[30]), 0)));
        }

        State start = new State(new Cost(1, 0, 0, 0), new Cost(0, 0, 0, 0), 0);
        int sum = 0;
        maxDepth = 24;
        for (int i = 0; i < 30; i++) {
            int res = DFS(start, bp.get(i));
            sum += (i + 1) * res;
            System.out.println("BP: " + (i + 1) + " Has score: " + ((i + 1) * res) + " states visited: " + EXL.size());
            EXL.clear();
            bestBranch = 0;
        }
        System.out.println("Part 1: " + sum);
        maxDepth = 32;
        sum = 1;
        for (int i = 0; i < 3; i++) {
            int res = DFS(start, bp.get(i));
            sum *= res;
            System.out.println("BP: " + (i + 1) + " Has score: " + (res) + " states visited: " + EXL.size());
            EXL.clear();
            bestBranch = 0;
        }
        System.out.println("Part2: " + sum);
    }

    public static int DFS(State s, Blueprint b) {
        if (s.depth == maxDepth)
            return s.resources.geo;
        if (s.depth > maxDepth)
            return 0;

        if (bestBranch > 0) 
            if (s.bots.geo * (maxDepth - s.depth) + sum(maxDepth - s.depth) + s.resources.geo <= bestBranch)
                return 0;
        
        if (EXL.contains(s))
            return 0;
        EXL.add(s);
        int max = 0;
        for (State next : getSuccessors(s, b)) {
            int res = DFS(next, b);
            max = (res > max) ? res : max;
        }
        bestBranch = (max > bestBranch) ? max : bestBranch;
        return max;
    }

    private static int sum(int n) {
        int sum = 0;
        while (n > 1)
            sum += n--;
        return sum;
    }

    private static List<State> getSuccessors(State s, Blueprint b) {
        if (s.depth >= maxDepth)
            return Collections.emptyList();
        List<State> res = new ArrayList<>();

        State nextOre = nextOreBot(s, b);
        State nextClay = nextClayBot(s, b);
        State nextObs = nextObsBot(s, b);
        State nextGeo = nextGeoBot(s, b);
        if (nextGeo != null)
            res.add(nextGeo);
        if (nextObs != null)
            res.add(nextObs);
        if (nextClay != null)
            res.add(nextClay);
        if (nextOre != null)
            res.add(nextOre);
        res.add(nextMinute(s));
        return res;
    }

    private static State nextOreBot(State s, Blueprint b) {
        if (s.bots.ore > 4)
            return null;
        if (s.resources.ore >= b.oreBot.ore)
            return new State(new Cost(s.bots.ore + 1, s.bots.clay, s.bots.obs, s.bots.geo),
                    new Cost(s.resources.ore + s.bots.ore - b.oreBot.ore,
                            s.resources.clay + s.bots.clay,
                            s.resources.obs + s.bots.obs,
                            s.resources.geo + s.bots.geo),
                    s.depth + 1);
        return nextOreBot(nextMinute(s), b);
    }

    private static State nextClayBot(State s, Blueprint b) {
        if (s.bots.clay > 20 || s.depth > maxDepth - 4)
            return null;
        if (s.resources.ore >= b.clayBot.ore)
            return new State(new Cost(s.bots.ore, s.bots.clay + 1, s.bots.obs, s.bots.geo),
                    new Cost(s.resources.ore + s.bots.ore - b.clayBot.ore,
                            s.resources.clay + s.bots.clay,
                            s.resources.obs + s.bots.obs,
                            s.resources.geo + s.bots.geo),
                    s.depth + 1);
        return nextOreBot(nextMinute(s), b);
    }

    private static State nextObsBot(State s, Blueprint b) {
        if (s.bots.clay == 0 || s.bots.obs > 18 || s.depth > maxDepth - 3)
            return null;
        if (s.resources.ore >= b.obsBot.ore && s.resources.clay >= b.obsBot.clay)
            return new State(new Cost(s.bots.ore, s.bots.clay, s.bots.obs + 1, s.bots.geo),
                    new Cost(s.resources.ore + s.bots.ore - b.obsBot.ore,
                            s.resources.clay + s.bots.clay - b.obsBot.clay,
                            s.resources.obs + s.bots.obs,
                            s.resources.geo + s.bots.geo),
                    s.depth + 1);
        return nextOreBot(nextMinute(s), b);
    }

    private static State nextGeoBot(State s, Blueprint b) {
        if (s.bots.obs == 0 || s.depth > maxDepth - 2)
            return null;
        if (s.resources.ore >= b.geoBot.ore && s.resources.obs >= b.geoBot.obs)
            return new State(new Cost(s.bots.ore, s.bots.clay, s.bots.obs, s.bots.geo + 1),
                    new Cost(s.resources.ore + s.bots.ore - b.geoBot.ore,
                            s.resources.clay + s.bots.clay,
                            s.resources.obs + s.bots.obs - b.geoBot.obs,
                            s.resources.geo + s.bots.geo),
                    s.depth + 1);
        return nextOreBot(nextMinute(s), b);
    }

    private static State nextMinute(State s) {
        return new State(s.bots,
                new Cost(s.resources.ore + s.bots.ore,
                        s.resources.clay + s.bots.clay,
                        s.resources.obs + s.bots.obs,
                        s.resources.geo + s.bots.geo),
                s.depth + 1);
    }
}
