import java.util.Comparator;
import java.util.List;

public abstract class Entry implements Comparator<Entry> , Comparable<Entry>{
    int value;
    List<Entry> elems;

    @Override
    public int compare(Entry fst, Entry snd) {
        if (fst instanceof AtomicEntry && snd instanceof AtomicEntry)
            return fst.value - snd.value;
        if (fst instanceof AtomicEntry)
            return compare(new ListEntry(List.of(fst)), snd);
        if (snd instanceof AtomicEntry)
            return compare(fst, new ListEntry(List.of(snd)));

        int min = Math.min(fst.elems.size(), snd.elems.size());
        for (int i = 0; i < min; i++) 
            if (compare(fst.elems.get(i), snd.elems.get(i)) != 0)
                return compare(fst.elems.get(i), snd.elems.get(i));
        if (fst.elems.size() < snd.elems.size())
            return -1;
        if (snd.elems.size() < fst.elems.size())
            return 1;
        return 0;
    }

    @Override
    public int compareTo(Entry o) {
        return compare(this, o);
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entry))
            return false;
        Entry e = (Entry) obj;
        return toString().equals(e.toString());
    }
}