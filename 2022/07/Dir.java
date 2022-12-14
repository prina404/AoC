import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Dir extends Entry implements Iterable<Entry> {
    private final Set<Entry> contents = new HashSet<>();
    private int size = 0;

    public Dir(String name) {
        super(name);
    }

    public void addElem(Entry f) {
        contents.add(f);
    }

    public Dir findDir(String[] path) {
        if (path.length == 0)
            return this;
        Dir aux = (Dir) getEntry(path[0]);
        if (path.length == 1)
            return aux;
        return aux.findDir(Arrays.copyOfRange(path, 1, path.length));

    }

    public MyFile findFile(String[] path) {
        if (path.length == 1)
            return (MyFile) getEntry(path[0]);
        Dir aux = (Dir) getEntry(path[0]);
        return aux.findFile(Arrays.copyOfRange(path, 1, path.length));
    }

    private Entry getEntry(String nome) {
        for (Entry e : contents)
            if (e.getName().equals(nome))
                return e;
        throw new IllegalAccessError("entry mancante, con nome " + nome);
    }

    @Override
    public int getSize() {
        if (size > 0)
            return size;
        for (Entry e : contents)
            size += e.getSize();
        return size;
    }

    @Override
    public Iterator<Entry> iterator() {
        return contents.iterator();
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + contents.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dir)
            return hashCode() == obj.hashCode();
        return false;
    }
}
