import java.util.List;

public abstract class Entry implements Comparable<Entry> {
    protected int value;
    protected List<Entry> elems;

    @Override
    public int compareTo(Entry snd) {
        if (this instanceof AtomicEntry && snd instanceof AtomicEntry)
            return this.value - snd.value;
        if (this instanceof AtomicEntry)
            return new ListEntry(List.of(this)).compareTo(snd);
        if (snd instanceof AtomicEntry)
            return this.compareTo(new ListEntry(List.of(snd)));

        int min = Math.min(this.elems.size(), snd.elems.size());
        for (int i = 0; i < min; i++)
            if (this.elems.get(i).compareTo(snd.elems.get(i)) != 0)
                return this.elems.get(i).compareTo(snd.elems.get(i));

        if (this.elems.size() == snd.elems.size())
            return 0;
        return (this.elems.size() < snd.elems.size()) ? -1 : 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entry))
            return false;
        Entry e = (Entry) obj;
        return toString().equals(e.toString());
    }
}