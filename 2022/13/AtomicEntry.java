
public class AtomicEntry extends Entry {

    public AtomicEntry(int atom) {
        super.value = atom;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}