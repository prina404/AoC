
public abstract class Entry {

    private final String name;

    public Entry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getSize();

    @Override
    public String toString(){
        return name + " with size: " + getSize(); 
    }
}
