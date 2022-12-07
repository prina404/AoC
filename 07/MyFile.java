
public class MyFile extends Entry {
    private final int size;

    public MyFile(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyFile)
            return hashCode() == obj.hashCode();
        return false;
    }
}
