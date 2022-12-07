
public class MyFile implements Entry {
    private final int size;
    private final String name;

    public MyFile(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + size;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyFile)
            return hashCode() == obj.hashCode();
        return false;
    }
}
