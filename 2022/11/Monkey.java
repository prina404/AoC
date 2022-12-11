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
    private final List<Integer> modList = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23);
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
        for (Integer long1 : modList)
            i *= Integer.toUnsignedLong(long1);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monkey ").append(nth).append(": ");
        for (Long i : items)
            sb.append(i).append(", ");
        return sb.toString();
    }

}