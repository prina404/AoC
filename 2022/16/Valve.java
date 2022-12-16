import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Valve {
    String name;
    List<Valve> successors = new ArrayList<>();
    int flowRate;
    Map<Valve, Integer> distances = new HashMap<>();

    public Valve(String name, int flowRate) {
        this.name = name;
        this.flowRate = flowRate;
        if (flowRate == 0)
            return;
    }

    public void addSuccessor(Valve next) {
        successors.add(next);
    }

    // very basic caching of distances between valves
    public int distFrom(Valve other) {
        if (!distances.containsKey(other))
            distances.put(other, distFrom(other, new HashSet<>()));

        return distances.get(other);
    }

    public int distFrom(Valve other, Set<Valve> explored) {
        if (explored.contains(this))
            return 100; // hardcoded not-too-small value to avoid overflow
        explored.add(this);
        for (Valve v : successors)
            if (v.equals(other))
                return 1;
        int min = Integer.MAX_VALUE;
        for (Valve v : successors) {
            int dist = v.distFrom(other, new HashSet<>(explored));
            if (dist < min)
                min = dist;
        }
        return 1 + min;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Valve))
            return false;
        Valve o = (Valve) obj;
        return name.equals(o.name);
    }

    public boolean equals(String s) {
        return name.equals(s);
    }

}
