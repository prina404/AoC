import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class Monkey implements Iterable<Long> {

    public final int nth;
    private final List<Long> items;
    private Function<Long, Long> op;
    private int test;
    private final int[] next;
    private long inspectionCounter = 0;
    private final List<Long> modList = List.of(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L);
    public boolean firstPart = true;

    public Monkey(int nth, List<Long> items, Function<Long, Long> f, int test, int[] next) {
        this.nth = nth;
        this.items = items;
        op = f;
        this.test = test;
        this.next = next;
    }

    public long[] turn() {
        inspectionCounter++;
        Long item = items.remove(0);
        item = op.apply(item);
        
        if (firstPart)
            item = Math.floorDiv(item, 3);
        item = item % modProd(); // so many headaches for this line :(
        if (item % test == 0) {
            return new long[] { item, next[0] };
        }
        return new long[] { item, next[1] };
    }

    private long modProd() {
        long i = 1;
        for (Long mod : modList)
            i *= mod;
        return i;
    }

    public long getActivityCount() {
        return inspectionCounter;
    }

    public void addItem(long i) {
        items.add(i);
    }

    @Override
    public Iterator<Long> iterator() {
        return List.copyOf(items).iterator();
    }
}