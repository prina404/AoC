import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

public class Solution {
    // code is bad and unreadable
    // but runs in < 1 sec 
    public record Cost(int ore, int clay, int obs, int geo) {}

    public record Blueprint(Cost oreBot, Cost clayBot, Cost obsBot, Cost geoBot) {
        public int maxOre() {
            return Math.max(oreBot.ore, Math.max(clayBot.ore, Math.max(obsBot.ore, geoBot.ore)));
        }
    }

    public record State(Cost bots, Cost resources, int depth) {}

    static List<Blueprint> bp = new ArrayList<>();
    static Set<Integer> EXL = new HashSet<>();
    static int bestBranch = 0;
    static int maxDepth = 0;
    static int minGeodeHeight = 24;

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
            minGeodeHeight = maxDepth;
            sum += (i + 1) * DFS(start, bp.get(i));
            EXL.clear();
            bestBranch = 0;
        }
        System.out.println("Part 1: " + sum);
        maxDepth = 32;
        sum = 1;
        for (int i = 0; i < 3; i++) {
            minGeodeHeight = maxDepth;
            sum *= DFS(start, bp.get(i));
            EXL.clear();
            bestBranch = 0;
        }
        System.out.println("Part2: " + sum);
    }

    public static int DFS(State s, Blueprint b) {
        if (s.depth == maxDepth)
            return s.resources.geo;
        if (s.bots.geo * (maxDepth - s.depth) + sum(maxDepth - s.depth) + s.resources.geo <= bestBranch)
            return 0;
        if (s.resources.geo == 1 && s.depth > minGeodeHeight)
            return 0;
        if (s.resources.geo == 1 && s.depth < minGeodeHeight)
            minGeodeHeight = s.depth;
        if (EXL.contains(StateID(s)))
            return 0;
        EXL.add(StateID(s));

        int max = 0;
        for (State next : getSuccessors(s, b))
            max = Math.max(max, DFS(next, b));

        bestBranch = Math.max(max, bestBranch);
        return max;
    }

    private static int StateID(State s) {
        return s.resources.ore + 
                (s.resources.clay<<7)+
                (s.resources.obs<<14)+
                (s.bots.geo<<21)+
                (s.bots.ore<<28)+ 
                (s.bots.clay<<35)+ 
                (s.bots.obs<<42)+
                (s.depth<<49); 
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
        State next;
        
        if ((next = nextGeoBot(s, b)) != null)
            res.add(next);
        if ((next = nextObsBot(s, b)) != null)
            res.add(next);
        if ((next = nextClayBot(s, b)) != null)
            res.add(next);
        if ((next = nextOreBot(s, b)) != null)
            res.add(next);
        res.add(nextMinute(s));
        return res;
    }

    private static State nextOreBot(State s, Blueprint b) {
        if (s.bots.ore > b.maxOre() || s.depth > maxDepth - 17)
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
        if (s.bots.clay >= b.obsBot.clay || s.depth > maxDepth - 9)
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
        if (s.bots.clay == 0 || s.bots.obs >= b.geoBot.obs || s.depth > maxDepth - 4)
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
